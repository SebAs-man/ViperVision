package com.github.sebasman.presenter.states;

import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.presenter.listeners.HUDController;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.view.config.ViewConfig;
import com.github.sebasman.view.render.GameUiStatic;
import com.github.sebasman.view.render.GameWorldRenderer;
import com.github.sebasman.view.UiManager;
import com.github.sebasman.contracts.view.ILayout;
import com.github.sebasman.view.layout.VerticalLayout;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.components.Button;
import com.github.sebasman.view.assets.ColorPalette;
import com.github.sebasman.view.render.HUDRenderer;
import processing.core.PApplet;

/**
 * The paused state of the game, where the player can see a pause message
 * and can resume the game by pressing 'Space' or 'p'.
 */
public final class PausedState implements IState {
    // This is a singleton class for the paused state of the game.
    private static final IState INSTANCE = new PausedState();
    // List of UI components to be displayed in the paused state
    private UiManager uiManager;
    // Game messages coordinator
    private HUDController hudController;

    /**
     * Private constructor to prevent instantiation.
     */
    private PausedState() {}

    /**
     * Returns the singleton instance of the PausedGame state.
     * @return the instance of PausedGame
     */
    public static IState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(IGameContext game) {
        this.hudController = new HUDController(game.getSession().getScore(), game.getProfile().getHighScore());
        // Lazy Initialization: The UI is built only the first time you enter pause.
        if(this.uiManager == null) {
            this.uiManager = this.buildUi(game);
        }
    }

    @Override
    public void onExit(IGameContext game) {
        // No cleaning is required when coming out of the break.
    }

    @Override
    public void update(IGameContext game) {
        // Delegates the update of the UI (cursor, hover) to the manager.
        if(this.uiManager != null) {
            this.uiManager.update(game.getRenderer());
        }
    }

    @Override
    public void gameTickUpdate(IGameContext game) {
        // Paused, the game logic is frozen. Nothing is done.
    }

    @Override
    public void draw(IGameContext game, Float interpolation) {
        PApplet renderer  = game.getRenderer();
        // Draws the state of the game below, but static
        GameUiStatic.getInstance().render(renderer);
        GameWorldRenderer.getInstance().render(game, 0f);
        HUDRenderer.getInstance().render(renderer, this.hudController);
        // Draw the pause overlay
        renderer.fill(255, 255, 255, 160);
        renderer.rect(0, 0, renderer.width, renderer.height);
        renderer.textFont(Assets.titleFont);
        renderer.fill(ColorPalette.TEXT_SECONDARY);
        renderer.textSize(renderer.width/12f);
        renderer.text("PAUSE", renderer.width / 2f, renderer.height/2f);
        renderer.textFont(Assets.textFont);
        renderer.textSize(renderer.width/24f);
        renderer.text("Press 'p' or SPACE to continue", renderer.width / 2f, renderer.height/7.5f);
        // Draw UI components (buttons)
        if(this.uiManager != null) {
            this.uiManager.draw(renderer);
        }
    }

    @Override
    public void keyPressed(IGameContext game, int keyCode) {
        PApplet renderer = game.getRenderer();
        if(Character.toLowerCase(renderer.key) == 'p' || renderer.key == ' ') {
            game.popState(); // Pops the PausedState off the stack and returns to PlayingState.
        }
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        // Delegates clicks to the UI manager.
        if(this.uiManager != null) {
            this.uiManager.handleMousePress(mouseX, mouseY);
        }
    }

    /**
     * Builds the UI for the paused state of the game.
     * @param game the game instance to build the UI for
     * @return the UiManager containing the UI components for the paused state
     */
    private UiManager buildUi(IGameContext game) {
        UiManager uiManager = new UiManager();
        PApplet  renderer  = game.getRenderer();

        ILayout menuLayout = new VerticalLayout((renderer.width/2) - (ViewConfig.COMPONENT_WIDTH/2),
                (int) (renderer.height/1.25));

        // The "Main Menu" button has a two-step logic:
        // 1. popState(): Pops out the PausedState.
        // 2. changeState(): Replaces the PlayingState with the MenuState.
        menuLayout.add(new Button("Main Menu", Assets.homeImage,
                () -> {
                    game.popState();
                    game.changeState(MenuState.getInstance());
                }));

        uiManager.addLayout(menuLayout);
        return uiManager;
    }
}
