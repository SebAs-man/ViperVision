package com.github.sebasman.presenter.states;

import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.presenter.engine.BoardInteractionController;
import com.github.sebasman.view.config.ViewConfig;
import com.github.sebasman.view.UiManager;
import com.github.sebasman.contracts.view.ILayout;
import com.github.sebasman.view.layout.VerticalLayout;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.components.Button;
import com.github.sebasman.view.config.ColorPalette;
import com.github.sebasman.view.render.GameUiStatic;
import com.github.sebasman.view.render.GameWorldRenderer;
import com.github.sebasman.view.render.HUDRenderer;
import com.github.sebasman.view.render.ObstacleRenderer;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * The paused state of the game, where the player can see a pause message
 * and can resume the game by pressing 'Space' or 'p'.
 */
public final class PausedState implements IState {
    // This is a singleton class for the paused state of the game.
    private static final IState INSTANCE = new PausedState();
    // List of UI components to be displayed in the paused state
    private UiManager uiManager;

    /**
     * Private constructor to prevent instantiation.
     */
    private PausedState() {}

    /**
     * Returns the singleton instance of the PausedGame state.
     * @return the instance of PausedGame
     */
    public static IState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(IGameContext game) {
        // Lazy Initialization: The UI is built only the first time you enter pause.
        if(this.uiManager == null) {
            this.uiManager = this.buildUi(game);
        }
    }

    @Override
    public void onExit(IGameContext game) {
        // No cleaning is required when coming out of the break.
    }

    @Override
    public void update(IGameContext game) {
        // Delegates the update of the UI (cursor, hover) to the manager.
        if(this.uiManager != null) {
            this.uiManager.update(game.getRenderer());
        }
    }

    @Override
    public void draw(IGameContext game) {
        PApplet renderer  = game.getRenderer();
        GameUiStatic.getInstance().render(renderer);
        GameWorldRenderer.getInstance().render(game, null);
        HUDRenderer.getInstance().render(renderer);

        if(game.getSession() != null) {
            ObstacleRenderer.getInstance().draw(renderer, game.getSession().getBoard());
        }
        // Draw the pause overlay (the color filter and the text).
        float x_ = renderer.width - (ViewConfig.SIDE_PANEL_WIDTH/2f) - ViewConfig.GAME_AREA_PADDING;
        renderer.pushStyle();
        renderer.noStroke();
        renderer.fill(255, 255, 255);
        renderer.rect(renderer.width - ViewConfig.GAME_AREA_PADDING - ViewConfig.SIDE_PANEL_WIDTH, ViewConfig.GAME_AREA_PADDING,
                ViewConfig.SIDE_PANEL_WIDTH,renderer.height - (ViewConfig.GAME_AREA_PADDING*2), ViewConfig.RADIUS);
        renderer.textFont(Assets.titleFont);
        renderer.fill(ColorPalette.TEXT_SECONDARY);
        renderer.textSize(ViewConfig.SLIDER_WIDTH/3f);
        renderer.text("PAUSE", x_, renderer.height/2f);
        renderer.textFont(Assets.textFont);
        renderer.textSize(ViewConfig.SLIDER_WIDTH/11f);
        renderer.text("Press 'p' or SPACE to continue", x_, renderer.height/7.5f);
        renderer.popStyle();
        // Draw UI components (buttons)
        if(this.uiManager != null) {
            this.uiManager.draw(renderer);
        }
    }

    @Override
    public void keyPressed(IGameContext game, int keyCode) {
        PApplet renderer = game.getRenderer();
        if(Character.toLowerCase(renderer.key) == 'p' || renderer.key == ' ') {
            game.popState(); // Pops the PausedState off the stack and returns to PlayingState.
        }
    }

    @Override
    public void mousePressed(IGameContext game, int mouseX, int mouseY) {
        PApplet render = game.getRenderer();
        // Delegates clicks to the UI manager.
        if(render.mouseButton == PConstants.LEFT && this.uiManager != null) {
            this.uiManager.handleMousePress(mouseX, mouseY);
            return;
        }
        // Right-click operation (to add/remove obstacles)
        if(render.mouseButton == PConstants.RIGHT){
            BoardInteractionController.getInstance().handleRightClick(game, mouseX, mouseY);
        }
    }

    /**
     * Builds the UI for the paused state of the game.
     * @param game the game instance to build the UI for
     * @return the UiManager containing the UI components for the paused state
     */
    private UiManager buildUi(IGameContext game) {
        UiManager uiManager = new UiManager();
        PApplet  renderer  = game.getRenderer();

        ILayout menuLayout = new VerticalLayout(renderer.width - ViewConfig.SIDE_PANEL_WIDTH,
                (int) (renderer.height/1.25));

        // The "Main Menu" button has a two-step logic:
        // 1. popState(): Pops out the PausedState.
        // 2. changeState(): Replaces the PlayingState with the MenuState.
        menuLayout.add(new Button("Main Menu", Assets.homeImage,
                () -> {
                    game.popState();
                    game.changeState(MenuState.getInstance());
                }));

        uiManager.addLayout(menuLayout);
        return uiManager;
    }
}
