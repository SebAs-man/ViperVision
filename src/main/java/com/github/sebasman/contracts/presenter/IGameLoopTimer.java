package com.github.sebasman.contracts.presenter;

public interface IGameLoopTimer {
    /**
     * Updates the timer. This method must be called on each frame of the main loop.
     * Calculates how many ticks of game logic are pending since the last call.
     */
    void update();

    /**
     * Checks if a tick can be updated in the game
     * @return True if yes, false otherwise
     */
    boolean shouldTick();

    /**
     * Reverts the timer's speed to its original base speed for this session.
     * Called when a temporary speed effect ends.
     */
    void revertToBaseSpeed();

    /**
     * Returns the interpolation value, which is the fraction of the time
     * that has elapsed to the next tick.
     * @return a value between 0.0 and 1.0.
     */
    float getInterpolation();

    /**
     * Returns the base speed of this timer instance.
     * @return The base ticks per second.
     */
    float getTicksPerSecond();

    /**
     * Changes the current speed of the game timer.
     * @param ticksPerSecond The new rate of updates per second.
     */
    void setTicksPerSecond(float ticksPerSecond);
}
