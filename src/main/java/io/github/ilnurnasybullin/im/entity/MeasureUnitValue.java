package io.github.ilnurnasybullin.im.entity;

import java.util.function.UnaryOperator;

public interface MeasureUnitValue<V, U> {

    MeasureUnitValue<V, U> multiply(MeasureUnitValue<V, U> multiplier);
    MeasureUnitValue<V, U> divider(MeasureUnitValue<V, U> divider);
    MeasureUnitValue<V, U> operation(UnaryOperator<V> operator);
    MeasureUnitValue<V, U> sqrt();
    MeasureUnitValue<V, U> pow(int degree);

    V value();
    MeasureUnit<U> measureUnit();
}
