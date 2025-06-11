package com.github.sebasman.states;

import com.github.sebasman.core.Board;
import com.github.sebasman.core.Food;
import com.github.sebasman.Game;
import com.github.sebasman.core.Snake;
import com.github.sebasman.strategies.ControlStrategy;

import java.util.Objects;

/**
 * The playing state of the game, where the player controls the snake and interacts with food.
 */
public final class PlayingState implements State{
    // This is a singleton class for the playing state of the game.
    private static final PlayingState INSTANCE = new PlayingState();
    // The control strategy for handling user input.
    private ControlStrategy controlStrategy;

    /**
     * Private constructor to prevent instantiation.
     */
    private PlayingState() {}

    /**
     * Returns the singleton instance of the PlayingState.
     * @return the instance of PlayingState
     */
    public static PlayingState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("Game is running :)");
    }

    @Override
    public void update(Game game) {
        this.controlStrategy.update(game, game.getSnake());
        // Update the snake's position based on the current direction.
        game.getSnake().update();
        checkCollisions(game);
    }

    @Override
    public void draw(Game game, float interpolation) {
        game.background(0);
        Board.getInstance().draw(game);
        game.getSnake().draw(game, interpolation);
        game.getFood().draw(game);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        if (Character.toLowerCase(game.key) == 'p' || game.key == ' ') {
            game.setState(PausedState.getInstance());
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
        Snake snake = game.getSnake();
        Food food = game.getFood();

        if (snake.checkCollisionWithWall() || snake.checkCollisionWithSelf()) {
            game.setState(GameOverState.getInstance());
            return;
        }

        if (snake.getHead().equals(food.getPosition())) {
            snake.grow();
            food.spawn(snake.getBodySet());
        }
    }

    // --- Setters ---

    /**
     * Sets the control strategy for handling user input in the game.
     * @param controlStrategy the control strategy to set
     */
    public void setControlStrategy(ControlStrategy controlStrategy) {
        Objects.requireNonNull(controlStrategy, "Control strategy cannot be null");
        this.controlStrategy = controlStrategy;
    }
}
