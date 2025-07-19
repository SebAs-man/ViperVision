package com.github.sebasman.contracts.model.states;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.entities.ISnakeAPI;

/**
 * Defines the contract for the state of the snake.
 * Each state encapsulates a specific behavior, such as how the snake
 * handles collisions or updates itself.
 */
public interface ISnakeState {
    /**
     * Handles the logic for when a collision is detected.
     * The behavior will differ depending on the state.
     * @param snake The snake instance to which the state belongs.
     * @param session The current game session.
     */
    void handleCollision(ISnakeAPI snake, IGameSession session);
}
