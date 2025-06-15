package com.github.sebasman.core.interfaces.model;

/**
 * Updatable is an interface that defines a contract for objects that can be updated.
 * Classes implementing this interface should provide their own update logic.
 */
public interface Updatable {
    /**
     * The update method is called to perform any necessary updates to the object's state.
     * This method should be implemented by classes that need to perform periodic updates.
     */
    void update();
}
