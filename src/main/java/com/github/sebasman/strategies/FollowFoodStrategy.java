package com.github.sebasman.strategies;

import com.github.sebasman.core.SnakeAPI;
import com.github.sebasman.core.vo.Direction;
import com.github.sebasman.core.Game;
import com.github.sebasman.core.vo.Position;
import com.github.sebasman.core.ControlStrategy;

/**
 * A strategy for controlling the snake to follow the food.
 */
public final class FollowFoodStrategy implements ControlStrategy {
    // Singleton instance of FollowFoodStrategy
    private static final FollowFoodStrategy INSTANCE = new FollowFoodStrategy();

    /**
     * Private constructor to prevent instantiation.
     */
    private FollowFoodStrategy() {}

    /**
     * Returns the singleton instance of FollowFoodStrategy.
     * @return The singleton instance of FollowFoodStrategy.
     */
    public  static FollowFoodStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public void update(Game game, SnakeAPI snake) {
        Position head = snake.getHead();
        Position food = game.getFood().getPosition();

        int dx = food.x() - head.x();
        int dy = food.y() - head.y();

        if (Math.abs(dx) > Math.abs(dy)) {
            // If the horizontal distance is greater, prioritize horizontal movement
            if (dx > 0) {
                snake.bufferDirection(Direction.RIGHT);
            } else {
                snake.bufferDirection(Direction.LEFT);
            }
        } else {
            // If the vertical distance is greater, prioritize vertical movement
            if (dy > 0) {
                snake.bufferDirection(Direction.DOWN);
            } else {
                snake.bufferDirection(Direction.UP);
            }
        }
    }

    @Override
    public void keyPressed(Game game, SnakeAPI snake, int keyCode) {
        // The AI doesn't respond to the keyboard, so this method is empty.
    }
}
