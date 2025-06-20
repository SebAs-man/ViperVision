package com.github.sebasman.view.render;

import com.github.sebasman.contracts.model.IFoodAPI;
import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.view.assets.Assets;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Food-specific renderer. Knows how to draw an object
 * that implements IFoodAPI on the screen.
 */
public class FoodRender {
    /**
     * Draws the object instance
     * @param context The context in which the drawing is to be made
     * @param food The instance of the object to be drawn
     */
    public void draw(PApplet context, IFoodAPI food) {
        if(food == null || food.getPosition() == null) return; // Ensure position is set before drawing

        PImage appleImage = Assets.appleImage;
        int x = food.getPosition().x() * ModelConfig.BOX_SIZE;
        int y = food.getPosition().y() * ModelConfig.BOX_SIZE;
        context.image(appleImage, x, y, ModelConfig.BOX_SIZE, ModelConfig.BOX_SIZE);
    }
}
