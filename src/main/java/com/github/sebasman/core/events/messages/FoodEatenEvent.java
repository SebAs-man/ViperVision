package com.github.sebasman.core.events.messages;

import com.github.sebasman.core.events.GameEvent;
import com.github.sebasman.core.interfaces.model.FoodAPI;
import com.github.sebasman.core.interfaces.model.SnakeAPI;

/**
 * Event that is published when the food has been consumed.
 */
public class FoodEatenEvent implements GameEvent {
    public FoodAPI food;
    public SnakeAPI snake;

    /**
     * Constructor of the message
     * @param food the food consumed
     * @param snake the snake in the game
     */
    public FoodEatenEvent(FoodAPI food, SnakeAPI snake) {
        this.food = food;
        this.snake = snake;
    }
}
