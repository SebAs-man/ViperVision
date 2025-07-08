package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;
import com.github.sebasman.contracts.model.IFoodAPI;
import com.github.sebasman.contracts.model.ISnakeAPI;

/**
 * Event published when the snake eats its food
 * @param food The instance of the food consumed by the snake
 * @param snake The instance of the snake
 */
public record FoodEatenEvent(IFoodAPI food, ISnakeAPI snake) implements IGameEvent {
}
