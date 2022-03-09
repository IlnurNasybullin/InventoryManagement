package io.github.ilnurnasybullin.im.service;

import io.github.ilnurnasybullin.im.entity.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CalculationServiceImpl implements CalculationService {

    private final VariableDependencyManagementService variableDependencyManagement;
    private final DirectedGraph<String> calculationGraph;
    private final EvaluatorService<MeasureUnitValue<Double, String>> evaluatorService;
    
    public CalculationServiceImpl(VariableDependencyManagementService variableDependencyManagement, 
                                  EvaluatorService<MeasureUnitValue<Double, String>> evaluatorService) {
        this.variableDependencyManagement = variableDependencyManagement;
        this.evaluatorService = evaluatorService;
        calculationGraph = new DirectedGraphImpl<>();
    }

    @Override
    public AnswerParameters calculate(TaskParameters parameters) {
        Map<String, MeasureUnitValue<Double, String>> constants = parameters.constants()
                .stream().collect(Collectors.toMap(Constant::name, Constant::value));

        Map<String, Variable<String>> variables = parameters.variables()
                .stream().collect(Collectors.toMap(Constant::name, Function.identity()));

        Set<String> names = new HashSet<>(constants.keySet());
        names.addAll(variables.keySet());

        variableDependencyManagement.createPatterns(names);

        parameters.variables().forEach(variable -> {
            for (String sourceName: variableDependencyManagement.dependencies(variable.value())) {
                calculationGraph.addEdge(sourceName, variable.name());
            }
        });

        Map<String, MeasureUnitValue<Double, String>> dependencies = new HashMap<>(constants);

        List<Variable<MeasureUnitValue<Double, String>>> answers = new ArrayList<>();
        Iterator<String> iterator = calculationGraph.topologicalOrderIterator();
        while (iterator.hasNext()) {
            String variableName = iterator.next();
            Variable<String> variable = variables.get(variableName);
            if (variable != null) {
                Variable<MeasureUnitValue<Double, String>> answer = evaluatorService.evaluate(variable, dependencies);
                answers.add(answer);
                dependencies.put(answer.name(), answer.value());
            }
        }

        return new AnswerParameters(answers);
    }
}
