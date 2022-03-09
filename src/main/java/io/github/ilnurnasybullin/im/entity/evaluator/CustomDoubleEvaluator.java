package io.github.ilnurnasybullin.im.entity.evaluator;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Operator;

import java.util.*;
import java.util.function.BiFunction;

public class CustomDoubleEvaluator extends DoubleEvaluator implements CustomEvaluator<Double> {

    public final static Set<Operator> operators;
    public final static Set<Function> functions;

    public final static Map<Function, BiFunction<List<Object>, Object, Iterator<Double>>> functionAdapters;
    public final static Map<Function, BiFunction<Iterator<Double>, Object, Double>> functionEvaluators;

    static {
        operators = Set.of(
                DoubleEvaluator.PLUS, DoubleEvaluator.MINUS,
                DoubleEvaluator.MULTIPLY, DoubleEvaluator.DIVIDE,
                DoubleEvaluator.NEGATE, DoubleEvaluator.EXPONENT,
                DoubleEvaluator.MODULO, DoubleEvaluator.NEGATE_HIGH
        );

        functions = Set.of(
                DoubleEvaluator.ACOSINE, DoubleEvaluator.MIN, DoubleEvaluator.MAX,
                DoubleEvaluator.ASINE, DoubleEvaluator.ATAN, DoubleEvaluator.AVERAGE,
                DoubleEvaluator.COSINE, DoubleEvaluator.SINE, DoubleEvaluator.SINEH,
                DoubleEvaluator.COSINEH, DoubleEvaluator.TANGENT, DoubleEvaluator.TANGENTH,
                DoubleEvaluator.CEIL, DoubleEvaluator.ABS, DoubleEvaluator.FLOOR, DoubleEvaluator.LN,
                DoubleEvaluator.LOG, DoubleEvaluator.RANDOM, DoubleEvaluator.ROUND, DoubleEvaluator.SUM
        );

        BiFunction<List<Object>, Object, Iterator<Double>> ifFunctionAdapter =
                (list, context) -> {
                    Iterator<Object> iterator = list.iterator();
                    boolean predicate = (boolean) iterator.next();
                    double trueResult = getDoubleValue(iterator.next());
                    double falseResult = getDoubleValue(iterator.next());

                    List<Double> singleton = predicate ? List.of(trueResult) : List.of(falseResult);
                    return singleton.iterator();
                };

        BiFunction<Iterator<Double>, Object, Double> ifFunctionEvaluator =
                (iter, context) -> iter.next();

        functionAdapters = Map.of(
                Functions.IF, ifFunctionAdapter
        );

        BiFunction<Iterator<Double>, Object, Double> sqrtFunctionEvaluator =
                (iter, context) -> Math.sqrt(iter.next());

        functionEvaluators = Map.of(
                Functions.IF, ifFunctionEvaluator,
                Functions.SQRT, sqrtFunctionEvaluator
        );
    }

    public CustomDoubleEvaluator() {
        super();
    }

    @Override
    public Double toValue(String literal, Object evaluationContext) {
        return super.toValue(literal, evaluationContext);
    }

    @Override
    public Double evaluate(Operator operator, List<Object> operands, Object context) {
        Iterator<Double> args = operands.stream().map(CustomDoubleEvaluator::getDoubleValue).iterator();
        return evaluate(operator, args, context);
    }

    @Override
    protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
        BiFunction<Iterator<Double>, Object, Double> evaluator = functionEvaluators.get(function);
        if (evaluator != null) {
            return evaluator.apply(arguments, evaluationContext);
        }

        return super.evaluate(function, arguments, evaluationContext);
    }

    @Override
    public Double evaluate(Function function, List<Object> arguments, Object context) {
        BiFunction<List<Object>, Object, Iterator<Double>> adapter = functionAdapters.get(function);

        if (adapter != null) {
            return evaluate(function, adapter.apply(arguments, context), context);
        }

        Iterator<Double> args = arguments.stream().map(CustomDoubleEvaluator::getDoubleValue).iterator();
        return evaluate(function, args, context);
    }

    @Override
    public boolean evaluateOperator(Operator operator) {
        return operators.contains(operator);
    }

    @Override
    public boolean evaluateFunction(Function function) {
        return functions.contains(function) || functionAdapters.containsKey(function) || functionEvaluators.containsKey(function);
    }

    @Override
    public Double evaluate(Operator operator, Iterator<Double> operands, Object evaluationContext) {
        return super.evaluate(operator, operands, evaluationContext);
    }

    public static double getDoubleValue(Object value) {
        if (value instanceof String) {
            return Double.parseDouble((String) value);
        } else {
            return (double) value;
        }
    }
}
