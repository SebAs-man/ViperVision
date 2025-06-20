package com.github.sebasman.presenter.states;

import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.config.ColorPalette;
import processing.core.PApplet;

/**
 * The starting state of the game, where the player is prompted to start the game.
 */
public final class StartingState implements IState {
    // This is a singleton class for the starting state of the game.
    private static final IState INSTANCE = new StartingState();

    /**
     * Private constructor to prevent instantiation.
     */
    private StartingState() {}

    /**
     * Returns the singleton instance of the StartingState.
     * @return the instance of StartingState
     */
    public static IState getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnter(IGameContext game) {
        System.out.println("Press any key to start the game!");
    }

    @Override
    public void onExit(IGameContext game) {
        // This state does not requite any action on exit.
    }

    @Override
    public void update(IGameContext game) {
        // This state does not require any updates, so this method can be empty.
    }

    @Override
    public void gameTickUpdate(IGameContext game) {
        // This state does not require any game tick updates, so this method can be empty.
    }

    @Override
    public void draw(IGameContext game, Float interpolation) {
        PApplet renderer = game.getRenderer();
        // Draw the background
        renderer.fill(0, 0, 0, 125); // Semi-transparent black background
        renderer.image(Assets.backgroundPortalImage, 0, 0, renderer.width, renderer.height);
        renderer.rect(0, 0, renderer.width, renderer.height); // Draw a rectangle to cover the background
        // Draw the title and instructions
        renderer.textFont(Assets.titleFont);
        renderer.fill(ColorPalette.TEXT_PRIMARY);
        renderer.textSize(renderer.width/9f);
        renderer.text("VIPER VISION", renderer.width / 2f, renderer.height / 2f);
        renderer.textFont(Assets.textFont);
        renderer.textSize(renderer.width/21f);
        renderer.text("Press any key for start", renderer.width / 2f, renderer.height / 1.15f);
    }

    @Override
    public void keyPressed(IGameContext game, int keyCode) {
        game.changeState(MenuState.getInstance());
    }

    @Override
    public void mousePressed(int mouseX, int mouseY) {
        // This state does not handle mouse presses, so this method can be empty.
    }
}
