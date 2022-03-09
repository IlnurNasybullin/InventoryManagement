package io.github.ilnurnasybullin.im.entity;

public interface MeasureUnitValue<V, U> extends Comparable<MeasureUnitValue<V, U>> {

    MeasureUnitValue<V, U> multiply(MeasureUnitValue<V, U> multiplier);
    MeasureUnitValue<V, U> divide(MeasureUnitValue<V, U> divider);

    MeasureUnitValue<V, U> sqrt();
    MeasureUnitValue<V, U> pow(int degree);

    V value();
    MeasureUnit<U> measureUnit();
}
