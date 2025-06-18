package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.events.listeners.GameLogicCoordinator;
import com.github.sebasman.core.interfaces.engine.ControlStrategy;
import com.github.sebasman.core.interfaces.model.FoodAPI;
import com.github.sebasman.core.interfaces.model.SnakeAPI;
import com.github.sebasman.core.interfaces.engine.State;
import com.github.sebasman.core.interfaces.ui.UiComponent;
import com.github.sebasman.core.vo.Position;
import com.github.sebasman.entities.FoodImpl;
import com.github.sebasman.entities.SnakeImpl;
import com.github.sebasman.ui.GameWorldRenderer;
import com.github.sebasman.ui.UiManager;
import com.github.sebasman.core.interfaces.ui.Layout;
import com.github.sebasman.ui.layouts.VerticalLayout;
import com.github.sebasman.utils.GameConfig;
import processing.core.PConstants;

import java.util.List;
import java.util.Objects;

/**
 * PreparingState is a game state that represents the preparation phase before the game starts.
 */
public class PreparingState implements State {
    // The strategy used for controlling the snake in this state.
    private final ControlStrategy strategy;
    // The UI manager for handling user interface elements.
    private final UiManager uiManager;

    /**
     * Constructor for PreparingState.
     * @param strategy the control strategy to be used in this state.
     */
    public PreparingState(ControlStrategy strategy) {
        Objects.requireNonNull(strategy, "Control strategy cannot be null");
        this.strategy = strategy;
        this.uiManager = new UiManager();
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("Entering PreparingState with control strategy: " + this.strategy.getClass().getSimpleName());
        this.buildUi(game);
        // Initialize the game components such as the snake and food.
        SnakeAPI snake = new SnakeImpl(new Position(GameConfig.GRID_WIDTH/4, GameConfig.GRID_HEIGHT/2), 3);
        FoodAPI food = new FoodImpl(1, new Position(3*GameConfig.GRID_WIDTH/4, GameConfig.GRID_HEIGHT/2));
        // Register the entities in the game instance.
        game.setSnake(snake);
        game.setFood(food);
        // Reset the game state and set the last played strategy.
        game.resetScore();
        game.setLastPlayedStrategy(this.strategy);
    }

    @Override
    public void onExit(Game game) {

    }

    @Override
    public void update(Game game) {
        this.uiManager.update(game);
    }

    @Override
    public void gameTickUpdate(Game game) {
        // No game tick updates needed in preparing state
    }

    @Override
    public void draw(Game game, Float interpolation) {
        game.getStaticElementsRender().render(game);
        GameWorldRenderer.getInstance().render(game, 0f);

        int gameWidth = (game.width - GameConfig.SIDE_PANEL_WIDTH);
        game.pushStyle();
        game.rectMode(PConstants.CENTER);
        game.fill(0, 0, 0, 215); // Semi-transparent black background
        game.rect(gameWidth/2f, game.height/3f, GameConfig.BOX_SIZE*3.5f, GameConfig.BOX_SIZE*3, 16); // Draw a rectangle to cover the background
        game.popStyle();
        // Draw the UI components
        this.uiManager.draw(game);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        if(this.strategy.isGameStartAction(keyCode)) {
            game.changeState(new PlayingState(this.strategy));
        }
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        this.uiManager.handleMousePress(mouseX, mouseY);
    }

    /**
     * Builds the UI for the preparing state of the game.
     * @param game the game instance to build the UI for
     */
    private void buildUi(Game game) {
        Layout sidePanel = new VerticalLayout(game.width - GameConfig.SIDE_PANEL_WIDTH,
                GameConfig.GAME_AREA_PADDING*2);

        List<UiComponent> componentList = strategy.getSidePanelComponents();
        componentList.forEach(sidePanel::add);

        this.uiManager.addLayout(sidePanel);
    }
}
