package io.github.ilnurnasybullin.im.entity.evaluator;

import com.fathzer.soft.javaluator.Function;

public final class Functions {

    private Functions() {}

    public static final Function SQRT = new Function("sqrt", 1);
    public static final Function IF = new Function("if", 3);
    public static final Function ARGMIN = new Function("argmin", 1, Integer.MAX_VALUE);
    public static final Function GET_BY_INDEX = new Function("getByIndex", 2, Integer.MAX_VALUE);

}
