package com.github.sebasman.states;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.engine.State;
import com.github.sebasman.ui.GameUiDynamic;
import com.github.sebasman.ui.UiManager;
import com.github.sebasman.core.interfaces.ui.Layout;
import com.github.sebasman.ui.layouts.VerticalLayout;
import com.github.sebasman.utils.GameConfig;
import com.github.sebasman.strategies.FollowFoodStrategy;
import com.github.sebasman.strategies.HumanControlStrategy;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.ui.components.Button;
import com.github.sebasman.utils.ColorPalette;

/**
 * The menu state of the game, where the player can see the main menu options.
 */
public final class MenuState implements State {
    // This is a singleton class for the menu state of the game.
    private static final State INSTANCE = new MenuState();
    // List of UI components to be displayed in the menu state
    private final UiManager uiManager;

    /**
     * Private constructor to prevent instantiation.
     */
    private MenuState() {
        this.uiManager = new UiManager();
    }

    /**
     * Returns the singleton instance of the MenuState.
     * @return the instance of MenuState
     */
    public static State getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(Game game) {
        // Clear any existing UI components
        this.uiManager.clear();
        // Initialize the layout for the menu
        Layout menuLayout = new VerticalLayout(GameConfig.CENTER_GAME_X - (GameConfig.COMPONENT_WIDTH/2),
                game.height / 2);
        menuLayout.add(new Button("Play", Assets.playImage,
                () -> game.changeState(new PreparingState(HumanControlStrategy.getInstance()))));
        menuLayout.add(new Button("Watch AI Play", Assets.watchAIImage,
                () -> game.changeState(new PreparingState(FollowFoodStrategy.getInstance()))));
        this.uiManager.addLayout(menuLayout);
        // Set the dynamic components for the game UI
        game.resetScore();
        game.setFood(null);
        game.setSnake(null);
    }

    @Override
    public void update(Game game) {
        this.uiManager.update(game);
    }

    @Override
    public void gameTickUpdate(Game game) {
        // This state does not require game tick updates, so this method can be empty.
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
        this.uiManager.draw(game);
    }

    @Override
    public void keyPressed(Game game, int keyCode) {
        // This state does not handle key presses, so this method can be empty.
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        this.uiManager.handleMousePress(mouseX, mouseY);
    }
}
