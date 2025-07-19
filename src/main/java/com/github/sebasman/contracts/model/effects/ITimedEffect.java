package com.github.sebasman.contracts.model.effects;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.IExpirable;

/**
 * Defines the contract for an effect that has a limited duration.
 * In addition to its initial application, it can be updated on each game tick
 * and has a method to signal when it has finished.
 */

public interface ITimedEffect extends IEffect, IExpirable {
    /**
     * Called just before the effect is removed.
     * This is where the state should be reverted to normal (e.g., set snake back to NORMAL).
     * @param session The game session to clean up.
     */
    void onFinish(IGameSession session);
}
