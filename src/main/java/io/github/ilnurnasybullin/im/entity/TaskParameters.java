package io.github.ilnurnasybullin.im.entity;

import java.util.List;

public record TaskParameters(List<Constant<MeasureUnitValue<Double, String>>> constants,
                             List<Variable<String>> variables) implements CalculationParameters<MeasureUnitValue<Double, String>> {

}
