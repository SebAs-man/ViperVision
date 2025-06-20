package com.github.sebasman.model;

import com.github.sebasman.contracts.model.IDrawable;
import com.github.sebasman.GameConfig;
import com.github.sebasman.view.assets.ColorPalette;
import processing.core.PApplet;

/**
 * The Board class represents the game board and is responsible for drawing the grid.
 * It implements the Drawable interface to allow for custom drawing behavior.
 */
public class Board implements IDrawable {
    // Singleton instance of the Board class
    private static final Board instance = new Board();

    /**
     * Private constructor to prevent instantiation.
     */
    private Board() {}

    /**
     * Returns the singleton instance of the Board class.
     * @return the instance of Board
     */
    public static Board getInstance() {
        return instance;
    }

    @Override
    public void draw(PApplet context, Float interpolation) {
        context.pushStyle();
        context.noStroke();
        for(int y = 0; y < GameConfig.GRID_HEIGHT; y++) {
            for(int x = 0; x < GameConfig.GRID_WIDTH; x++) {
                if((x + y) % 2 == 0) {
                    context.fill(ColorPalette.BOARD_LIGHT); // Light color for even squares
                } else {
                    context.fill(ColorPalette.BOARD_DARK); // Dark color for odd squares
                }
                context.rect(x * GameConfig.BOX_SIZE, y * GameConfig.BOX_SIZE,
                           GameConfig.BOX_SIZE, GameConfig.BOX_SIZE);
            }
        }
        context.popStyle();
    }
}
