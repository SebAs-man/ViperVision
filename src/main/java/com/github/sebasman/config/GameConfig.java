package com.github.sebasman.config;

/**
 * The GameConfig class contains constant configuration values for the game.
 * This class is final and cannot be instantiated.
 * It provides predefined settings for the game's board, visual appearance,
 * gameplay, and scoring.
 */
public final class GameConfig {
    /**
     * Private constructor to prevent instantiation of the GameConfig class.
     * This class is designed to only contain static constants and utility methods.
     * Any attempt to create an instance of this class will result in an AssertionError.
     */
    private GameConfig(){
        throw new AssertionError("Cannot instantiate GameConfig class");
    }

    // --- Constants ---

    // Board dimensions (in cells)
    public static final int BOARD_WIDTH_CELLS = 25;
    public static final int BOARD_HEIGHT_CELLS = 25;
    // Visual configuration (in pixels)
    public static final int CELL_SIZE = 20;
    public static final int CELL_PADDING = 2;
    public static final int WINDOW_WIDTH = (BOARD_WIDTH_CELLS * CELL_SIZE) + (CELL_PADDING * 2);
    public static final int WINDOW_HEIGHT = (BOARD_HEIGHT_CELLS * CELL_SIZE) + (CELL_PADDING * 2);
    // Game configuration
    public static final int INITIAL_SNAKE_LENGTH = 3;
    public static final int INITIAL_SNAKE_SPEED_MS = 150;
    // Score
    public static final int INITIAL_SCORE = 0;
}
