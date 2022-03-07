package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.MeasureUnit;
import io.github.ilnurnasybullin.im.entity.StringMeasureUnit;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UnitExtractorServiceImpl implements UnitExtractorService<String> {

    private final Pattern dividePattern = Pattern.compile("\\s*/\\s*");
    private final Pattern multiplyPattern = Pattern.compile("\\s*\\*\\s*");

    // unit_1 = num | num / num
    // num = str | str * num
    @Override
    public MeasureUnit<String> extract(String unit) {
        String[] args = dividePattern.split(unit.trim());

        AtomicInteger integer = new AtomicInteger(1);
        IntUnaryOperator multiplier = value -> -value;
        Map<String, Integer> units = Arrays.stream(args)
                .map(arg -> Map.entry(arg, integer.getAndUpdate(multiplier)))
                .flatMap(entry ->
                        Arrays.stream(multiplyPattern.split(entry.getKey()))
                                .map(str -> Map.entry(str, entry.getValue()))
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));

        return StringMeasureUnit.measureUnit(units);
    }
}
