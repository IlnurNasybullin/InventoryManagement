package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.AnswerParameters;
import io.github.ilnurnasybullin.im.entity.Discount;
import io.github.ilnurnasybullin.im.entity.MeasureUnitValue;
import io.github.ilnurnasybullin.im.entity.TaskParameters;
import org.springframework.stereotype.Service;

@Service
public class InventoryManagementCalculationServiceImpl implements InventoryManagementCalculationService {

    @Override
    public AnswerParameters answer(TaskParameters parameters) {
        MeasureUnitValue<Double, String> Q_w =
                parameters.K()
                        .multiply(parameters.v())
                        .divider(parameters.s())
                        .operation(value -> value * 2)
                        .sqrt();

        MeasureUnitValue<Double, String> L_w = calcL(parameters, Q_w, parameters.C_0());

        MeasureUnitValue<Double, String> Q_opt = Q_w;
        MeasureUnitValue<Double, String> L_opt = L_w;

        for (Discount discount: parameters.discounts()) {
            MeasureUnitValue<Double, String> Q_i = discount.Q();
            double multiplyValue = 1 - discount.discount();
            MeasureUnitValue<Double, String> C_i = parameters.C_0().operation(value -> multiplyValue * value);

            MeasureUnitValue<Double, String> L_i = Q_w.value() > Q_i.value() ?
                    calcL(parameters, Q_w, C_i) :
                    calcL(parameters, Q_i, C_i);

            if (L_opt.value() >= L_i.value()) {
                L_opt = L_i;
                Q_opt = Q_i;
            }
        }

        MeasureUnitValue<Double, String> n_opt =
                parameters.v()
                        .multiply(parameters.T())
                        .divider(Q_opt);

        MeasureUnitValue<Double, String> w_opt =
                parameters.v()
                        .divider(Q_opt);

        MeasureUnitValue<Double, String> t_opt =
                Q_opt.divider(parameters.v());

        MeasureUnitValue<Double, String> q_ord =
                parameters.t().multiply(parameters.v());

        return new AnswerParameters(Q_opt, L_opt, n_opt, w_opt, t_opt, q_ord);
    }

    private MeasureUnitValue<Double, String> calcL(TaskParameters parameters, MeasureUnitValue<Double, String> Q,
                                                   MeasureUnitValue<Double, String> C) {
        MeasureUnitValue<Double, String> registrationAndDelivery =
                parameters.K()
                        .multiply(parameters.v())
                        .multiply(parameters.T())
                        .divider(Q);

        MeasureUnitValue<Double, String> storageAndLoss =
                parameters.s()
                        .multiply(parameters.T())
                        .multiply(Q)
                        .operation(value -> value / 2);

        MeasureUnitValue<Double, String> buying =
                C.multiply(parameters.v())
                        .multiply(parameters.T());

        return registrationAndDelivery.operation(value -> value + storageAndLoss.value() + buying.value());
    }
}
