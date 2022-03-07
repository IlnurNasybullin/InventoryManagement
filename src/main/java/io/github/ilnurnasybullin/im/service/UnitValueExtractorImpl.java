package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.DoubleMeasureUnitValue;
import io.github.ilnurnasybullin.im.entity.MeasureUnitValue;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UnitValueExtractorImpl implements UnitValueExtractor<Double, String> {

    private final Pattern unitValuePattern = Pattern.compile("([0-9.]+)\\s+(.*)");
    private final UnitExtractorService<String> unitExtractor;

    public UnitValueExtractorImpl(UnitExtractorService<String> unitExtractor) {
        this.unitExtractor = unitExtractor;
    }

    //unit_value = count unit
    @Override
    public MeasureUnitValue<Double, String> extract(String unitValue) {
        Matcher matcher = unitValuePattern.matcher(unitValue);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("Unit value %s is incorrect!", unitValue));
        }

        String count = matcher.group(1);
        String unit = matcher.group(2);

        Double value = Double.parseDouble(count);

        return new DoubleMeasureUnitValue(unitExtractor.extract(unit), value);
    }
}
