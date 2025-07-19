package com.github.sebasman.contracts.model.entities;

/**
 * This is an interface that defines the contract for objects that expire in time.
 */
public interface IExpirable {
    /**
     * Updates the internal state of the food item based on the elapsed time.
     * This is used for foods that have a limited lifespan.
     * @param elapsedTime The time in milliseconds since the last update.
     */
    void update(long elapsedTime);

    /**
     * Checks if the object's lifetime has already expired
     * @return True if already expired, false otherwise.
     */
    boolean isExpired();
}
