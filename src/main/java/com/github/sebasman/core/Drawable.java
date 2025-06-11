package com.github.sebasman.core;

import processing.core.PApplet;

/**
 * Drawable is an interface that defines a contract for objects that can be drawn on a PApplet context.
 */
public interface Drawable {
    /**
     * The draw method is called to render the object on the provided PApplet context.
     * Implementing classes should provide their own drawing logic.
     * @param context The PApplet context used for drawing.
     */
    void draw(PApplet context);
}
