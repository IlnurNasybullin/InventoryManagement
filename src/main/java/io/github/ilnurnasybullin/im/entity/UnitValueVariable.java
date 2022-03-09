package io.github.ilnurnasybullin.im.entity;

public record UnitValueVariable(String name, String description,
                                MeasureUnitValue<Double, String> value) implements Variable<MeasureUnitValue<Double, String>> {

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", name, description, value);
    }
}
