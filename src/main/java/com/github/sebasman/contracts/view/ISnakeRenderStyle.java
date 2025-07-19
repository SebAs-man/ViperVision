package com.github.sebasman.contracts.view;

import processing.core.PApplet;

/**
 * Defines a contract for a snake's rendering style.
 * An implementation of this can provide dynamic colors for effects like invulnerability.
 */
public interface ISnakeRenderStyle {
    /**
     * Gets the current fill color for the snake's body.
     * @param context The PApplet context.
     * @return The integer value of the color to use.
     */
    int getFillColor(PApplet context);
}
