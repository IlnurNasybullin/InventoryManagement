package io.github.ilnurnasybullin.im.entity;

public record DoubleMeasureUnitValue(MeasureUnit<String> measureUnit, Double value) implements MeasureUnitValue<Double, String> {
    @Override
    public MeasureUnitValue<Double, String> multiply(MeasureUnitValue<Double, String> multiplier) {
        MeasureUnit<String> multiplyUnit = measureUnit.multiply(multiplier.measureUnit());
        Double multiplyDouble = value * multiplier.value();

        return new DoubleMeasureUnitValue(multiplyUnit, multiplyDouble);
    }

    @Override
    public MeasureUnitValue<Double, String> divide(MeasureUnitValue<Double, String> divider) {
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
    public int compareTo(MeasureUnitValue<Double, String> o) {
        return Double.compare(this.value, o.value());
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, measureUnit);
    }
}
