package com.github.sebasman.view.render;

import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.view.config.ColorPalette;
import com.github.sebasman.view.config.ViewConfig;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Specific renderer for the board. Knows how to draw a Board object
 */
public final class BoardRender {
    // Singleton instance of BoardRender
    private static final BoardRender INSTANCE = new BoardRender();
    // PGraphics buffer for pre-rendering board
    private PGraphics buffer;
    // Flag to check if the UI has been initialized
    private boolean isInitialized = false;

    /**
     * Private constructor to prevent instantiation.
     */
    private BoardRender() {}

    /**
     * Returns the singleton instance of BoardRender.
     * @return The singleton instance of BoardRender.
     */
    public static BoardRender getInstance() {
        return INSTANCE;
    }

    /**
     * Pre-renders the static board of the UI into the buffer.
     * @param context The PApplet context used for rendering
     */
    public void initialize(PApplet context){
        if(isInitialized) {
            System.err.println("The board is already initialized, skipping...");
            return; // Board is already initialized, skip initialization
        }
        // Set the flag to true to prevent re-initialization
        this.isInitialized = true;
        // Initialize the PGraphics buffer.
        this.buffer = context.createGraphics(ModelConfig.GRID_WIDTH*ModelConfig.BOX_SIZE, ModelConfig.GRID_HEIGHT*ModelConfig.BOX_SIZE);
        // Load the elements
        preRenderElements();
    }

    /**
     * Pre-rendering help method
     */
    private void preRenderElements(){
        buffer.beginDraw();
        buffer.noStroke();
        for(int y = 0; y < ModelConfig.GRID_HEIGHT; y++) {
            for(int x = 0; x < ModelConfig.GRID_WIDTH; x++) {
                if((x + y) % 2 == 0) {
                    buffer.fill(ColorPalette.BOARD_LIGHT); // Light color for even squares
                } else {
                    buffer.fill(ColorPalette.BOARD_DARK); // Dark color for odd squares
                }
                buffer.rect(x * ModelConfig.BOX_SIZE, y * ModelConfig.BOX_SIZE,
                        ModelConfig.BOX_SIZE, ModelConfig.BOX_SIZE);
            }
        }
        buffer.endDraw();
    }

    /**
     * Draws the object instance
     * @param context The context in which the drawing is to be made
     */
    public void draw(PApplet context) {
        if (!isInitialized) {
            System.err.println("The board is not initialized, skipping render...");
            return; // UI is not initialized, skip drawing
        }
        // Draw the pre-rendered buffer onto the main game canvas
        context.image(buffer, ViewConfig.GAME_AREA_PADDING, ViewConfig.GAME_AREA_PADDING*2 + ViewConfig.TOP_BAR_HEIGHT);
    }
}
