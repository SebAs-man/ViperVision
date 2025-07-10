package com.github.sebasman.presenter.states;

import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.view.config.ViewConfig;
import com.github.sebasman.view.UiManager;
import com.github.sebasman.contracts.view.ILayout;
import com.github.sebasman.view.layout.VerticalLayout;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.components.Button;
import com.github.sebasman.view.config.ColorPalette;
import com.github.sebasman.view.render.GameUiStatic;
import com.github.sebasman.view.render.GameWorldRenderer;
import com.github.sebasman.view.render.HUDRenderer;
import com.github.sebasman.view.render.ObstacleRenderer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * The game over the state of the game, where the player sees the game over a message
 * and can restart the game by pressing 'Enter'.
 */
public final class GameOverState implements IState {
    // This is a singleton class for the game over state of the game.
    private static final IState INSTANCE = new GameOverState();
    // List of UI components to be displayed in the game over state
    private UiManager uiManager;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameOverState() {}

    /**
     * Returns the singleton instance of the GameOverState.
     * @return the instance of GameOverState
     */
    public static IState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(IGameContext game) {
        // Lazy initialization of the UI manager
        if(this.uiManager == null) {
            this.uiManager = this.buildUi(game);
        }
    }

    @Override
    public void onExit(IGameContext game) {
        // This state does not require any action on exit.
    }

    @Override
    public void update(IGameContext game) {
        // Delegates the update of the UI (cursor, hover) to the manager.
        if(this.uiManager != null) {
            this.uiManager.update(game.getRenderer());
        }
    }

    @Override
    public void draw(IGameContext game) {
        PApplet renderer = game.getRenderer();
        GameUiStatic.getInstance().render(renderer);
        GameWorldRenderer.getInstance().render(game, null);
        HUDRenderer.getInstance().render(renderer);

        if(game.getSession() != null) {
            ObstacleRenderer.getInstance().draw(renderer, game.getSession().getBoard());
        }
        // Draw the game over background and text
        renderer.pushStyle();
        renderer.fill(0, 0, 0, 215); // Semi-transparent black background
        renderer.rect(0, 0, renderer.width, renderer.height);
        renderer.textFont(Assets.titleFont);
        renderer.fill(ColorPalette.TEXT_QUATERNARY);
        renderer.textSize(renderer.width/9f);
        renderer.text("GAME OVER", renderer.width/2f, renderer.height/4f);
        renderer.popStyle();
        // Draw the UI components
        if(this.uiManager != null) {
            this.uiManager.draw(renderer);
        }
    }

    @Override
    public void keyPressed(IGameContext game, int keyCode) {
        // No key actions needed in game over state
    }

    @Override
    public void mousePressed(IGameContext game, int mouseX, int mouseY) {
        PApplet renderer = game.getRenderer();
        if(renderer.mouseButton == PConstants.LEFT && this.uiManager != null) {
            this.uiManager.handleMousePress(mouseX, mouseY);
        }
    }

    /**
     * Builds the UI for the game over state.
     * @param game the game instance to build the UI for
     * @return the UiManager containing the game over UI components
     */
    private UiManager buildUi(IGameContext game) {
        UiManager uiManager = new UiManager();
        PApplet renderer = game.getRenderer();

        ILayout menuLayout = new VerticalLayout(
                (renderer.width/2) - (ViewConfig.BUTTON_WIDTH /2),
                renderer.height/2);

        menuLayout.add(new Button("Retry", Assets.retryImage,
                () -> game.changeState(
                        new PreparingState(game.getProfile().getLastPlayedStrategy()))));
        menuLayout.add(new Button("Menu", Assets.homeImage,
                () -> game.changeState(MenuState.getInstance())));

       uiManager.addLayout(menuLayout);
       return uiManager;
    }
}
