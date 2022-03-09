package io.github.ilnurnasybullin.im.entity.evaluator;

import com.fathzer.soft.javaluator.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class GeneralEvaluator extends AbstractEvaluator<Object> {

    private final static List<CustomEvaluator<?>> evaluators;
    private final static Map<Function, BiFunction<Iterator<Object>, Object, Object>> functionEvaluators;
    public final static Parameters parameters;

    static {
        parameters = DoubleEvaluator.getDefaultParameters();
        parameters.add(Operators.LE);
        parameters.add(Operators.LQ);
        parameters.add(Operators.EQ);
        parameters.add(Operators.NQ);
        parameters.add(Operators.GE);
        parameters.add(Operators.GR);

        parameters.add(Functions.IF);
        parameters.add(Functions.SQRT);
        parameters.add(Functions.ARGMIN);
        parameters.add(Functions.GET_BY_INDEX);

        evaluators = List.of(
                new CustomDoubleEvaluator(),
                new BooleanEvaluator(new Parameters()),
                new IntegerEvaluator(new Parameters())
        );

        BiFunction<Iterator<Object>, Object, Object> getByIndexEvaluator =
                (iter, context) -> {
                    int index = IntegerEvaluator.getIntegerValue(iter.next());
                    Object res = null;
                    while (index >= 0 && iter.hasNext()) {
                        res = iter.next();
                        index--;
                    }

                    if (index >= 0) {
                        throw new IllegalArgumentException("Args count is too small!");
                    }

                    return res;
                };

        functionEvaluators = Map.of(
                Functions.GET_BY_INDEX, getByIndexEvaluator
        );
    }

    /**
     * Constructor.
     *
     * @param parameters The evaluator parameters.
     *                   <br>Please note that there's no side effect between the evaluator and the parameters.
     *                   So, changes made to the parameters after the call to this constructor are ignored by the instance.
     */
    public GeneralEvaluator(Parameters parameters) {
        super(parameters);
    }

    public static GeneralEvaluator create() {
        return new GeneralEvaluator(parameters);
    }

    @Override
    protected Object toValue(String literal, Object evaluationContext) {
        System.out.println(literal);
        return literal;
    }

    @Override
    protected Object evaluate(Operator operator, Iterator<Object> operands, Object evaluationContext) {
        CustomEvaluator<?> customEvaluator = evaluators.stream()
                .filter(evaluator -> evaluator.evaluateOperator(operator))
                .findAny()
                .orElseThrow();

        List<Object> args = new ArrayList<>();
        operands.forEachRemaining(args::add);
        return customEvaluator.evaluate(operator, args, evaluationContext);
    }

    @Override
    protected Object evaluate(Function function, Iterator<Object> arguments, Object evaluationContext) {
        BiFunction<Iterator<Object>, Object, Object> eval = functionEvaluators.get(function);
        if (eval != null) {
            return eval.apply(arguments, evaluationContext);
        }

        CustomEvaluator<?> customEvaluator = evaluators.stream()
                .filter(evaluator -> evaluator.evaluateFunction(function))
                .findAny()
                .orElseThrow();

        List<Object> args = new ArrayList<>();
        arguments.forEachRemaining(args::add);
        return customEvaluator.evaluate(function, args, evaluationContext);
    }
}
