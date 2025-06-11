package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.State;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.utils.ColorPalette;

/**
 * The starting state of the game, where the player is prompted to start the game.
 */
public final class StartingState implements State {
    // This is a singleton class for the starting state of the game.
    private static final StartingState INSTANCE = new StartingState();

    /**
     * Private constructor to prevent instantiation.
     */
    private StartingState() {}

    /**
     * Returns the singleton instance of the StartingState.
     * @return the instance of StartingState
     */
    public static StartingState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("Press any key to start the game!");
    }

    @Override
    public void update(Game game) {
        // This state does not require any updates, so this method can be empty.
    }

    @Override
    public void draw(Game game, Float interpolation) {
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
        game.text("Press any key for start", game.width / 2f, game.height / 1.25f);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        game.changeState(MenuState.getInstance());
    }

    @Override
    public void mousePressed(Game game) {
        // This state does not handle mouse presses, so this method can be empty.
    }
}
