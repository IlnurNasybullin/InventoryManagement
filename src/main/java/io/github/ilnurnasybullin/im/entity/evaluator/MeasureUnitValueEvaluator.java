package io.github.ilnurnasybullin.im.entity.evaluator;

import com.fathzer.soft.javaluator.*;
import io.github.ilnurnasybullin.im.entity.DoubleMeasureUnitValue;
import io.github.ilnurnasybullin.im.entity.MeasureUnit;
import io.github.ilnurnasybullin.im.entity.MeasureUnitValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class MeasureUnitValueEvaluator extends AbstractEvaluator<MeasureUnitValue<Double, String>> {

    private final MeasureUnit<String> init;
    private final GeneralEvaluator evaluator;
    private final CustomDoubleEvaluator doubleEvaluator;

    private final static Map<Operator, BiFunction<Iterator<MeasureUnitValue<Double, String>>, Object, MeasureUnitValue<Double, String>>> operators;

    static {
        BiFunction<Iterator<MeasureUnitValue<Double, String>>, Object, MeasureUnitValue<Double, String>> multiplyEvaluator =
                (iter, context) -> iter.next().multiply(iter.next());

        BiFunction<Iterator<MeasureUnitValue<Double, String>>, Object, MeasureUnitValue<Double, String>> divideEvaluator =
                (iter, context) -> iter.next().divide(iter.next());

        BiFunction<Iterator<MeasureUnitValue<Double, String>>, Object, MeasureUnitValue<Double, String>> powEvaluator =
                (iter, context) -> iter.next().pow((int)(double)iter.next().value());

        operators = Map.of(
                DoubleEvaluator.MULTIPLY, multiplyEvaluator,
                DoubleEvaluator.DIVIDE, divideEvaluator,
                DoubleEvaluator.EXPONENT, powEvaluator
        );
    }

    private MeasureUnitValueEvaluator(Parameters parameters, MeasureUnit<String> init) {
        super(parameters);
        this.init = init;
        this.evaluator = GeneralEvaluator.create();
        this.doubleEvaluator = new CustomDoubleEvaluator();
    }

    public static MeasureUnitValueEvaluator create(MeasureUnit<String> init) {
        return new MeasureUnitValueEvaluator(GeneralEvaluator.parameters, init);
    }

    @Override
    protected MeasureUnitValue<Double, String> toValue(String literal, Object evaluationContext) {
        Double value = doubleEvaluator.toValue(literal, evaluationContext);
        return new DoubleMeasureUnitValue(init, value);
    }

    @Override
    protected MeasureUnitValue<Double, String> evaluate(Operator operator, Iterator<MeasureUnitValue<Double, String>> operands, Object evaluationContext) {
        BiFunction<Iterator<MeasureUnitValue<Double, String>>, Object, MeasureUnitValue<Double, String>> evaluator = operators.get(operator);
        if (evaluator != null) {
            return evaluator.apply(operands, evaluationContext);
        }


        MeasureUnitValue<Double, String> unitValue = operands.next();
        MeasureUnit<String> unit = unitValue.measureUnit();

        List<Object> args = new ArrayList<>();
        args.add(unitValue.value());
        operands.forEachRemaining(operand -> args.add(operand.value()));

        double result = getResult(operator, evaluationContext, args);
        return new DoubleMeasureUnitValue(unit, result);
    }

    private double getResult(Operator operator, Object evaluationContext, List<Object> args) {
        double result;
        if (Operators.isBooleanOperator(operator)) {
            result = (boolean)(evaluator.evaluate(operator, args.iterator(), evaluationContext)) ? 1d : 0d;
        } else {
            result = (double) this.evaluator.evaluate(operator, args.iterator(), evaluationContext);
        }

        return result;
    }

    @Override
    protected MeasureUnitValue<Double, String> evaluate(Function function, Iterator<MeasureUnitValue<Double, String>> arguments, Object evaluationContext) {
        if (function == Functions.SQRT) {
            return sqrt(arguments);
        }

        if (function == Functions.IF) {
            return ifFunction(arguments);
        }

        if (function == Functions.ARGMIN) {
            return argmin(function, arguments, evaluationContext);
        }

        if (function == Functions.GET_BY_INDEX) {
            return getByIndex(arguments);
        }

        MeasureUnitValue<Double, String> unitValue = arguments.next();
        MeasureUnit<String> unit = unitValue.measureUnit();

        List<Object> args = new ArrayList<>();
        args.add(unitValue.value());
        arguments.forEachRemaining(arg -> args.add(arg.value()));

        double result = (double) evaluator.evaluate(function, args.iterator(), evaluationContext);
        return new DoubleMeasureUnitValue(unit, result);
    }

    private MeasureUnitValue<Double, String> getByIndex(Iterator<MeasureUnitValue<Double, String>> arguments) {
        int index = (int) (double) arguments.next().value();

        MeasureUnitValue<Double, String> unitValue = null;
        while (index >= 0 && arguments.hasNext()) {
            unitValue = arguments.next();
            index--;
        }

        if (index >= 0) {
            throw new IllegalArgumentException("Args count is too small!");
        }

        return unitValue;
    }

    private DoubleMeasureUnitValue argmin(Function function, Iterator<MeasureUnitValue<Double, String>> arguments, Object evaluationContext) {
        List<Object> args = new ArrayList<>();
        arguments.forEachRemaining(arg -> args.add(arg.value()));
        int index = (int) evaluator.evaluate(function, args.iterator(), evaluationContext);
        return new DoubleMeasureUnitValue(init, (double) index);
    }

    private MeasureUnitValue<Double, String> ifFunction(Iterator<MeasureUnitValue<Double, String>> arguments) {
        MeasureUnitValue<Double, String> predicate = arguments.next();
        MeasureUnitValue<Double, String> trueResult = arguments.next();
        MeasureUnitValue<Double, String> falseResult = arguments.next();

        return predicate.value() == 1d ? trueResult : falseResult;
    }

    private MeasureUnitValue<Double, String> sqrt(Iterator<MeasureUnitValue<Double, String>> arguments) {
        return arguments.next().sqrt();
    }
}
