package com.github.sebasman.presenter.strategies;

import com.github.sebasman.contracts.model.ISnakeAPI;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.vo.Direction;
import com.github.sebasman.contracts.presenter.IControlStrategy;
import processing.core.PConstants;

/**
 * A strategy for controlling the snake using human input via keyboard.
 * This strategy allows the player to control the snake's direction
 * by pressing the arrow keys.
 */
public final class HumanControlStrategy implements IControlStrategy {
    /**
     * Builder of a new Strategy.
     */
    public HumanControlStrategy() {}

    @Override
    public void update(IGameContext game, ISnakeAPI snake) {
        // The human control does not need to do anything in the update loop.
        // All logic is reactive to keyboard events.
    }

    @Override
    public void keyPressed(IGameContext game, ISnakeAPI snake, int keyCode) {
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

    @Override
    public boolean isGameStartAction(int keyCode) {
        return keyCode == PConstants.UP || keyCode == PConstants.DOWN ||
                keyCode == PConstants.LEFT || keyCode == PConstants.RIGHT;
    }
}
