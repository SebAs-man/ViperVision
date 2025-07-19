package com.github.sebasman.contracts.model.effects;

import com.github.sebasman.contracts.model.IGameSession;

/**
 * Defines the contract for an effect that has a limited duration.
 * In addition to its initial application, it can be updated on each game tick
 * and has a method to signal when it has finished.
 */

public interface ITimedEffect extends IEffect{
    /**
     * Updates the effect's internal timer.
     * @param elapsedTime The time in milliseconds since the last update.
     */
    void update(long elapsedTime);

    /**
     * Checks if the effect's duration has expired.
     * @return true if the effect is finished, false otherwise.
     */
    boolean isFinished();

    /**
     * Called just before the effect is removed.
     * This is where the state should be reverted to normal (e.g., set snake back to NORMAL).
     * @param session The game session to clean up.
     */
    void onFinish(IGameSession session);
}
