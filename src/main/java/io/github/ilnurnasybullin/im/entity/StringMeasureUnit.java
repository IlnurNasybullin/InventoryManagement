package io.github.ilnurnasybullin.im.entity;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record StringMeasureUnit(
        Map<String, Integer> units) implements MeasureUnit<String> {

    public static final MeasureUnit<String> EMPTY = StringMeasureUnit.measureUnit(Map.of());

    public static MeasureUnit<String> baseMeasureUnit(String unit) {
        return new StringMeasureUnit(Map.of(unit, 1));
    }

    public static MeasureUnit<String> measureUnit(Map<String, Integer> units) {
        return new StringMeasureUnit(Map.copyOf(units));
    }

    @Override
    public MeasureUnit<String> multiply(MeasureUnit<String> multiplier) {
        return sumDegrees(multiplier.units());
    }

    private MeasureUnit<String> sumDegrees(Map<String, Integer> multiplierUnits) {
        Map<String, Integer> units = Stream.concat(
                units().entrySet().stream(),
                multiplierUnits.entrySet().stream()
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));

        units.entrySet().removeIf(entry -> entry.getValue() == 0);
        return measureUnit(units);
    }

    @Override
    public MeasureUnit<String> divide(MeasureUnit<String> divider) {
        Map<String, Integer> dividerUnits = divider.units().entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), -entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return sumDegrees(dividerUnits);
    }

    @Override
    public MeasureUnit<String> sqrt() {
        Map<String, Integer> sqrtUnits = sqrtMap(units());
        return measureUnit(sqrtUnits);
    }

    @Override
    public int degree(MeasureUnit<String> subUnit) {
        Map<String, Integer> units = subUnit.units();
        int degree = Integer.MAX_VALUE;
        for (Map.Entry<String, Integer> subUnitEntry: units.entrySet()) {
            Integer thisCount = this.units.get(subUnitEntry.getKey());
            if (thisCount == null) {
                throw new IllegalArgumentException(String.format("%s isn't subunit %s", subUnit, this));
            }

            int subDegree = thisCount / subUnitEntry.getValue();
            if (subDegree == 0) {
                throw new IllegalArgumentException(String.format("%s isn't subunit %s", subUnit, this));
            }

            if (Math.abs(degree) > Math.abs(subDegree)) {
                degree = subDegree;
            }
        }

        return degree;
    }

    @Override
    public MeasureUnit<String> pow(int degree) {
        return StringMeasureUnit.measureUnit(
                units.entrySet().stream()
                        .map(entry -> Map.entry(entry.getKey(), degree * entry.getValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );
    }

    private Map<String, Integer> sqrtMap(Map<String, Integer> map) {
        return map.entrySet().stream().map(this::sqrtEntry)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, Integer> sqrtEntry(Map.Entry<String, Integer> entry) {
        Integer value = entry.getValue();
        if (value % 2 != 0) {
            throw new IllegalStateException(String.format("Illegal measure unit for sqrt: %s", entry));
        }

        return Map.entry(entry.getKey(), value / 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringMeasureUnit that = (StringMeasureUnit) o;
        return Objects.equals(units, that.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(units);
    }

    @Override
    public String toString() {
        StringJoiner numerator = new StringJoiner(" * ");
        StringJoiner denominator = new StringJoiner(" * ");

        units.forEach((key, value) -> {
            if (value > 0) {
                numerator.add(key);
            } else {
                denominator.add(key);
            }
        });

        if (denominator.length() == 0) {
            return numerator.toString();
        }

        return String.format("%s / %s", numerator, denominator);
    }
}
