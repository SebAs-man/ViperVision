package com.github.sebasman.view.render;

import com.github.sebasman.contracts.model.entities.IFoodAPI;
import com.github.sebasman.model.config.ModelConfig;
import processing.core.PApplet;

import java.util.Set;

/**
 * Singleton renderer specific for food.
 * Knows how to draw a list of food objects, using the
 * specific icon that each food object provides.
 */
public final class FoodRender {
    private static final FoodRender INSTANCE = new FoodRender();

    /**
     * Private constructor to prevent instantiation.
     */
    private FoodRender() {}

    /**
     * Returns the singleton instance of FoodRender.
     * @return The singleton instance of FoodRender.
     */
    public static FoodRender getInstance() {
        return INSTANCE;
    }

    /**
     * Draws all active meals on the board.
     * @param context The PApplet context for drawing operations.
     * @param foods The list of foods to draw.
     */
    public void draw(PApplet context, Set<IFoodAPI> foods) {
        if(foods == null || foods.isEmpty()) return;

        context.pushStyle();
        for(IFoodAPI food : foods) {
            if(food == null || food.getPosition() == null) continue;
            int x = food.getPosition().x() * ModelConfig.BOX_SIZE;
            int y = food.getPosition().y() * ModelConfig.BOX_SIZE;
            context.image(food.getIcon(), x, y, ModelConfig.BOX_SIZE, ModelConfig.BOX_SIZE);
        }
        context.popStyle();
    }
}
