package io.github.ilnurnasybullin.im.entity;

import java.util.List;

public interface CalculationParameters<V> {
    List<Constant<V>> constants();
    List<Variable<String>> variables();
}
