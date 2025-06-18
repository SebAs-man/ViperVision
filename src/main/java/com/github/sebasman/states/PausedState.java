package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.engine.State;
import com.github.sebasman.ui.GameWorldRenderer;
import com.github.sebasman.ui.UiManager;
import com.github.sebasman.core.interfaces.ui.Layout;
import com.github.sebasman.ui.layouts.VerticalLayout;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.ui.components.Button;
import com.github.sebasman.utils.ColorPalette;
import com.github.sebasman.utils.GameConfig;

/**
 * The paused state of the game, where the player can see a pause message
 * and can resume the game by pressing 'Space' or 'p'.
 */
public final class PausedState implements State {
    // This is a singleton class for the paused state of the game.
    private static final State INSTANCE = new PausedState();
    // List of UI components to be displayed in the paused state
    private UiManager uiManager;

    /**
     * Private constructor to prevent instantiation.
     */
    private PausedState() {}

    /**
     * Returns the singleton instance of the PausedGame state.
     * @return the instance of PausedGame
     */
    public static State getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("Paused game. Press 'p' or 'Space' to continue.");
        if(this.uiManager == null) {
            this.uiManager = this.buildUi(game);
        }
    }

    @Override
    public void onExit(Game game) {

    }

    @Override
    public void update(Game game) {
        if(this.uiManager != null) {
            this.uiManager.update(game);
        }
    }

    @Override
    public void gameTickUpdate(Game game) {
        // This state does not update the game logic, it only displays the pause message.
    }

    @Override
    public void draw(Game game, Float interpolation) {
        // Draw static elements
        game.getStaticElementsRender().render(game);
        GameWorldRenderer.getInstance().render(game, 0f);
        // Draw the pause message
        game.fill(255, 255, 255, 160);
        game.rect(0, 0, game.width, game.height);
        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_SECONDARY);
        game.textSize(game.width/12f);
        game.text("PAUSE", game.width / 2f, game.height/2f);
        game.textFont(Assets.textFont);
        game.textSize(game.width/24f);
        game.text("Press 'p' or SPACE to continue", game.width / 2f, game.height/7.5f);
        // Draw the UI components
        if(this.uiManager != null) {
            this.uiManager.draw(game);
        }
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        if(Character.toLowerCase(game.key) == 'p' || game.key == ' ') {
            game.popState();
        }
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        if(this.uiManager != null) {
            this.uiManager.handleMousePress(mouseX, mouseY);
        }
    }

    /**
     * Builds the UI for the paused state of the game.
     * @param game the game instance to build the UI for
     * @return the UiManager containing the UI components for the paused state
     */
    private UiManager buildUi(Game game) {
        UiManager uiManager = new UiManager();

        Layout menuLayout = new VerticalLayout((game.width/2) - (GameConfig.COMPONENT_WIDTH/2), (int) (game.height/1.25));

        menuLayout.add(new Button("Main Menu", Assets.homeImage,
                () -> {
                    game.popState();
                    game.changeState(MenuState.getInstance());
                }));

        uiManager.addLayout(menuLayout);
        return uiManager;
    }
}
