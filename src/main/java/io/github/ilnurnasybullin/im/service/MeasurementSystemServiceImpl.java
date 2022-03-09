package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.DoubleUnaryOperator;

@Service
public class MeasurementSystemServiceImpl implements MeasurementSystemService<Double, String> {

    private final WeightedDirectedGraph<MeasureUnit<String>, Edge<MeasureUnit<String>>> graph;

    public MeasurementSystemServiceImpl() {
        this.graph = new WeightedDirectedGraphImpl<>();
    }

    @Override
    public void putOrReplace(MeasureUnit<String> from, MeasureUnit<String> to, Double fromTo, Double toFrom) {
        graph.addEdge(from, to, fromTo, toFrom);
    }

    @Override
    public Optional<MeasureUnitValue<Double, String>> tryCast(MeasureUnitValue<Double, String> from, MeasureUnit<String> cast) {
        Map<String, Integer> units = from.measureUnit().units();

        MeasureUnit<String> current = cast;
        Iterator<MeasureUnit<String>> iterator = graph.breadthIterator(cast);
        boolean isContains;
        do {
            isContains = containsAllUnits(units, current.units());
        } while (iterator.hasNext() && !isContains && (current = iterator.next()) != null);

        if (!isContains) {
            return Optional.empty();
        }

        List<Edge<MeasureUnit<String>>> path = graph.path(current, cast);
        if (path.isEmpty()) {
            return Optional.of(from);
        }

        DoubleUnaryOperator operator = DoubleUnaryOperator.identity();
        MeasureUnit<String> source = current;

        int degree = from.measureUnit().degree(current);
        for (Edge<MeasureUnit<String>> edge: path) {
            boolean notReversed = edge.source().equals(source) || edge.target().equals(cast);
            if (notReversed) {
                operator = operator.andThen(value -> value * Math.pow(edge.to() / edge.from(), degree));
                source = edge.target();
            } else {
                source = edge.source();
                operator = operator.andThen(value -> value * Math.pow(edge.from() / edge.to(), degree));
            }
        }

        MeasureUnit<String> castedUnit = replaceIn(from.measureUnit(), current, cast);
        double value = operator.applyAsDouble(from.value());

        return Optional.of(new DoubleMeasureUnitValue(castedUnit, value));
    }

    private MeasureUnit<String> replaceIn(MeasureUnit<String> unit, MeasureUnit<String> oldPart, MeasureUnit<String> newPart) {
        int degree = unit.degree(oldPart);
        MeasureUnit<String> divideUnit = oldPart.pow(degree);
        MeasureUnit<String> multiplyUnit = newPart.pow(degree);

        return unit.divide(divideUnit).multiply(multiplyUnit);
    }

    private boolean containsAllUnits(Map<String, Integer> units, Map<String, Integer> part) {
        for (Map.Entry<String, Integer> partUnit: part.entrySet()) {
            String partUnitName = partUnit.getKey();
            if (!units.containsKey(partUnitName)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void clearAll() {
        graph.clear();
    }

    @Override
    public void addUnit(MeasureUnit<String> unit) {
        graph.addVertex(unit);
    }
}
