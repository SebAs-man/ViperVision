package com.github.sebasman.contracts.model;

import processing.core.PApplet;

/**
 * Drawable is an interface that defines a contract for objects that can be drawn on a PApplet context.
 */
public interface IDrawable {
    /**
     * Draws the object on the provided PApplet context with a specified interpolation value.
     * @param context The PApplet context where the object will be drawn.
     * @param interpolation A float value representing the interpolation factor for drawing.
     */
    void draw(PApplet context, Float interpolation);
}
