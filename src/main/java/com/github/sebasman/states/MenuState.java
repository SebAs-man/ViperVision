package com.github.sebasman.states;

import com.github.sebasman.Game;

/**
 * The menu state of the game, where the player can see the main menu options.
 */
public class MenuState implements State {
    // This is a singleton class for the menu state of the game.
    private static final MenuState INSTANCE = new MenuState();

    /**
     * Private constructor to prevent instantiation.
     */
    private MenuState() {}

    /**
     * Returns the singleton instance of the MenuState.
     * @return the instance of MenuState
     */
    public static MenuState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(Game game) {

    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void draw(Game game) {

    }

    @Override
    public void keyPressed(Game game, int keyCode) {

    }
}
