package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.MeasureUnitValue;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RuleExtractorServiceImpl implements RuleExtractorService<String> {

    private final MeasurementSystemService<Double, String> measurementSystemService;
    private final UnitValueExtractor<Double, String> unitValueExtractor;
    private final Pattern rulePattern = Pattern.compile("(-?[0-9.]+\\s+[a-zA-Zа-яА-ЯёЁ.*/^\\s]+)\\s+=\\s+(-?[0-9.]+\\s+[a-zA-Zа-яА-ЯёЁ.*/^\\s]+)");

    public RuleExtractorServiceImpl(MeasurementSystemService<Double, String> measurementSystemService,
                                    UnitValueExtractor<Double, String> unitValueExtractor) {
        this.measurementSystemService = measurementSystemService;
        this.unitValueExtractor = unitValueExtractor;
    }

    //unit_value_1 = unit_value_2
    @Override
    public void addRule(String rule) {
        Matcher matcher = rulePattern.matcher(rule);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("Rule %s is incorrect!", rule));
        }

        String unitValue_1 = matcher.group(1);
        String unitValue_2 = matcher.group(2);

        MeasureUnitValue<Double, String> source = unitValueExtractor.extract(unitValue_1);
        MeasureUnitValue<Double, String> target = unitValueExtractor.extract(unitValue_2);

        measurementSystemService.putOrReplace(source.measureUnit(), target.measureUnit(), source.value(), target.value());
    }
}
