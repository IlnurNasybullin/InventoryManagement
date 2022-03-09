package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.Variable;

import java.util.Map;

public interface EvaluatorService<V> {
    Variable<V> evaluate(Variable<String> strVariable, Map<String, V> dependencies);
}
