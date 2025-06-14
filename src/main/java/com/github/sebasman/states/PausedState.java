package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.engine.State;
import com.github.sebasman.core.interfaces.ui.UiComponent;
import com.github.sebasman.ui.GameUiDynamic;
import com.github.sebasman.ui.layout.VerticalLayout;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.ui.Button;
import com.github.sebasman.utils.ColorPalette;
import com.github.sebasman.utils.GameConfig;
import processing.core.PConstants;

/**
 * The paused state of the game, where the player can see a pause message
 * and can resume the game by pressing 'Space' or 'p'.
 */
public final class PausedState implements State {
    // This is a singleton class for the paused state of the game.
    private static final State INSTANCE = new PausedState();
    // List of UI components to be displayed in the paused state
    private VerticalLayout layout;

    /**
     * Private constructor to prevent instantiation.
     */
    private PausedState() {}

    /**
     * Returns the singleton instance of the PausedGame state.
     * @return the instance of PausedGame
     */
    public static State getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(Game game) {
        System.out.println("Paused game. Press 'p' or 'Space' to continue.");
        int layoutX = GameConfig.CENTER_GAME_X;
        int layoutY = game.height / 2;
        this.layout = new VerticalLayout(layoutX, layoutY);
        this.layout.add(new Button("Main Menu", Assets.homeImage,
                () -> {
                    game.popState();
                    game.changeState(MenuState.getInstance());
                }));
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
        game.getStaticElementsRender().render(game, 0f);
        GameUiDynamic.getInstance().render(game, 0f);
        // Draw the pause message
        game.fill(255, 255, 255, 160);
        game.rect(0, 0, game.width, game.height);
        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_SECONDARY);
        game.textSize(game.width/12f);
        game.text("PAUSE", game.width / 2f, game.height/2f);
        game.textFont(Assets.textFont);
        game.textSize(game.width/24f);
        game.text("Press 'p' or SPACE to continue", game.width / 2f, game.height/7.5f);
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
        if(Character.toLowerCase(game.key) == 'p' || game.key == ' ') {
            game.popState();
        }
    }

    @Override
    public void mousePressed(Game game) {
        if(this.layout != null) {
            this.layout.handleMousePress(game);
        }
    }
}
