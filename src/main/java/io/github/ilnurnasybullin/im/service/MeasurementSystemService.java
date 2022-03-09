package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.MeasureUnit;
import io.github.ilnurnasybullin.im.entity.MeasureUnitValue;

import java.util.Optional;

public interface MeasurementSystemService<V, U> {
    void putOrReplace(MeasureUnit<U> from, MeasureUnit<U> to, V fromTo, V toFrom);
    Optional<MeasureUnitValue<V, U>> tryCast(MeasureUnitValue<V, U> from, MeasureUnit<U> cast);
    void clearAll();
    void addUnit(MeasureUnit<U> unit);
}
