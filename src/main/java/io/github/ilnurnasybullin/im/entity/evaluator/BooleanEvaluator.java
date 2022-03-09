package io.github.ilnurnasybullin.im.entity.evaluator;

import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class BooleanEvaluator extends AbstractEvaluator<Object> implements CustomEvaluator<Boolean> {

    private final static Map<Operator, BiFunction<List<Object>, Object, Iterator<Object>>> operatorAdapters;
    private final static Map<Operator, BiFunction<Iterator<Object>, Object, Boolean>> operatorEvaluators;

    static {
        operatorAdapters = Map.of();

        BiFunction<Iterator<Object>, Object, Boolean> leOperator =
                (iter, context) -> (double) iter.next() < (double) iter.next();

        BiFunction<Iterator<Object>, Object, Boolean> lqOperator =
                (iter, context) -> (double) iter.next() <= (double) iter.next();

        BiFunction<Iterator<Object>, Object, Boolean> geOperator =
                (iter, context) -> (double) iter.next() >= (double) iter.next();

        BiFunction<Iterator<Object>, Object, Boolean> grOperator =
                (iter, context) -> (double) iter.next() > (double) iter.next();

        BiFunction<Iterator<Object>, Object, Boolean> eqOperator =
                (iter, context) -> (double) iter.next() == (double) iter.next();

        BiFunction<Iterator<Object>, Object, Boolean> nqOperator =
                (iter, context) -> (double) iter.next() != (double) iter.next();

        operatorEvaluators = Map.of(
                Operators.LE, leOperator,
                Operators.LQ, lqOperator,
                Operators.GE, geOperator,
                Operators.GR, grOperator,
                Operators.EQ, eqOperator,
                Operators.NQ, nqOperator
        );
    }

    /**
     * Constructor.
     *
     * @param parameters The evaluator parameters.
     *                   <br>Please note that there's no side effect between the evaluator and the parameters.
     *                   So, changes made to the parameters after the call to this constructor are ignored by the instance.
     */
    public BooleanEvaluator(Parameters parameters) {
        super(parameters);
    }

    @Override
    public Object toValue(String literal, Object evaluationContext) {
        return literal;
    }

    @Override
    protected Object evaluate(Operator operator, Iterator<Object> operands, Object evaluationContext) {
        BiFunction<Iterator<Object>, Object, Boolean> evaluator = operatorEvaluators.get(operator);
        if (evaluator != null) {
            return evaluator.apply(operands, evaluationContext);
        }

        throw new IllegalStateException();
    }

    @Override
    public Boolean evaluate(Operator operator, List<Object> operands, Object context) {
        List<Object> args = new ArrayList<>();
        operands.forEach(operand -> {
            if (operand instanceof String) {
                args.add(Double.parseDouble((String) operand));
            } else {
                args.add(operand);
            }
        });

        return (boolean) evaluate(operator, args.iterator(), context);
    }

    @Override
    public Boolean evaluate(Function function, List<Object> arguments, Object context) {
        throw new IllegalStateException();
    }

    @Override
    public boolean evaluateOperator(Operator operator) {
        return operatorAdapters.containsKey(operator) || operatorEvaluators.containsKey(operator);
    }

    @Override
    public boolean evaluateFunction(Function function) {
        return false;
    }
}
