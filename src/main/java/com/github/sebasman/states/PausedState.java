package com.github.sebasman.states;

import com.github.sebasman.Game;

/**
 * The paused state of the game, where the player can see a pause message
 * and can resume the game by pressing 'Space' or 'p'.
 */
public class PausedState implements State{
    // This is a singleton class for the paused state of the game.
    private static final PausedState INSTANCE = new PausedState();

    /**
     * Private constructor to prevent instantiation.
     */
    private PausedState() {}

    /**
     * Returns the singleton instance of the PausedGame state.
     * @return the instance of PausedGame
     */
    public static PausedState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("Game is paused. Press 'Space' or 'p' to resume.");
    }

    @Override
    public void update(Game game) {
        // The game is paused, so don't update anything.
    }

    @Override
    public void draw(Game game) {
        game.getSnake().draw();
        game.getFood().draw();

        game.fill(0, 0, 0, 150);
        game.rect(0, 0, game.width, game.height);

        game.fill(255);
        game.textSize(50);
        game.text("PAUSE", game.width / 2f, game.height / 2f);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        if(Character.toLowerCase(game.key) == 'p' || game.key == ' ') {
            game.setState(PlayingState.getInstance());
        }
    }
}
