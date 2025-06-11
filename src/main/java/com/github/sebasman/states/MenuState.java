package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.entities.Board;
import com.github.sebasman.core.State;
import com.github.sebasman.utils.GameConfig;
import com.github.sebasman.strategies.FollowFoodStrategy;
import com.github.sebasman.strategies.HumanControlStrategy;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.ui.Button;
import com.github.sebasman.utils.ColorPalette;

/**
 * The menu state of the game, where the player can see the main menu options.
 */
public final class MenuState implements State {
    // This is a singleton class for the menu state of the game.
    private static final MenuState INSTANCE = new MenuState();
    // Menu buttons for human and AI options
    private Button humanButton;
    private Button aiButton;

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
        humanButton = new Button("Play", Assets.playImage, game.width/2, game.height/2);
        aiButton = new Button("Watch AI Play", Assets.watchAIImage, game.width/2, (int) (game.height/2f+GameConfig.BUTTON_HEIGHT*1.5));
    }

    @Override
    public void update(Game game) {
        // No updates needed in menu state
    }

    @Override
    public void draw(Game game, Float interpolation) {
        Board.getInstance().draw(game, null);
        game.fill(0, 0, 0, 200); // Semi-transparent black background
        game.rect(0, 0, game.width, game.height); // Draw a rectangle to cover the background
        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_PRIMARY);
        game.textSize(game.width/9f);
        game.text("VIPER VISION", game.width / 2f, game.height / 4f);

        humanButton.draw(game);
        aiButton.draw(game);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        // This state does not handle key presses, so this method can be empty.
    }

    @Override
    public void mousePressed(Game game) {
        if (humanButton.isMouseOver(game.mouseX, game.mouseY)) {
            game.changeState(new PlayingState(HumanControlStrategy.getInstance()));
        }
        if (aiButton.isMouseOver(game.mouseX, game.mouseY)) {
            game.changeState(new PlayingState(FollowFoodStrategy.getInstance()));
        }
    }
}
