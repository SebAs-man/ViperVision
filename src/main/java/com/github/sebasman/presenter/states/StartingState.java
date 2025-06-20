package com.github.sebasman.presenter.states;

import com.github.sebasman.view.GameView;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.assets.ColorPalette;

/**
 * The starting state of the game, where the player is prompted to start the game.
 */
public final class StartingState implements IState {
    // This is a singleton class for the starting state of the game.
    private static final IState INSTANCE = new StartingState();

    /**
     * Private constructor to prevent instantiation.
     */
    private StartingState() {}

    /**
     * Returns the singleton instance of the StartingState.
     * @return the instance of StartingState
     */
    public static IState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(GameView game) {
        System.out.println("Press any key to start the game!");
    }

    @Override
    public void onExit(GameView game) {
        // This state does not requite any action on exit.
    }

    @Override
    public void update(GameView game) {
        // This state does not require any updates, so this method can be empty.
    }

    @Override
    public void gameTickUpdate(GameView game) {
        // This state does not require any game tick updates, so this method can be empty.
    }

    @Override
    public void draw(GameView game, Float interpolation) {
        // Draw the background
        game.fill(0, 0, 0, 125); // Semi-transparent black background
        game.image(Assets.backgroundPortalImage, 0, 0, game.width, game.height);
        game.rect(0, 0, game.width, game.height); // Draw a rectangle to cover the background
        // Draw the title and instructions
        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_PRIMARY);
        game.textSize(game.width/9f);
        game.text("VIPER VISION", game.width / 2f, game.height / 2f);
        game.textFont(Assets.textFont);
        game.textSize(game.width/21f);
        game.text("Press any key for start", game.width / 2f, game.height / 1.15f);
    }

    @Override
    public void keyPressed(GameView game, int keyCode) {
        game.changeState(MenuState.getInstance());
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        // This state does not handle mouse presses, so this method can be empty.
    }
}
