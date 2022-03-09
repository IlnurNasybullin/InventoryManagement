package io.github.ilnurnasybullin.im.service;

import com.fathzer.soft.javaluator.StaticVariableSet;
import io.github.ilnurnasybullin.im.entity.MeasureUnitValue;
import io.github.ilnurnasybullin.im.entity.StringMeasureUnit;
import io.github.ilnurnasybullin.im.entity.UnitValueVariable;
import io.github.ilnurnasybullin.im.entity.Variable;
import io.github.ilnurnasybullin.im.entity.evaluator.MeasureUnitValueEvaluator;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EvaluatorServiceImpl implements EvaluatorService<MeasureUnitValue<Double, String>> {

    private final MeasureUnitValueEvaluator evaluator;

    public EvaluatorServiceImpl() {
        evaluator = MeasureUnitValueEvaluator.create(StringMeasureUnit.EMPTY);
    }

    @Override
    public Variable<MeasureUnitValue<Double, String>> evaluate(Variable<String> strVariable,
                                                               Map<String, MeasureUnitValue<Double, String>> dependencies) {
        StaticVariableSet<MeasureUnitValue<Double, String>> dependVariables = new StaticVariableSet<>();
        dependencies.forEach(dependVariables::set);

        MeasureUnitValue<Double, String> unitValue = evaluator.evaluate(strVariable.value(), dependVariables);
        return new UnitValueVariable(strVariable.name(), strVariable.description(), unitValue);
    }
}
