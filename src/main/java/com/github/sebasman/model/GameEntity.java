package com.github.sebasman.model;

import com.github.sebasman.model.common.Position;

import java.util.List;

/**
 * Represents a game entity that exists within a 2D grid system.
 * Game entities are objects with specific positions in the game world
 * that may have an active or inactive state.
 */
public interface GameEntity {
    /**
     * Retrieves the list of positions associated with this game entity.
     * @return a list of {@code Position} objects representing the locations
     *         of this game entity within the 2D grid system.
     */
    List<Position> getPositions();

    /**
     * Checks whether the game entity is currently in an active state.
     * @return {@code true} if the game entity is active, otherwise {@code false}.
     */
    boolean isActive();

    /**
     * Sets the active state of the game entity.
     * An active game entity can participate in game logic, while an inactive
     * entity is generally ignored in the game system.
     * @param active {@code true} to set the game entity as active, {@code false} to set it as inactive.
     */
    void setActive(boolean active);

    // void update();
}
