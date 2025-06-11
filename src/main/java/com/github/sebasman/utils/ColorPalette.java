package com.github.sebasman.utils;

import processing.core.PApplet;

/**
 * colorPallete is a utility class that holds the color constants used in the UI.
 */
public final class ColorPalette {
    // --- Constants for color board ---
    public static int BOARD_LIGHT;
    public static int BOARD_DARK;
    // --- Colors for the snake ---
    public static int SNAKE_BODY;
    public static int SNAKE_EYES;
    // --- Colors for the UI elements ---
    public static int BUTTON_FILL;
    public static int BUTTON_HOVER_FILL;
    public static int BUTTON_STROKE;
    public static int TEXT_PRIMARY;
    public static int TEXT_SECONDARY;
    public  static int TEXT_TERTIARY;
    public static int TEXT_QUATERNARY;

    /**
     * Loads the color palette using the provided PApplet instance.
     * @param p The PApplet instance used to access the color() method.
     */
    public static void load(PApplet p) {
        // Initialize colors using the PApplet instance
        BOARD_LIGHT = p.color(170, 215, 81);
        BOARD_DARK = p.color(162, 209, 73);
        SNAKE_BODY = p.color(97, 74, 240);
        SNAKE_EYES = p.color(255); // Blanco
        BUTTON_FILL = p.color(63, 15, 208, 200);
        BUTTON_HOVER_FILL = p.color(90, 45, 230, 2000);
        BUTTON_STROKE = p.color(120, 70, 255, 255);
        TEXT_PRIMARY = p.color(220, 220, 220, 255);
        TEXT_SECONDARY = p.color(44, 102, 30, 255);
        TEXT_TERTIARY = p.color(20, 20, 80, 255);
        TEXT_QUATERNARY = p.color(242, 25, 25);
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private ColorPalette(){}
}
