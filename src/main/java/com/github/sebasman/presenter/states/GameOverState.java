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
 * The game over the state of the game, where the player sees the game over a message
 * and can restart the game by pressing 'Enter'.
 */
public final class GameOverState implements IState {
    // This is a singleton class for the game over state of the game.
    private static final IState INSTANCE = new GameOverState();
    // List of UI components to be displayed in the game over state
    private UiManager uiManager;
    // Game messages coordinator
    private HUDController hudController;

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
    public void onEnter(GameView game) {
        this.hudController = new HUDController(game.getSession().getScore(), game.getProfile().getHighScore());
        // Lazy initialization of the UI manager
        if(this.uiManager == null) {
            this.uiManager = this.buildUi(game);
        }
    }

    @Override
    public void onExit(GameView game) {

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
        // This state does not require game tick updates, so this method can be empty.
    }

    @Override
    public void draw(GameView game, Float interpolation) {
        // Draw static elements
        GameUiStatic.getInstance().render(game);
        GameWorldRenderer.getInstance().render(game, 0f);
        HUDRenderer.getInstance().render(game, this.hudController);
        // Draw the game over background and text
        game.fill(0, 0, 0, 215); // Semi-transparent black background
        game.rect(0, 0, game.width, game.height);
        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_QUATERNARY);
        game.textSize(game.width/9f);
        game.text("GAME OVER", game.width/2f, game.height/4f);
        // Draw the UI components
        if(this.uiManager != null) {
            this.uiManager.draw(game);
        }
    }

    @Override
    public void keyPressed(GameView game, int keyCode) {
        // No key actions needed in game over state
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        if(this.uiManager != null) {
            this.uiManager.handleMousePress(mouseX, mouseY);
        }
    }

    /**
     * Builds the UI for the game over state.
     * @param game the game instance to build the UI for
     * @return the UiManager containing the game over UI components
     */
    private UiManager buildUi(GameView game) {
        UiManager uiManager = new UiManager();

        ILayout menuLayout = new VerticalLayout(
                (game.width/2) - (GameConfig.COMPONENT_WIDTH/2),
                game.height/2);

        menuLayout.add(new Button("Retry", Assets.retryImage,
                () -> game.changeState(
                        new PreparingState(game.getProfile().getLastPlayedStrategy()))));
        menuLayout.add(new Button("Menu", Assets.homeImage,
                () -> game.changeState(MenuState.getInstance())));

       uiManager.addLayout(menuLayout);
       return uiManager;
    }
}
