package io.github.ilnurnasybullin.im.entity;

public record UnitValueConstant(String name, MeasureUnitValue<Double, String> value)
        implements Constant<MeasureUnitValue<Double, String>> {

}
