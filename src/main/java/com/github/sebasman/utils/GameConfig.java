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
    public static final int BOX_SIZE = 40;
    // --- Constants for game configuration ---
    public static final int STARTING_FRAME_RATE = 10;
    // --- Constants for snake configuration ---
    public static final int INPUT_BUFFER_LIMIT = 3;
    // --- Constants for UI configuration ---
    public static final int TOP_BAR_HEIGHT = (int) (BOX_SIZE * 1.5);
    public static final int GAME_AREA_PADDING = 15;
    public static final int COMPONENT_WIDTH = (int) (BOX_SIZE * 6.25);
    public static final int COMPONENT_HEIGHT = (int) (BOX_SIZE * 1.5);
    public static final int RADIUS = 24;
    public static final int CENTER_GAME_X = ((GRID_WIDTH * BOX_SIZE) + GAME_AREA_PADDING*2)/2;
    public static final int SIDE_PANEL_WIDTH = COMPONENT_WIDTH + GAME_AREA_PADDING * 2;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameConfig(){}
}
