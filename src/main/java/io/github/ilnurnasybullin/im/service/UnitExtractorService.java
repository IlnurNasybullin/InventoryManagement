package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.MeasureUnit;

public interface UnitExtractorService<U> {
    MeasureUnit<U> extract(String unit);
}
