package com.github.sebasman.presenter.states;

import com.github.sebasman.contracts.configuration.IConfigParameter;
import com.github.sebasman.contracts.presenter.IHUDController;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.presenter.IUiProvider;
import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.presenter.listeners.HUDController;
import com.github.sebasman.contracts.presenter.IControlStrategy;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.contracts.view.IUiComponent;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.components.Button;
import com.github.sebasman.view.components.ComponentFactory;
import com.github.sebasman.view.config.ViewConfig;
import com.github.sebasman.view.render.GameUiStatic;
import com.github.sebasman.view.render.GameWorldRenderer;
import com.github.sebasman.view.UiManager;
import com.github.sebasman.contracts.view.ILayout;
import com.github.sebasman.view.layout.VerticalLayout;
import com.github.sebasman.view.render.HUDRenderer;
import processing.core.PApplet;
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
    private IHUDController hudController;

    /**
     * Constructor for PreparingState.
     * @param strategy the control strategy to be used in this state.
     */
    public PreparingState(IControlStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Control strategy cannot be null");
        this.uiManager = new UiManager();
    }

    @Override
    public void onEnter(IGameContext game) {
        // Start a new game session.
        game.startNewSession();
        // Saves the strategy to be used in this session in the user's profile.
        game.getProfile().setLastPlayedStrategy(this.strategy);
        // Build the UI for this state.
        this.buildUi(game);
        this.hudController = new HUDController(game.getSession().getScore(), game.getProfile().getHighScore());
    }

    @Override
    public void onExit(IGameContext game) {

    }

    @Override
    public void update(IGameContext game) {
        this.uiManager.update(game.getRenderer());
    }

    @Override
    public void gameTickUpdate(IGameContext game) {
        // No game tick updates needed in preparing state
    }

    @Override
    public void draw(IGameContext game, Float interpolation) {
        PApplet renderer = game.getRenderer();
        GameUiStatic.getInstance().render(renderer);
        GameWorldRenderer.getInstance().render(game, 0f);
        HUDRenderer.getInstance().render(renderer, this.hudController);

        renderer.pushStyle();
        renderer.rectMode(PConstants.CENTER);
        renderer.imageMode(PConstants.CENTER);
        renderer.fill(0, 0, 0, 215); // Semi-transparent black background
        renderer.rect( (renderer.width - ViewConfig.SIDE_PANEL_WIDTH)/2f, renderer.height/3f, ModelConfig.BOX_SIZE*3.5f, ModelConfig.BOX_SIZE*3, 16); // Draw a rectangle to cover the background
        renderer.image(Assets.commandImage, (renderer.width - ViewConfig.SIDE_PANEL_WIDTH)/2f,  renderer.height/3f, ModelConfig.BOX_SIZE*3.5f, ModelConfig.BOX_SIZE*3);
        renderer.popStyle();
        // Draw the UI components
        this.uiManager.draw(renderer);
    }

    @Override
    public void keyPressed(IGameContext game, int keyCode) {
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
    private void buildUi(IGameContext game) {
        PApplet renderer = game.getRenderer();
        List<IConfigParameter> parameters = List.of();
        if(this.strategy instanceof IUiProvider){
            parameters = ((IUiProvider)this.strategy).getConfigurationParameters();
        }
        if(!parameters.isEmpty()){
            ComponentFactory factory = new ComponentFactory();
            ILayout sidePanel = new VerticalLayout(renderer.width - ViewConfig.SIDE_PANEL_WIDTH,
                    ViewConfig.GAME_AREA_PADDING*2);
            for(IConfigParameter param : parameters){
                IUiComponent component = param.accept(factory);
                sidePanel.add(component);
            }
            this.uiManager.addLayout(sidePanel);
        }

        ILayout centerLayout = new VerticalLayout(renderer.width - ViewConfig.SIDE_PANEL_WIDTH,
                renderer.height-ViewConfig.GAME_AREA_PADDING*2-ViewConfig.BUTTON_HEIGHT);
        centerLayout.add(new Button("¡Start!", null,
                () -> game.changeState(new PlayingState(this.strategy))));
        this.uiManager.addLayout(centerLayout);
    }
}
