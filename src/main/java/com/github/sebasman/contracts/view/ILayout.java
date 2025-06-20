package com.github.sebasman.contracts.view;

import processing.core.PApplet;

import java.util.List;

/**
 * Represents a layout that can contain multiple UI components.
 * Provides methods to add components, draw them, update their state,
 * and handle mouse press events.
 */
public interface ILayout {
    /**
     * Adds a component to the layout.
     * @param component the UiComponent to be added to the layout.
     */
    void add(IUiComponent component);

    /**
     * Draws all components in the layout starting from the specified x and y coordinates.
     * @param context the PApplet context where the components will be drawn.
     */
    void draw(PApplet context);

    /**
     * Handles mouse press events for all components in the layout.
     * This method should be called when a mouse press event occurs.
     * @param mouseX the x-coordinate of the mouse press event.
     * @param mouseY the y-coordinate of the mouse press event.
     */
    void handleMousePress(int mouseX, int mouseY);

    /**
     * Returns the list of components in the layout.
     * @return a list of UiComponent objects in the layout.
     */
    List<IUiComponent> getComponents();
}
