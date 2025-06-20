package com.github.sebasman.presenter.states;

import com.github.sebasman.presenter.HUDController;
import com.github.sebasman.view.GameView;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.view.render.GameUiStatic;
import com.github.sebasman.view.render.GameWorldRenderer;
import com.github.sebasman.view.UiManager;
import com.github.sebasman.contracts.view.ILayout;
import com.github.sebasman.view.layout.VerticalLayout;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.components.Button;
import com.github.sebasman.view.assets.ColorPalette;
import com.github.sebasman.GameConfig;
import com.github.sebasman.view.render.HUDRenderer;

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
    public void onEnter(GameView game) {
        this.hudController = new HUDController(game.getSession().getScore(), game.getProfile().getHighScore());
        // Lazy Initialization: The UI is built only the first time you enter pause.
        if(this.uiManager == null) {
            this.uiManager = this.buildUi(game);
        }
    }

    @Override
    public void onExit(GameView game) {
        // No cleaning is required when coming out of the break.
    }

    @Override
    public void update(GameView game) {
        // Delegates the update of the UI (cursor, hover) to the manager.
        if(this.uiManager != null) {
            this.uiManager.update(game);
        }
    }

    @Override
    public void gameTickUpdate(GameView game) {
        // Paused, the game logic is frozen. Nothing is done.
    }

    @Override
    public void draw(GameView game, Float interpolation) {
        // Draws the state of the game below, but static
        GameUiStatic.getInstance().render(game);
        GameWorldRenderer.getInstance().render(game, 0f);
        HUDRenderer.getInstance().render(game, this.hudController);
        // Draw the pause overlay
        game.fill(255, 255, 255, 160);
        game.rect(0, 0, game.width, game.height);
        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_SECONDARY);
        game.textSize(game.width/12f);
        game.text("PAUSE", game.width / 2f, game.height/2f);
        game.textFont(Assets.textFont);
        game.textSize(game.width/24f);
        game.text("Press 'p' or SPACE to continue", game.width / 2f, game.height/7.5f);
        // Draw UI components (buttons)
        if(this.uiManager != null) {
            this.uiManager.draw(game);
        }
    }

    @Override
    public void keyPressed(GameView game, int keyCode) {
        if(Character.toLowerCase(game.key) == 'p' || game.key == ' ') {
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
    private UiManager buildUi(GameView game) {
        UiManager uiManager = new UiManager();

        ILayout menuLayout = new VerticalLayout((game.width/2) - (GameConfig.COMPONENT_WIDTH/2), (int) (game.height/1.25));

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
