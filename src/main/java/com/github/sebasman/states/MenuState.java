package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.engine.State;
import com.github.sebasman.core.interfaces.ui.UiComponent;
import com.github.sebasman.ui.GameUiDynamic;
import com.github.sebasman.ui.layout.VerticalLayout;
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
    // List of UI components to be displayed in the menu state
    private VerticalLayout layout;

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
        int layoutX = GameConfig.CENTER_GAME_X - (GameConfig.COMPONENT_WIDTH/2);
        int layoutY = game.height / 2;
        this.layout = new VerticalLayout(layoutX, layoutY);
        this.layout.add(new Button("Play", Assets.playImage,
                () -> game.changeState(new PreparingState(HumanControlStrategy.getInstance()))));
        this.layout.add(new Button("Watch AI Play", Assets.watchAIImage,
                () -> game.changeState(new PreparingState(FollowFoodStrategy.getInstance()))));
        game.resetScore();
        game.setFood(null);
        game.setSnake(null);
    }

    @Override
    public void update(Game game) {
        if(layout != null) {
            this.layout.update();
        }
    }

    @Override
    public void draw(Game game, Float interpolation) {
        // Draw static elements
        game.getStaticElementsRender().render(game, 0f);
        GameUiDynamic.getInstance().render(game, 0f);
        // Draw the title
        int gameWidth = game.width - GameConfig.SIDE_PANEL_WIDTH - GameConfig.GAME_AREA_PADDING * 2;
        game.fill(0, 0, 0, 215); // Semi-transparent black background
        game.rect(0, 0, gameWidth, game.height, 16); // Draw a rectangle to cover the background
        game.textFont(Assets.titleFont);
        game.fill(ColorPalette.TEXT_QUATERNARY);
        game.textSize(gameWidth/9f);
        game.text("Snake Game", gameWidth / 2f, game.height / 4f);
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
        // This state does not handle key presses, so this method can be empty.
    }

    @Override
    public void mousePressed(Game game) {
        if(this.layout != null) {
            this.layout.handleMousePress(game);
        }
    }
}
