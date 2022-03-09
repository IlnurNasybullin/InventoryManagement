package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.dto.Task;
import io.github.ilnurnasybullin.im.entity.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        task.rules().forEach(this::addRule);
        List<MeasureUnit<String>> castUnits = task.castUnits().stream().map(unitExtractor::extract).toList();
        castUnits.forEach(measurementSystem::addUnit);


        List<Constant<MeasureUnitValue<Double, String>>> constants = task.constants().stream()
                .map(constant -> Map.entry(constant.name(), toMeasureUnitValue(constant.value())))
                .map(entry -> (Constant<MeasureUnitValue<Double, String>>) new UnitValueConstant(
                        entry.getKey(), toCast(entry.getValue(), castUnits)
                )).toList();

        List<Variable<String>> variables = task.variables().stream()
                .map(variable -> (Variable<String>) new StringVariable(
                        variable.name(), variable.description(), variable.expression()
                )).toList();

        return new TaskParameters(constants, variables);
    }

    private MeasureUnitValue<Double, String> toCast(MeasureUnitValue<Double, String> value, Collection<MeasureUnit<String>> units) {
        MeasureUnitValue<Double, String> casted = value;

        for(MeasureUnit<String> unit: units) {
            casted = measurementSystem.tryCast(casted, unit).orElse(casted);
        }

        return casted;
    }

    private void addRule(String rule) {
        ruleExtractor.addRule(rule);
    }

    private MeasureUnitValue<Double, String> toMeasureUnitValue(String unitValue) {
        return unitValueExtractor.extract(unitValue);
    }
}
