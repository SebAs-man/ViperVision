package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.engine.State;
import com.github.sebasman.core.interfaces.ui.UiComponent;
import com.github.sebasman.ui.GameUiDynamic;
import com.github.sebasman.ui.layout.VerticalLayout;
import com.github.sebasman.utils.GameConfig;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.ui.Button;
import com.github.sebasman.utils.ColorPalette;
import processing.core.PConstants;

/**
 * The game over the state of the game, where the player sees the game over a message
 * and can restart the game by pressing 'Enter'.
 */
public final class GameOverState implements State {
    // This is a singleton class for the game over state of the game.
    private static final State INSTANCE = new GameOverState();
    // List of UI components to be displayed in the game over state
    private VerticalLayout layout;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameOverState() {}

    /**
     * Returns the singleton instance of the GameOverState.
     * @return the instance of GameOverState
     */
    public static State getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("Game Over!");
        int layoutX = GameConfig.CENTER_GAME_X;
        int layoutY = game.height / 2;
        this.layout = new VerticalLayout(layoutX, layoutY);
        this.layout.add(new Button("Retry", Assets.retryImage,
                () -> game.changeState(new PreparingState(game.getLastPlayedStrategy()))));
        this.layout.add(new Button("Menu", Assets.homeImage,
                () -> game.changeState(MenuState.getInstance())));
    }

    @Override
    public void update(Game game) {
        if(this.layout != null) {
            this.layout.update();
        }
    }

    @Override
    public void draw(Game game, Float interpolation) {
        // Draw static elements
        game.getStaticElementsRender().render(game, null);
        GameUiDynamic.getInstance().render(game, 0f);
        // Draw the game over background and text
        game.fill(0, 0, 0, 215); // Semi-transparent black background
        game.rect(0, 0, game.width, game.height);
        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_QUATERNARY);
        game.textSize(game.width/9f);
        game.text("GAME OVER", game.width/2f, game.height/4f);
        // Draw the buttons
        boolean isHoveringComponent = false;
        if(this.layout != null) {
            for(UiComponent component : this.layout.getComponents()) {
                if(component.isMouseOver(game.mouseX, game.mouseY)) {
                    isHoveringComponent = true;
                    break;
                }
            }
            this.layout.draw(game);
        }
        game.cursor(isHoveringComponent ? PConstants.HAND : PConstants.ARROW);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        // No key actions needed in game over state
    }

    @Override
    public void mousePressed(Game game) {
        if(this.layout != null) {
            this.layout.handleMousePress(game);
        }
    }
}
