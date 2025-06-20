package com.github.sebasman.presenter.states;

import com.github.sebasman.contracts.view.IUiProvider;
import com.github.sebasman.presenter.HUDController;
import com.github.sebasman.view.GameView;
import com.github.sebasman.contracts.presenter.IControlStrategy;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.contracts.view.IUiComponent;
import com.github.sebasman.view.render.GameUiStatic;
import com.github.sebasman.view.render.GameWorldRenderer;
import com.github.sebasman.view.UiManager;
import com.github.sebasman.contracts.view.ILayout;
import com.github.sebasman.view.layout.VerticalLayout;
import com.github.sebasman.GameConfig;
import com.github.sebasman.view.render.HUDRenderer;
import processing.core.PConstants;

import java.util.List;
import java.util.Objects;

/**
 * PreparingState is a game state that represents the preparation phase before the game starts.
 */
public final class PreparingState implements IState {
    // The strategy used for controlling the snake in this state.
    private final IControlStrategy strategy;
    // The UI manager for handling user interface elements.
    private final UiManager uiManager;
    // Game messages coordinator
    private HUDController hudController;

    /**
     * Constructor for PreparingState.
     * @param strategy the control strategy to be used in this state.
     */
    public PreparingState(IControlStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Control strategy cannot be null");
        this.uiManager = new UiManager();
    }

    @Override
    public void onEnter(GameView game) {
        // Start a new game session.
        game.startNewSession();
        // Saves the strategy to be used in this session in the user's profile.
        game.getProfile().setLastPlayedStrategy(this.strategy);
        // Build the UI for this state.
        this.buildUi(game);
        this.hudController = new HUDController(game.getSession().getScore(), game.getProfile().getHighScore());
    }

    @Override
    public void onExit(GameView game) {

    }

    @Override
    public void update(GameView game) {
        this.uiManager.update(game);
    }

    @Override
    public void gameTickUpdate(GameView game) {
        // No game tick updates needed in preparing state
    }

    @Override
    public void draw(GameView game, Float interpolation) {
        GameUiStatic.getInstance().render(game);
        GameWorldRenderer.getInstance().render(game, 0f);
        HUDRenderer.getInstance().render(game, this.hudController);

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
    public void keyPressed(GameView game, int keyCode) {
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
    private void buildUi(GameView game) {
        List<IUiComponent> strategyComponents = List.of();
        if(this.strategy instanceof IUiProvider){
            strategyComponents = ((IUiProvider)this.strategy).getUiComponents();
        }
        if(!strategyComponents.isEmpty()){
            ILayout sidePanel = new VerticalLayout(game.width - GameConfig.SIDE_PANEL_WIDTH,
                    GameConfig.GAME_AREA_PADDING*2);
            strategyComponents.forEach(sidePanel::add);
            this.uiManager.addLayout(sidePanel);
        }
    }
}
