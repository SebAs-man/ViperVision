package com.github.sebasman.model.config;

/**
 * Contains all configuration constants related to the Data Model
 * and the fundamental rules of the game.
 */
public final class ModelConfig {
    // --- Board and Entities Configuration ---
    public static final int GRID_WIDTH = 20;
    public static final int GRID_HEIGHT = 20;
    public static final int BOX_SIZE = 40;

    // --- Snake Configuration ---
    public static final int INPUT_BUFFER_LIMIT = 3;

    // --- Game Logic Settings ---
    public static final int STARTING_FRAME_RATE = 10;
    public static final int MAX_NOTIFICATIONS_SHOW = 2;

    /**
     * Private builder to prevent instantiation.
     */
    private ModelConfig() {}
}
