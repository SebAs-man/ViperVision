package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.State;
import com.github.sebasman.utils.GameConfig;
import com.github.sebasman.strategies.FollowFoodStrategy;
import com.github.sebasman.strategies.HumanControlStrategy;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.ui.Button;
import com.github.sebasman.utils.ColorPalette;
import processing.core.PConstants;

/**
 * The menu state of the game, where the player can see the main menu options.
 */
public final class MenuState implements State {
    // This is a singleton class for the menu state of the game.
    private static final State INSTANCE = new MenuState();
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
    public static State getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(Game game) {
        humanButton = new Button("Play", Assets.playImage, (game.width - GameConfig.SIDE_PANEL_WIDTH)/2, game.height/2);
        aiButton = new Button("Watch AI Play", Assets.watchAIImage, (game.width - GameConfig.SIDE_PANEL_WIDTH)/2, (int) (game.height/2f+GameConfig.BUTTON_HEIGHT*1.5));
        game.resetScore();
        game.setFood(null);
        game.setSnake(null);
    }

    @Override
    public void update(Game game) {
        // No updates needed in menu state
    }

    @Override
    public void draw(Game game, Float interpolation) {
        // Draw static elements
        game.getRender().render(game, 0f);
        // Change the cursor based on button hover state
        boolean isHoveringButton = humanButton.isMouseOver(game.mouseX, game.mouseY) || aiButton.isMouseOver(game.mouseX, game.mouseY);
        if(isHoveringButton){
            game.cursor(PConstants.HAND);
        } else{
            game.cursor(PConstants.ARROW);
        }
        // Draw the title
        int gameWidth = game.width - GameConfig.SIDE_PANEL_WIDTH - GameConfig.GAME_AREA_PADDING * 2;
        game.fill(0, 0, 0, 215); // Semi-transparent black background
        game.rect(0, 0, gameWidth, game.height, 16); // Draw a rectangle to cover the background
        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_QUATERNARY);
        game.textSize(gameWidth/9f);
        game.text("Snake Game", gameWidth / 2f, game.height / 4f);
        // Draw the buttons
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
            game.changeState(new PreparingState(HumanControlStrategy.getInstance()));
        }
        if (aiButton.isMouseOver(game.mouseX, game.mouseY)) {
            game.changeState(new PreparingState(FollowFoodStrategy.getInstance()));
        }
    }
}
