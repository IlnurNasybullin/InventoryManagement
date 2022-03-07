package io.github.ilnurnasybullin.im.entity;

public record VertexPath<V, E>(V from, V to, E fromTo, E toFrom) {
}
