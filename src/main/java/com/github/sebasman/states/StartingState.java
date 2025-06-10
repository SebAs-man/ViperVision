package com.github.sebasman.states;

import com.github.sebasman.Game;

/**
 * The starting state of the game, where the player is prompted to start the game.
 */
public class StartingState implements State {
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
    public void draw(Game game) {
        game.background(25, 25, 112);
        game.fill(255);
        game.textSize(50);
        game.text("VIPER VISION", game.width / 2f, game.height / 3f);
        game.textSize(20);
        game.text("Press any key for start", game.width / 2f, game.height / 2f);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        game.setState(PlayingState.getInstance());
    }
}
