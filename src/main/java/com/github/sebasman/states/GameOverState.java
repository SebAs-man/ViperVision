package com.github.sebasman.states;

import com.github.sebasman.Game;
import processing.core.PConstants;

/**
 * The game over the state of the game, where the player sees the game over a message
 * and can restart the game by pressing 'Enter'.
 */
public class GameOverState implements State{
    // This is a singleton class for the game over state of the game.
    private static final GameOverState INSTANCE = new GameOverState();

    /**
     * Private constructor to prevent instantiation.
     */
    private GameOverState() {}

    /**
     * Returns the singleton instance of the GameOverState.
     * @return the instance of GameOverState
     */
    public  static GameOverState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("GAME OVER...");
    }

    @Override
    public void update(Game game) {
        // No updates needed in game over state
    }

    @Override
    public void draw(Game game) {
        game.getSnake().draw();
        game.getFood().draw();
        // Draw a semi-transparent red overlay
        game.fill(80, 0, 0, 150);
        game.rect(0, 0, game.width, game.height);

        game.fill(255, 0, 0);
        game.textSize(50);
        game.text("GAME OVER", game.width / 2f, game.height / 3f);

        game.fill(255);
        game.textSize(20);
        game.text("Press 'Enter' to restart", game.width / 2f, game.height / 2f);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        if (keyCode == PConstants.ENTER) {
            game.resetGame(); // Reset the game state
            game.setState(PlayingState.getInstance());
        }
    }
}
