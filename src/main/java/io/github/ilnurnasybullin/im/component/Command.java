package io.github.ilnurnasybullin.im.component;

public interface Command extends Runnable {
    boolean hasCommand(String commandName);
}
