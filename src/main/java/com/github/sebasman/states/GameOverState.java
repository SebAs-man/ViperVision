package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.entities.Board;
import com.github.sebasman.core.State;
import com.github.sebasman.utils.GameConfig;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.ui.Button;
import com.github.sebasman.utils.ColorPalette;

/**
 * The game over the state of the game, where the player sees the game over a message
 * and can restart the game by pressing 'Enter'.
 */
public final class GameOverState implements State {

    // This is a singleton class for the game over state of the game.
    private static final GameOverState INSTANCE = new GameOverState();
    // Menu buttons for retry and menu options
    private Button retryButton;
    private Button menuButton;

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
        System.out.println("Game Over!");
        this.retryButton = new Button("Retry", Assets.retryImage, game.width/2, game.height/2);
        this.menuButton = new Button("Menu", Assets.homeImage, game.width/2, (int) (game.height/2f+GameConfig.BUTTON_HEIGHT*1.5));
    }

    @Override
    public void update(Game game) {
        // No updates needed in game over state
    }

    @Override
    public void draw(Game game, Float interpolation) {
        Board.getInstance().draw(game, null);
        game.getSnake().draw(game, 0f);
        game.getFood().draw(game, null);

        game.fill(0, 0, 0, 225); // Semi-transparent black background
        game.rect(0, 0, game.width, game.height);
        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_QUATERNARY);
        game.textSize(game.width/9f);
        game.text("GAME OVER", game.width/2f, game.height/4f);

        this.retryButton.draw(game);
        this.menuButton.draw(game);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        // No key actions needed in game over state
    }

    @Override
    public void mousePressed(Game game) {
        if(this.retryButton.isMouseOver(game.mouseX, game.mouseY)) {
            game.changeState(new PlayingState(game.getLastPlayedStrategy()));
        }
        if(this.menuButton.isMouseOver(game.mouseX, game.mouseY)) {
            game.changeState(MenuState.getInstance()); // Go back to the main menu
        }
    }
}
