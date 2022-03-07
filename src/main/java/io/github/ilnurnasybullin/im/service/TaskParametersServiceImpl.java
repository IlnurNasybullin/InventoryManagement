package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.dto.DiscountDto;
import io.github.ilnurnasybullin.im.dto.Task;
import io.github.ilnurnasybullin.im.entity.Discount;
import io.github.ilnurnasybullin.im.entity.MeasureUnit;
import io.github.ilnurnasybullin.im.entity.MeasureUnitValue;
import io.github.ilnurnasybullin.im.entity.TaskParameters;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskParametersServiceImpl implements TaskParametersService {

    private final MeasurementSystemService<Double, String> measurementSystem;
    private final RuleExtractorService<String> ruleExtractor;
    private final UnitExtractorService<String> unitExtractor;
    private final UnitValueExtractor<Double, String> unitValueExtractor;

    public TaskParametersServiceImpl(MeasurementSystemService<Double, String> measurementSystem,
                                     RuleExtractorService<String> ruleExtractor,
                                     UnitExtractorService<String> unitExtractor,
                                     UnitValueExtractor<Double, String> unitValueExtractor) {
        this.measurementSystem = measurementSystem;
        this.ruleExtractor = ruleExtractor;
        this.unitExtractor = unitExtractor;
        this.unitValueExtractor = unitValueExtractor;
    }

    @Override
    public TaskParameters taskParameters(Task task) {
        enum Arg {
            V, T, C, K, s, t
        }

        task.rules().forEach(this::addRule);
        List<MeasureUnit<String>> castUnits = task.castUnits().stream().map(unitExtractor::extract).toList();
        System.out.println(castUnits);

        Map<Arg, MeasureUnitValue<Double, String>> unitValuesMap = Stream.of(
                        Map.entry(Arg.V, task.parameters().V()),
                        Map.entry(Arg.T, task.parameters().T()),
                        Map.entry(Arg.C, task.parameters().C()),
                        Map.entry(Arg.K, task.parameters().K()),
                        Map.entry(Arg.s, task.parameters().s()),
                        Map.entry(Arg.t, task.parameters().tOrd())
                ).map(entry -> Map.entry(entry.getKey(), toMeasureUnitValue(entry.getValue())))
                .map(entry -> Map.entry(entry.getKey(), toCast(entry.getValue(), castUnits)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println(unitValuesMap);

        List<Discount> discounts = task.parameters().discounts().stream()
                .map(this::toDiscount)
                .map(discount -> toCast(discount, castUnits))
                .toList();

        return new TaskParameters(
                unitValuesMap.get(Arg.K),
                unitValuesMap.get(Arg.V),
                unitValuesMap.get(Arg.s),
                unitValuesMap.get(Arg.T),
                unitValuesMap.get(Arg.C),
                unitValuesMap.get(Arg.t),
                discounts
        );
    }

    private MeasureUnitValue<Double, String> toCast(MeasureUnitValue<Double, String> value, Collection<MeasureUnit<String>> units) {
        MeasureUnitValue<Double, String> casted = value;

        for(MeasureUnit<String> unit: units) {
            casted = measurementSystem.tryCast(casted, unit).orElse(casted);
        }

        return casted;
    }

    private Discount toCast(Discount value, Collection<MeasureUnit<String>> units) {
        MeasureUnitValue<Double, String> Q = toCast(value.Q(), units);
        return new Discount(Q, value.discount());
    }

    private void addRule(String rule) {
        ruleExtractor.addRule(rule);
    }

    private Discount toDiscount(DiscountDto discountDto) {
        return new Discount(
                toMeasureUnitValue(discountDto.Q()),
                discountDto.value()
        );
    }

    private MeasureUnitValue<Double, String> toMeasureUnitValue(String unitValue) {
        return unitValueExtractor.extract(unitValue);
    }
}
