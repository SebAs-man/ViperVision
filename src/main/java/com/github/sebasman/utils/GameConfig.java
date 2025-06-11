package com.github.sebasman.utils;

/**
 * GameConfig is a utility class that holds the configuration constants for the game.
 * It is designed to be a singleton-like class, meaning it should not be instantiated.
 * All fields are static and final, ensuring they are constants.
 */
public final class GameConfig {
    // --- Constants for board configuration ---
    public static final int GRID_WIDTH = 20;
    public static final int GRID_HEIGHT = 20;
    public static final int BOX_SIZE = 35;
    // --- Constants for game configuration ---
    public static final int STARTING_FRAME_RATE = 10;
    // --- Constants for snake configuration ---
    public static final int INPUT_BUFFER_LIMIT = 3;
    // --- Constants for Buttons configuration ---
    public static final int BUTTON_WIDTH = 250;
    public static final int BUTTON_HEIGHT = 60;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameConfig(){}
}
