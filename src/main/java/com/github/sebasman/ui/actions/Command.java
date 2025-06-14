package com.github.sebasman.ui.actions;

/**
 * Command Pattern: Represents an action or an operation that can be executed.
 * Uncouples the object that invokes the action (e.g., a button) from the object that performs it.
 */
@FunctionalInterface // It is an interface with a single abstract method, ideal for lambdas.
public interface Command {
    /**
     * Executes the command.
     * This method should contain the logic for the action to be performed.
     */
    void execute();
}
