package io.github.ilnurnasybullin.im.entity;

public record AnswerParameters(
        /*
          Оптимальное количество ресурсов, которое нужно заказать
         */
        MeasureUnitValue<Double, String> Q_opt,
        /*
          Минимальные затраты, необходимые для осуществления и хранения заказа
         */
        MeasureUnitValue<Double, String> L_opt,
        /*
          Количество поставок за планируемый промежуток времени
         */
        MeasureUnitValue<Double, String> n_opt,
        /*
          Количество поставок за некоторый промеждуток времени
         */
        MeasureUnitValue<Double, String> w_opt,
        /*
          Промежуток времени между заказами
         */
        MeasureUnitValue<Double, String> t_opt,
        /*
          Количество товаров, при которых нужко осуществлять заказ
         */
        MeasureUnitValue<Double, String> q_ord
) { }
