package com.github.sebasman.presenter.engine;

/**
 * Manages the timing of the game loop to produce “ticks” at a constant rate.
 * This class encapsulates the delta-timing logic to decouple the game engine
 * from the rendering frame rate.
 */
public final class GameLoopTimer {
    private double nsPerTick;
    private long lastTime;
    private double delta;
    /**
     * The maximum time allowed for a frame in nanoseconds before it is considered
     * an anomalous “jump” (e.g., 200 ms). If the elapsed time is greater than this,
     * the timer will be reset to avoid the “catch-up.”
     */
    private static final long MAX_FRAME_TICKS_NS = 100_000_000; // 100ms

    /**
     * Constructs a new timer with an initial ticks per second rate.
     * @param ticksPerSecond The number of times the game logic should be updated per second.
     */
    public GameLoopTimer(int ticksPerSecond){
        this.setTicksPerSecond(ticksPerSecond);
        this.lastTime = System.nanoTime();
        this.delta = 0;
    }

    /**
     * Updates the timer. This method must be called on each frame of the main loop.
     * Calculates how many ticks of game logic are pending since the last call.
     */
    public void update(){
        long now = System.nanoTime();
        long elapsedTime = now - lastTime;
        // If too much time has passed, that lapse is ignored and the time starting point is simply reset.
        if(elapsedTime > MAX_FRAME_TICKS_NS){
            System.out.println("WARN: Salto de tiempo detectado. Reseteando el temporizador para evitar 'catch-up'.");
            lastTime = now;
            return;
        }
        this.delta += elapsedTime / nsPerTick;
        lastTime = now;
    }

    public boolean shouldTick(){
        if(delta >= 1){
            delta--;
            return true;
        }
        return false;
    }

    /**
     * Returns the interpolation value, which is the fraction of the time
     * that has elapsed to the next tick.
     * @return a value between 0.0 and 1.0.
     */
    public float getInterpolation(){
        return (float) delta;
    }

    /**
     * Changes the speed of the game timer.
     * @param ticksPerSecond The new rate of updates per second.
     */
    public void setTicksPerSecond(int ticksPerSecond){
        if(ticksPerSecond <= 0) return;
        this.nsPerTick = 1_000_000_000.0 / ticksPerSecond;
    }
}
