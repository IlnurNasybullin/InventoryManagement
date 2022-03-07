package io.github.ilnurnasybullin.im.entity;

import java.util.List;

public record TaskParameters(
        /*
          Стоимость осуществления заказа
         */
        MeasureUnitValue<Double, String> K,
        /*
          Скорость потребления ресурсов
         */
        MeasureUnitValue<Double, String> v,
        /*
          Стоимость хранения ресурса
         */
        MeasureUnitValue<Double, String> s,
        /*
          Время планирования
         */
        MeasureUnitValue<Double, String> T,
        /*
          Стоимость ресурса без скидки
         */
        MeasureUnitValue<Double, String> C_0,
        /*
          Время осуществления заказа
         */
        MeasureUnitValue<Double, String> t,
        /*
          Скидки
         */
        List<Discount> discounts) {

}
