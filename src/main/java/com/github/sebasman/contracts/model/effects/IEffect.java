package com.github.sebasman.contracts.model.effects;

import com.github.sebasman.contracts.model.IGameSession;

/**
 * Defines the contract for any effect that may be applied
 * to the game session. Uses the Command pattern.
 */
@FunctionalInterface
public interface IEffect {
    /**
     * Executes the effect logic on the game session.
     * @param session The active game session to which the effect will be applied.
     */
    void apply(IGameSession session);
}
