package com.github.sebasman.states;

import com.github.sebasman.Game;
import com.github.sebasman.core.Board;
import com.github.sebasman.core.GameConfig;
import com.github.sebasman.ui.Assets;
import com.github.sebasman.ui.Button;
import com.github.sebasman.ui.ColorPalette;

/**
 * The paused state of the game, where the player can see a pause message
 * and can resume the game by pressing 'Space' or 'p'.
 */
public final class PausedState implements State{
    // This is a singleton class for the paused state of the game.
    private static final PausedState INSTANCE = new PausedState();
    // A button to return to the main menu
    private Button menuButton;

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
        this.menuButton = new Button("Main Menu", Assets.homeImage, game.width/2, (int) (game.height/1.15));
    }

    @Override
    public void update(Game game) {
        // The game is paused, so don't update anything.
    }

    @Override
    public void draw(Game game, float interpolation) {
        Board.getInstance().draw(game);
        game.getSnake().draw(game);
        game.getFood().draw(game);

        game.fill(255, 255, 255, 175);
        game.rect(0, 0, game.width, game.height);

        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_SECONDARY);
        game.textSize(game.width/12f);
        game.text("PAUSE", game.width / 2f, game.height/2f);

        game.textFont(Assets.textFont);
        game.textSize(game.width/24f);
        game.text("Press 'p' or SPACE to continue", game.width / 2f, game.height/7.5f);

        this.menuButton.draw(game);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        if(Character.toLowerCase(game.key) == 'p' || game.key == ' ') {
            game.setState(PlayingState.getInstance());
        }
    }

    @Override
    public void mousePressed(Game game) {
        if(this.menuButton.isMouseOver(game.mouseX, game.mouseY)) {
            game.setState(MenuState.getInstance());
        }
    }
}
