package com.github.sebasman.core.interfaces.ui;

import com.github.sebasman.core.Game;

import processing.core.PApplet;

/**
 * Defines the contract for any classes that can render
 * the main game layout (frames, scores, etc.).
 */
public interface IStaticFrameRenderer {
    /**
     * Initializes the UI render API with the game instance.
     * @param context The PApplet context of the game, used for rendering.
     */
    void initialize(PApplet context);

    /**
     * Renders the UI elements of the game.
     * @param game The main instance of the game (the context).
     */
    void render(Game game);
}
