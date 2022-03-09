package io.github.ilnurnasybullin.im.entity.evaluator;

import com.fathzer.soft.javaluator.Operator;

public final class Operators {

    public final static Operator LE = new Operator("<", 2, Operator.Associativity.LEFT, 5);
    public final static Operator LQ = new Operator("<=", 2, Operator.Associativity.LEFT, 5);
    public final static Operator GE = new Operator(">=", 2, Operator.Associativity.LEFT, 5);
    public final static Operator GR = new Operator(">", 2, Operator.Associativity.LEFT, 5);
    public final static Operator EQ = new Operator("==", 2, Operator.Associativity.LEFT, 5);
    public final static Operator NQ = new Operator("!=", 2, Operator.Associativity.LEFT, 5);

    private Operators() {}

    public static boolean isBooleanOperator(Operator operator) {
        return operator == LE || operator == LQ || operator == GE
                || operator == GR || operator == EQ || operator == NQ;
    }
}
