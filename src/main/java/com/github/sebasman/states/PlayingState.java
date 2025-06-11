package com.github.sebasman.states;

import com.github.sebasman.core.*;
import com.github.sebasman.core.vo.Position;
import com.github.sebasman.entities.Board;
import com.github.sebasman.entities.FoodImpl;
import com.github.sebasman.entities.SnakeImpl;
import com.github.sebasman.utils.GameConfig;

import java.util.Objects;

/**
 * The playing state of the game, where the player controls the snake and interacts with food.
 */
public final class PlayingState implements State {
    // The control strategy for handling user input.
    private final ControlStrategy controlStrategy;

    /**
     * Constructor for the PlayingState.
     * @param controlStrategy the control strategy to use for handling user input
     */
    public PlayingState(ControlStrategy controlStrategy) {
        Objects.requireNonNull(controlStrategy, "Control strategy cannot be null");
        this.controlStrategy = controlStrategy;
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("Entering PlayingState with control strategy: " + this.controlStrategy.getClass().getSimpleName());
        // Initialize the game components such as the snake and food.
        SnakeAPI snake = new SnakeImpl(new Position(GameConfig.GRID_WIDTH/4, GameConfig.GRID_HEIGHT/2), 3);
        FoodAPI food = new FoodImpl();
        food.spawn(snake.getBodySet());
        // Set the snake and food in the game instance.
        game.setSnake(snake);
        game.setFood(food);
        game.setLastPlayedStrategy(this.controlStrategy);
    }

    @Override
    public void update(Game game) {
        this.controlStrategy.update(game, game.getSnake());
        // Update the snake's position based on the current direction.
        game.getSnake().update();
        checkCollisions(game);
    }

    @Override
    public void draw(Game game, Float interpolation) {
        Board.getInstance().draw(game, null);
        game.getSnake().draw(game, interpolation);
        game.getFood().draw(game, null);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        if (Character.toLowerCase(game.key) == 'p' || game.key == ' ') {
            game.pushState(PausedState.getInstance());
            return;
        }
        // Delegate the key press to the control strategy.
        this.controlStrategy.keyPressed(game, game.getSnake(), keyCode);
    }

    @Override
    public void mousePressed(Game game) {
        // No mouse interaction in this state, but can be overridden if needed.
    }

    /**
     * Checks for collisions between the snake and the walls or itself,
     * @param game the current game instance
     */
    private void checkCollisions(Game game) {
        SnakeAPI snake = game.getSnake();
        FoodAPI food = game.getFood();

        if (snake.checkCollisionWithWall() || snake.checkCollisionWithSelf()) {
            game.changeState(GameOverState.getInstance());
            return;
        }

        if (snake.getHead().equals(food.getPosition())) {
            snake.grow();
            food.spawn(snake.getBodySet());
        }
    }
}
