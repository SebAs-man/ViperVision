package com.github.sebasman.contracts.vo;

/**
 * Defines the categories of food to drive game logic, such as determining
 * when to spawn a new batch of food.
 */
public enum FoodCategory {
    //A food item that benefits the player and must be collected
    POSITIVE,
    // A food item that penalizes the player and is optional to collect
    NEGATIVE,
    // A neutral food item that may have unique effects but does not count towards goals
    NEUTRAL
}
