package com.github.sebasman.view.config;

import com.github.sebasman.model.config.ModelConfig;

/**
 * Contains all the configuration constants related to the view,
 * such as window dimensions, UI components, and padding.
 * May depend on ModelConfig to calculate their values.
 */
public final class ViewConfig {
    // --- Padding and frame configuration ---
    public static final int GAME_AREA_PADDING = 15;
    public static final int TOP_BAR_HEIGHT = (int) (ModelConfig.BOX_SIZE * 1.5);
    public static final int RADIUS = 24;

    // --- Configuration of UI Components ---
    public static final int COMPONENT_WIDTH = (int) (ModelConfig.BOX_SIZE * 6.25);
    public static final int COMPONENT_HEIGHT = (int) (ModelConfig.BOX_SIZE * 1.5);
    public static final int SIDE_PANEL_WIDTH = COMPONENT_WIDTH + (GAME_AREA_PADDING * 2);

    // --- Window Configuration (Calculated) ---
    private static final int BOARD_PIXEL_WIDTH = ModelConfig.GRID_WIDTH * ModelConfig.BOX_SIZE;
    private static final int BOARD_PIXEL_HEIGHT = ModelConfig.GRID_HEIGHT * ModelConfig.BOX_SIZE;

    public static final int WINDOW_WIDTH = BOARD_PIXEL_WIDTH + (GAME_AREA_PADDING * 4) + SIDE_PANEL_WIDTH;
    public static final int WINDOW_HEIGHT = BOARD_PIXEL_HEIGHT + (GAME_AREA_PADDING * 3) + TOP_BAR_HEIGHT;

    // --- Positioning Constants ---
    public static final int CENTER_GAME_X = (BOARD_PIXEL_WIDTH + GAME_AREA_PADDING * 2) / 2;
}
