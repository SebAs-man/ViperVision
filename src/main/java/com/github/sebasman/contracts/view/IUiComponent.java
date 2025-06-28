package com.github.sebasman.contracts.view;

import processing.core.PApplet;

/**
 * Represents any interactive graphic component in the UI.
 */
public interface IUiComponent {
    /**
     * Draws the component at the given position.
     * @param context The PApplet context to draw.
     * @param x The X coordinate where the panel starts.
     * @param y The Y coordinate where this component should be drawn.
     * @return the space that occupied this component.
     */
    float draw (PApplet context, float x, float y);

    /**
     * Handles mouse press events for the component.
     * @param mouseX Coordinate of the mouse on the X axis.
     * @param mouseY Coordinate of the mouse on the Y axis.
     */
    void handleMousePress(int mouseX, int mouseY);


    void update(int mouseX, int mouseY, boolean isMousePressed);

    /**
     * Returns if the component is currently in a hovered state.
     * @return true if the component is hovered, false otherwise.
     */
    boolean isHovered();
}
