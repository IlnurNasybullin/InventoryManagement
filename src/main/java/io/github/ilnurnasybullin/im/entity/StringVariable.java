package io.github.ilnurnasybullin.im.entity;

public record StringVariable(String name, String description, String value) implements Variable<String> {
}
