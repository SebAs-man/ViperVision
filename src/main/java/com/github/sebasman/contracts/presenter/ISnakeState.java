package com.github.sebasman.contracts.presenter;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.effects.IEffect;
import com.github.sebasman.contracts.model.entities.ISnakeAPI;
import com.github.sebasman.contracts.view.ISnakeRenderStyle;

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

    /**
     * Returns the rendering style associated with this state.
     * @return An object that defines how the snake should be drawn.
     */
    ISnakeRenderStyle getRenderStyle();

    /**
     * Processes an incoming effect according to the rules of the current state.
     * This method allows each state to decide whether to apply, modify, or ignore an effect.
     * @param effect The effect to be processed.
     * @return The resulting effect to be applied, or null if the effect should be canceled.
     */
    IEffect processEffect(IEffect effect);
}
