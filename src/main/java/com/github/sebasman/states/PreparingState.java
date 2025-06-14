package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.engine.ControlStrategy;
import com.github.sebasman.core.interfaces.gamemodel.FoodAPI;
import com.github.sebasman.core.interfaces.gamemodel.SnakeAPI;
import com.github.sebasman.core.interfaces.engine.State;
import com.github.sebasman.core.vo.Position;
import com.github.sebasman.entities.FoodImpl;
import com.github.sebasman.entities.SnakeImpl;
import com.github.sebasman.strategies.HumanControlStrategy;
import com.github.sebasman.ui.GameUiDynamic;
import com.github.sebasman.utils.GameConfig;
import processing.core.PConstants;

import java.util.Objects;

/**
 * PreparingState is a game state that represents the preparation phase before the game starts.
 */
public class PreparingState implements State {
    // The strategy used for controlling the snake in this state.
    private final ControlStrategy strategy;

    public PreparingState(ControlStrategy strategy) {
        Objects.requireNonNull(strategy, "Control strategy cannot be null");
        this.strategy = strategy;
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("Entering PreparingState with control strategy: " + this.strategy.getClass().getSimpleName());
        // Initialize the game components such as the snake and food.
        SnakeAPI snake = new SnakeImpl(new Position(GameConfig.GRID_WIDTH/4, GameConfig.GRID_HEIGHT/2), 3);
        FoodAPI food = new FoodImpl(1, new Position(3*GameConfig.GRID_WIDTH/4, GameConfig.GRID_HEIGHT/2));
        // Register the entities in the game instance.
        game.setSnake(snake);
        game.setFood(food);
        // Reset the game state and set the last played strategy.
        game.resetScore();
        game.setLastPlayedStrategy(this.strategy);
        game.cursor(PConstants.ARROW);
    }

    @Override
    public void update(Game game) {
        // No updates needed in preparing state
    }

    @Override
    public void draw(Game game, Float interpolation) {
        game.getStaticElementsRender().render(game, 0f);
        GameUiDynamic.getInstance().render(game, 0f);

        int gameWidth = (game.width - GameConfig.SIDE_PANEL_WIDTH);
        game.pushStyle();
        game.rectMode(PConstants.CENTER);
        game.fill(0, 0, 0, 215); // Semi-transparent black background
        game.rect(gameWidth/2f, game.height/3f, GameConfig.BOX_SIZE*3.5f, GameConfig.BOX_SIZE*3, 16); // Draw a rectangle to cover the background
        game.popStyle();
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        if (strategy instanceof HumanControlStrategy) {
            if (keyCode == PConstants.UP || keyCode == PConstants.DOWN || keyCode == PConstants.LEFT || keyCode == PConstants.RIGHT) {
                game.changeState(new PlayingState(this.strategy));
            }
        }
    }

    @Override
    public void mousePressed(Game game) {
        // No mouse interaction in this state, but can be overridden if needed.
    }
}
