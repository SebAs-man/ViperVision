package com.github.sebasman.ui;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.ui.IStaticFrameRenderer;
import com.github.sebasman.utils.ColorPalette;
import com.github.sebasman.utils.GameConfig;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * GameUiStatic is a singleton class that implements the IStaticFrameRenderer interface.
 */
public final class GameUiStatic implements IStaticFrameRenderer {
    // Singleton instance of GameUI
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
     * Returns the singleton instance of GameUI.
     * @return The singleton instance of GameUI.
     */
    public static GameUiStatic getInstance() {
        return INSTANCE;
    }

    @Override
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
        buffer.rect(GameConfig.GAME_AREA_PADDING/2f, GameConfig.GAME_AREA_PADDING,
                context.width - GameConfig.SIDE_PANEL_WIDTH - GameConfig.GAME_AREA_PADDING*3f, GameConfig.TOP_BAR_HEIGHT, 16);
        // Draw the side panel
        buffer.rect(context.width - GameConfig.GAME_AREA_PADDING - GameConfig.SIDE_PANEL_WIDTH, GameConfig.GAME_AREA_PADDING,
                GameConfig.SIDE_PANEL_WIDTH,context.height - (GameConfig.GAME_AREA_PADDING*2), 16);
        buffer.endDraw();
    }

    @Override
    public void render(Game game) {
        if (!isInitialized) {
            System.err.println("GameUI is not initialized, skipping render...");
            return; // UI is not initialized, skip drawing
        }
        // Draw the pre-rendered buffer onto the main game canvas
        game.image(buffer, 0, 0);
    }
}
