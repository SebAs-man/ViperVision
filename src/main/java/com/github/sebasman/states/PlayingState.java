package com.github.sebasman.states;

import com.github.sebasman.core.*;
import com.github.sebasman.core.interfaces.engine.ControlStrategy;
import com.github.sebasman.core.interfaces.engine.State;
import com.github.sebasman.core.interfaces.model.FoodAPI;
import com.github.sebasman.core.interfaces.model.SnakeAPI;
import com.github.sebasman.ui.GameUiDynamic;

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
        System.out.println("¡Good Look!");
    }

    @Override
    public void update(Game game) {
        // This state does not require updates, so this method can be empty.
    }

    @Override
    public void gameTickUpdate(Game game) {
        this.controlStrategy.update(game, game.getSnake());
        // Update the snake's position based on the current direction.
        game.getSnake().update();
        checkCollisions(game);
    }

    @Override
    public void draw(Game game, Float interpolation) {
        game.getStaticElementsRender().render(game, interpolation);
        GameUiDynamic.getInstance().render(game, interpolation);
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
    public void mousePressed(int mouseX, int mouseY) {
        // This state does not handle mouse presses, so this method can be empty.
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
            game.incrementScore(food.getScoreValue());
            snake.grow();
            food.spawn(snake.getBodySet());
        }
    }
}
