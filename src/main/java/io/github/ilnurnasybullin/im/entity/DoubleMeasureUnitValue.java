package io.github.ilnurnasybullin.im.entity;

import java.util.function.UnaryOperator;

public record DoubleMeasureUnitValue(MeasureUnit<String> measureUnit, Double value) implements MeasureUnitValue<Double, String> {
    @Override
    public MeasureUnitValue<Double, String> multiply(MeasureUnitValue<Double, String> multiplier) {
        MeasureUnit<String> multiplyUnit = measureUnit.multiply(multiplier.measureUnit());
        Double multiplyDouble = value * multiplier.value();

        return new DoubleMeasureUnitValue(multiplyUnit, multiplyDouble);
    }

    @Override
    public MeasureUnitValue<Double, String> divider(MeasureUnitValue<Double, String> divider) {
        MeasureUnit<String> multiplyUnit = measureUnit.divide(divider.measureUnit());
        Double divideDouble = value / divider.value();

        return new DoubleMeasureUnitValue(multiplyUnit, divideDouble);
    }

    @Override
    public MeasureUnitValue<Double, String> sqrt() {
        return new DoubleMeasureUnitValue(measureUnit.sqrt(), Math.sqrt(value));
    }

    @Override
    public MeasureUnitValue<Double, String> pow(int degree) {
        MeasureUnit<String> powUnits = measureUnit.pow(degree);
        return new DoubleMeasureUnitValue(powUnits, Math.pow(value, degree));
    }

    @Override
    public MeasureUnitValue<Double, String> operation(UnaryOperator<Double> operator) {
        return new DoubleMeasureUnitValue(measureUnit, operator.apply(value));
    }
}
