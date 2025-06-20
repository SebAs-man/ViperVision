package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;
import com.github.sebasman.contracts.model.IFoodAPI;
import com.github.sebasman.contracts.model.ISnakeAPI;

/**
 *
 * @param food
 * @param snake
 */
public record FoodEatenEvent(IFoodAPI food, ISnakeAPI snake) implements IGameEvent {
}
