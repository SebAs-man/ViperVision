package com.github.sebasman.strategies;

import com.github.sebasman.Direction;
import com.github.sebasman.Game;
import com.github.sebasman.Snake;
import processing.core.PConstants;

/**
 * A strategy for controlling the snake using human input via keyboard.
 * This strategy allows the player to control the snake's direction
 * by pressing the arrow keys.
 */
public class HumanControlStrategy implements ControlStrategy {
    // Singleton instance of the HumanControlStrategy
    private static final HumanControlStrategy INSTANCE = new HumanControlStrategy();

    /**
     * Private constructor to enforce a singleton pattern.
     */
    private HumanControlStrategy() {}

    /**
     * Returns the singleton instance of the HumanControlStrategy.
     * @return The singleton instance of HumanControlStrategy.
     */
    public static HumanControlStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public void update(Game game, Snake snake) {
        // The human control does not need to do anything in the update loop.
        // All logic is reactive to keyboard events.
    }

    @Override
    public void keyPressed(Game game, Snake snake, int keyCode) {
        if (keyCode == PConstants.UP) {
            snake.bufferDirection(Direction.UP);
        } else if (keyCode == PConstants.DOWN) {
            snake.bufferDirection(Direction.DOWN);
        } else if (keyCode == PConstants.LEFT) {
            snake.bufferDirection(Direction.LEFT);
        } else if (keyCode == PConstants.RIGHT) {
            snake.bufferDirection(Direction.RIGHT);
        }
    }
}
