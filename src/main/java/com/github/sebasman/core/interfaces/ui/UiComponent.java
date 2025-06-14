package com.github.sebasman.core.interfaces.ui;

import processing.core.PApplet;

/**
 * Represents any interactive graphic component in the UI.
 */
public interface UiComponent{
    /**
     * Draws the component at the given position.
     * @param context The PApplet context to draw.
     * @param x The X coordinate where the panel starts.
     * @param y The Y coordinate where this component should be drawn.
     */
    void draw (PApplet context, float x, float y);

    /**
     * Handles the mouse press event for the component.
     * @param context The PApplet context in which the component is drawn.
     */
    void handleMousePress(PApplet context);

    /**
     * Handles the mouse release event for the component.
     */
    void update();

    /**
     * Checks if the mouse is over the component.
     * @param mouseX Position of the mouse on the X axis.
     * @param mouseY Position of the mouse on the Y axis.
     * @return true if the mouse is over the component, false otherwise.
     */
    boolean isMouseOver(int mouseX, int mouseY);
}
