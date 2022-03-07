package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.MeasureUnitValue;

public interface UnitValueExtractor<V, U> {
    MeasureUnitValue<V, U> extract(String unitValue);
}
