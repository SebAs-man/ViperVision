package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;
import com.github.sebasman.contracts.model.entities.IFoodAPI;

/**
 * Event published when the snake eats its food
 * @param food The instance of the food consumed by the snake
 */
public record FoodEatenEvent(IFoodAPI food) implements IGameEvent {
}
