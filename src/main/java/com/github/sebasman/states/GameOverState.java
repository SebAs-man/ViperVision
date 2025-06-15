package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.engine.State;
import com.github.sebasman.ui.GameUiDynamic;
import com.github.sebasman.ui.UiManager;
import com.github.sebasman.core.interfaces.ui.Layout;
import com.github.sebasman.ui.layouts.VerticalLayout;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.ui.components.Button;
import com.github.sebasman.utils.ColorPalette;
import com.github.sebasman.utils.GameConfig;

/**
 * The game over the state of the game, where the player sees the game over a message
 * and can restart the game by pressing 'Enter'.
 */
public final class GameOverState implements State {
    // This is a singleton class for the game over state of the game.
    private static final State INSTANCE = new GameOverState();
    // List of UI components to be displayed in the game over state
    private final UiManager uiManager;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameOverState() {
        this.uiManager = new UiManager();
    }

    /**
     * Returns the singleton instance of the GameOverState.
     * @return the instance of GameOverState
     */
    public static State getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("Game Over!");
        this.uiManager.clear();
        Layout menuLayout = new VerticalLayout((game.width/2) - (GameConfig.COMPONENT_WIDTH/2), game.height/2);
        menuLayout.add(new Button("Retry", Assets.retryImage,
                () -> game.changeState(new PreparingState(game.getLastPlayedStrategy()))));
        menuLayout.add(new Button("Menu", Assets.homeImage,
                () -> game.changeState(MenuState.getInstance())));
        this.uiManager.addLayout(menuLayout);
    }

    @Override
    public void update(Game game) {
        this.uiManager.update(game);
    }

    @Override
    public void gameTickUpdate(Game game) {
        // This state does not require game tick updates, so this method can be empty.
    }

    @Override
    public void draw(Game game, Float interpolation) {
        // Draw static elements
        game.getStaticElementsRender().render(game, null);
        GameUiDynamic.getInstance().render(game, 0f);
        // Draw the game over background and text
        game.fill(0, 0, 0, 215); // Semi-transparent black background
        game.rect(0, 0, game.width, game.height);
        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_QUATERNARY);
        game.textSize(game.width/9f);
        game.text("GAME OVER", game.width/2f, game.height/4f);
        // Draw the buttons
        this.uiManager.draw(game);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        // No key actions needed in game over state
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        this.uiManager.handleMousePress(mouseX, mouseY);
    }
}
