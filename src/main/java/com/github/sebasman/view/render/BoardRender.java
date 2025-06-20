package com.github.sebasman.view.render;

import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.view.config.ColorPalette;
import processing.core.PApplet;

/**
 * Specific renderer for the board. Knows how to draw a Board object
 */
public class BoardRender {
    /**
     * Draws the object instance
     * @param context The context in which the drawing is to be made
     */
    public void draw(PApplet context) {
        context.pushStyle();
        context.noStroke();
        for(int y = 0; y < ModelConfig.GRID_HEIGHT; y++) {
            for(int x = 0; x < ModelConfig.GRID_WIDTH; x++) {
                if((x + y) % 2 == 0) {
                    context.fill(ColorPalette.BOARD_LIGHT); // Light color for even squares
                } else {
                    context.fill(ColorPalette.BOARD_DARK); // Dark color for odd squares
                }
                context.rect(x * ModelConfig.BOX_SIZE, y * ModelConfig.BOX_SIZE,
                        ModelConfig.BOX_SIZE, ModelConfig.BOX_SIZE);
            }
        }
        context.popStyle();
    }
}
