package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.MeasureUnit;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RuleExtractorServiceImpl implements RuleExtractorService<String> {

    private final MeasurementSystemService<Double, String> measurementSystemService;
    private final UnitExtractorService<String> unitExtractor;
    private final Pattern rulePattern = Pattern.compile("([0-9.]+)\\s+([a-zA-Zа-яА-ЯёЁ.*/\\s]+)\\s+=\\s+([0-9.]+)\\s+([a-zA-Zа-яА-ЯёЁ.*/\\s]+)");

    public RuleExtractorServiceImpl(MeasurementSystemService<Double, String> measurementSystemService,
                                    UnitExtractorService<String> unitExtractor) {
        this.measurementSystemService = measurementSystemService;
        this.unitExtractor = unitExtractor;
    }

    //count_1 unit_1 = count_2 unit_2
    @Override
    public void addRule(String rule) {
        Matcher matcher = rulePattern.matcher(rule);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("Rule %s is incorrect!", rule));
        }

        String count_1 = matcher.group(1);
        String unit_1 = matcher.group(2);
        String count_2 = matcher.group(3);
        String unit_2 = matcher.group(4);

        Double from = Double.parseDouble(count_1);
        Double to =Double.parseDouble(count_2);

        MeasureUnit<String> source = unitExtractor.extract(unit_1);
        MeasureUnit<String> target = unitExtractor.extract(unit_2);

        measurementSystemService.putOrReplace(source, target, from, to);
    }
}
