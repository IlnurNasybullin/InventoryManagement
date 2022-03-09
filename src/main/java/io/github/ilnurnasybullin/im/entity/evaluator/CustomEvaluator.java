package io.github.ilnurnasybullin.im.entity.evaluator;

import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Operator;

import java.util.List;

public interface CustomEvaluator<T> {
    T evaluate(Operator operator, List<Object> operands, Object context);
    T evaluate(Function function, List<Object> arguments, Object context);
    boolean evaluateOperator(Operator operator);
    boolean evaluateFunction(Function function);
}
