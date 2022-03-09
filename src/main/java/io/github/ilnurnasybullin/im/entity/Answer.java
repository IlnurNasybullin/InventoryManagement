package io.github.ilnurnasybullin.im.entity;

import java.util.Collection;

public interface Answer<V> {
    Collection<Variable<V>> variables();
}
