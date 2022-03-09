package io.github.ilnurnasybullin.im.entity;

import java.util.Collection;

public record AnswerParameters(Collection<Variable<MeasureUnitValue<Double, String>>> variables)
        implements Answer<MeasureUnitValue<Double, String>> { }
