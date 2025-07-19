package com.github.sebasman.contracts.model.entities;

import com.github.sebasman.contracts.model.IGameSession;
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
     */
    void addObstacle(Position position);

    /**
     * Removes an obstacle from a specific position.
     * @param position The position of the obstacle to remove.
     */
    void removeObstacle(Position position);

    /**
     * Generates obstacles in different random positions on the map
     * @param amount The number of obstacles to be generated
     * @param session The game session in which the obstacles will be generated.
     */
    void generateRandomObstacles(int amount, IGameSession session);

    /**
     * Returns a copy of the obstacle set for renderers or AI.
     * @return A new Set containing the positions of all obstacles.
     */
    Set<Position> getObstacles();
}
