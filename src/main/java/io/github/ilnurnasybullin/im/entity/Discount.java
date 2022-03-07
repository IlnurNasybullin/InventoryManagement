package io.github.ilnurnasybullin.im.entity;

public record Discount(
        /*
          Левая граница условия
         */
        MeasureUnitValue<Double, String> Q,
        /*
          Значение скидки (от 0 до 1)
         */
        double discount) {

}
