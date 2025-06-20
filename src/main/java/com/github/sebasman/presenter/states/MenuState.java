package com.github.sebasman.presenter.states;

import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.view.config.ViewConfig;
import com.github.sebasman.view.render.GameUiStatic;
import com.github.sebasman.view.render.GameWorldRenderer;
import com.github.sebasman.view.UiManager;
import com.github.sebasman.contracts.view.ILayout;
import com.github.sebasman.view.layout.VerticalLayout;
import com.github.sebasman.presenter.strategies.FollowFoodStrategy;
import com.github.sebasman.presenter.strategies.HumanControlStrategy;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.components.Button;
import com.github.sebasman.view.config.ColorPalette;
import processing.core.PApplet;

/**
 * The menu state of the game, where the player can see the main menu options.
 */
public final class MenuState implements IState {
    // This is a singleton class for the menu state of the game.
    private static final IState INSTANCE = new MenuState();
    // List of UI components to be displayed in the menu state
    private UiManager uiManager;

    /**
     * Private constructor to prevent instantiation.
     */
    private MenuState() {}

    /**
     * Returns the singleton instance of the MenuState.
     * @return the instance of MenuState
     */
    public static IState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(IGameContext game) {
        // Lazy initialization of the UI manager
        if(this.uiManager == null){
            this.uiManager = this.buildUi(game);
        }
        // Clears the previous game
        game.endCurrentSession();
    }

    @Override
    public void onExit(IGameContext game) {
        // This state does not require any action on exit.
    }

    @Override
    public void update(IGameContext game) {
        // The menu state does not logically update every frame,
        // but it does delegate the UI update.
        if(uiManager != null){
            this.uiManager.update(game.getRenderer());
        }
    }

    @Override
    public void gameTickUpdate(IGameContext game) {
        // This state does not require game tick updates, so this method can be empty.
    }

    @Override
    public void draw(IGameContext game, Float interpolation) {
        PApplet renderer = game.getRenderer();
        // Draw the static frame (background, side panel, top bar).
        GameUiStatic.getInstance().render(renderer);
        // Draw an empty “game world” as a background.
        GameWorldRenderer.getInstance().render(game, 0f);
        // Draws the menu overlay (title, etc.).
        int gameWidth = renderer.width - ViewConfig.SIDE_PANEL_WIDTH - ViewConfig.GAME_AREA_PADDING * 2;
        renderer.fill(0, 0, 0, 215);
        renderer.rect(0, 0, gameWidth, renderer.height, 16);
        renderer.textFont(Assets.titleFont);
        renderer.fill(ColorPalette.TEXT_QUATERNARY);
        renderer.textSize(gameWidth/9f);
        renderer.text("Snake Game", gameWidth / 2f, renderer.height / 4f);
        // Delegates the drawing of UI components (buttons) to the manager.
        if(this.uiManager != null) {
            this.uiManager.draw(renderer);
        }
    }

    @Override
    public void keyPressed(IGameContext game, int keyCode) {
        // This state does not handle key presses, so this method can be empty.
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        // Delegates the click event to the manager.
        if(this.uiManager != null){
            this.uiManager.handleMousePress(mouseX, mouseY);
        }
    }


    /**
     * Build the UI for the menu state using the Factory pattern.
     * Define and configure all layouts and components for this screen.
     * @param game the game instance to get dimensions and context.
     * @return the UiManager configured with the menu UI.
     */
    private UiManager buildUi(IGameContext game){
        PApplet renderer  = game.getRenderer();
        UiManager manager = new UiManager();

        ILayout menuLayout = new VerticalLayout(
                ViewConfig.CENTER_GAME_X - (ViewConfig.COMPONENT_WIDTH/2),
                renderer.height / 2);

        menuLayout.add(new Button("Play", Assets.playImage,
                () -> game.changeState(
                        new PreparingState(new HumanControlStrategy()))));
        menuLayout.add(new Button("Watch AI Play", Assets.watchAIImage,
                () -> game.changeState(
                        new PreparingState(new FollowFoodStrategy()))));

        manager.addLayout(menuLayout);
        return manager;
    }
}
