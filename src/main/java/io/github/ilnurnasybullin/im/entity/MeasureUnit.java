package io.github.ilnurnasybullin.im.entity;

import java.util.Map;

public interface MeasureUnit<T> {
    MeasureUnit<T> multiply(MeasureUnit<T> multiplier);
    MeasureUnit<T> divide(MeasureUnit<T> divider);
    MeasureUnit<T> sqrt();

    int degree(MeasureUnit<T> subUnit);
    MeasureUnit<T> pow(int degree);

    Map<T, Integer> units();
}
