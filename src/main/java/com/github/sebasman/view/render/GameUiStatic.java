package com.github.sebasman.view.render;

import com.github.sebasman.view.assets.ColorPalette;
import com.github.sebasman.view.config.ViewConfig;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * GameUiStatic is a singleton class that implements the static elements of the game.
 */
public final class GameUiStatic {
    // Singleton instance of GameUiStatic
    private static final GameUiStatic INSTANCE = new GameUiStatic();
    // PGraphics buffer for pre-rendering UI elements
    private PGraphics buffer;
    // Flag to check if the UI has been initialized
    private boolean isInitialized = false;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameUiStatic() {}

    /**
     * Returns the singleton instance of GameUiStatic.
     * @return The singleton instance of GameUiStatic.
     */
    public static GameUiStatic getInstance() {
        return INSTANCE;
    }

    public void initialize(PApplet context){
        if(isInitialized) {
            System.err.println("GameUI is already initialized, skipping...");
            return; // UI is already initialized, skip initialization
        }
        // Set the flag to true to prevent re-initialization
        isInitialized = true;
        // Initialize the PGraphics buffer with the size of the game window
        this.buffer = context.createGraphics(context.width, context.height);
        // Load the color palette for UI elements
        preRenderElements(context);
    }

    /**
     * Pre-renders the static elements of the UI into the buffer.
     * @param context The PApplet context used for rendering.
     */
    private void preRenderElements(PApplet context) {
        buffer.beginDraw();
        // Set the background color for the UI buffer
        buffer.background(ColorPalette.UI_BACKGROUND);
        // Draw the frame for the game area
        buffer.noStroke();
        buffer.fill(ColorPalette.UI_FRAME);
        // Draw the top bar
        buffer.rect(ViewConfig.GAME_AREA_PADDING/2f, ViewConfig.GAME_AREA_PADDING,
                context.width - ViewConfig.SIDE_PANEL_WIDTH - ViewConfig.GAME_AREA_PADDING*3f, ViewConfig.TOP_BAR_HEIGHT, 16);
        // Draw the side panel
        buffer.rect(context.width - ViewConfig.GAME_AREA_PADDING - ViewConfig.SIDE_PANEL_WIDTH, ViewConfig.GAME_AREA_PADDING,
                ViewConfig.SIDE_PANEL_WIDTH,context.height - (ViewConfig.GAME_AREA_PADDING*2), 16);
        buffer.endDraw();
    }

    public void render(PApplet game) {
        if (!isInitialized) {
            System.err.println("GameUI is not initialized, skipping render...");
            return; // UI is not initialized, skip drawing
        }
        // Draw the pre-rendered buffer onto the main game canvas
        game.image(buffer, 0, 0);
    }
}
