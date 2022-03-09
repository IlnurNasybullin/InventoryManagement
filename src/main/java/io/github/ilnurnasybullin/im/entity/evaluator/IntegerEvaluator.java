package io.github.ilnurnasybullin.im.entity.evaluator;

import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class IntegerEvaluator extends AbstractEvaluator<Object> implements CustomEvaluator<Integer> {

    private final static Map<Function, BiFunction<List<Object>, Object, Iterator<Object>>> functionAdapters;
    private final static Map<Function, BiFunction<Iterator<Object>, Object, Integer>> functionEvaluators;

    static {
        BiFunction<List<Object>, Object, Iterator<Object>> argminAdapter =
                (list, context) -> list.stream()
                        .map(CustomDoubleEvaluator::getDoubleValue)
                        .map(arg -> (Object) arg)
                        .iterator();

        functionAdapters = Map.of(
                Functions.ARGMIN, argminAdapter
        );

        BiFunction<Iterator<Object>, Object, Integer> argminEvaluator =
                (iter, context) -> {
                    int minIndex = 0;
                    double minValue = Double.MAX_VALUE;
                    int currentIndex = 0;

                    while (iter.hasNext()) {
                        double value = (double) iter.next();
                        if (minValue > value) {
                            minValue = value;
                            minIndex = currentIndex;
                        }

                        currentIndex++;
                    }

                    return minIndex;
                };

        functionEvaluators = Map.of(
                Functions.ARGMIN, argminEvaluator
        );
    }

    /**
     * Constructor.
     *
     * @param parameters The evaluator parameters.
     *                   <br>Please note that there's no side effect between the evaluator and the parameters.
     *                   So, changes made to the parameters after the call to this constructor are ignored by the instance.
     */
    protected IntegerEvaluator(Parameters parameters) {
        super(parameters);
    }

    @Override
    protected Object toValue(String literal, Object evaluationContext) {
        return literal;
    }

    @Override
    public Integer evaluate(Operator operator, List<Object> operands, Object context) {
        throw new IllegalStateException();
    }

    @Override
    public Integer evaluate(Function function, List<Object> arguments, Object context) {
        BiFunction<List<Object>, Object, Iterator<Object>> adapter = functionAdapters.get(function);
        if (adapter != null) {
            return evaluate(function, adapter.apply(arguments, context), context);
        }

        throw new IllegalStateException();
    }

    @Override
    public boolean evaluateOperator(Operator operator) {
        return false;
    }

    @Override
    public boolean evaluateFunction(Function function) {
        return functionAdapters.containsKey(function) || functionEvaluators.containsKey(function);
    }

    @Override
    protected Integer evaluate(Function function, Iterator<Object> arguments, Object evaluationContext) {
        BiFunction<Iterator<Object>, Object, Integer> evaluator = functionEvaluators.get(function);
        if (evaluator != null) {
            return evaluator.apply(arguments, evaluationContext);
        }

        throw new IllegalStateException();
    }

    public static int getIntegerValue(Object val) {
        if (val instanceof String) {
            return Integer.parseInt((String) val);
        } else if (val instanceof Double) {
            return (int) ((double) val);
        }

        return (int) val;
    }
}
