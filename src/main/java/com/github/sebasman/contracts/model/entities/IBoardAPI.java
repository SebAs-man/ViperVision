package com.github.sebasman.contracts.model.entities;

import com.github.sebasman.contracts.vo.Position;

import java.util.Set;

/**
 * The interface defines the contract for the operating boards in the game.
 */
public interface IBoardAPI {
    /**
     * Checks if an obstacle occupies a specific box.
     * @param position The position to check.
     * @return true if the box contains an obstacle, false otherwise.
     */
    boolean isObstacle(Position position);

    /**
     * Attempts to add an obstacle at a specific position.
     * @param position The position where the obstacle will be added.
     * @return true if the obstacle was added successfully, false if there was already one.
     */
    boolean addObstacle(Position position);

    /**
     * Removes an obstacle from a specific position.
     * @param position The position of the obstacle to remove.
     * @return true if the obstacle was removed, false if there was none.
     */
    boolean removeObstacle(Position position);

    /**
     * Returns a copy of the obstacle set for renderers or AI.
     * @return A new Set containing the positions of all obstacles.
     */
    Set<Position> getObstacles();
}
