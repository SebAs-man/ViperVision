package com.github.sebasman.ui.layout;

import com.github.sebasman.core.interfaces.ui.UiComponent;
import com.github.sebasman.utils.GameConfig;
import processing.core.PApplet;

import java.util.LinkedList;
import java.util.List;

/**
 * Manages a list of components and draws them in a vertical layout.
 */
public class VerticalLayout {
    // The list of components to be drawn in the layout
    private final List<UiComponent> components;
    // The x and y coordinate where the layout starts
    private final int x;
    private final int y;

    /**
     * Creates a new VerticalLayout with the specified starting coordinates.
     * @param x coordinate, where the layout starts on the x-axis.
     * @param y coordinate, where the layout starts on the y-axis.
     */
    public VerticalLayout(int x, int y){
        this.x = Math.max(x + (GameConfig.COMPONENT_WIDTH/2), 0);
        this.y = Math.max(y + (GameConfig.COMPONENT_HEIGHT/2), 0);
        this.components = new LinkedList<>();
    }

    /**
     * Adds a component to the vertical layout.
     * @param component the UiComponent to be added to the layout.
     */
    public void add(UiComponent component){
        this.components.add(component);
    }

    /**
     * Draws all components in the vertical layout starting from the specified x and y coordinates.
     * @param context the PApplet context where the components will be drawn.
     */
    public void draw(PApplet context){
        float currentY = this.y;
        for (UiComponent component : components) {
            component.draw(context, this.x, currentY);
            currentY += (GameConfig.COMPONENT_HEIGHT*1.5f);
        }
    }

    public void update() {
        components.forEach(UiComponent::update);
    }

    /**
     * Handles mouse press events for all components in the vertical layout.
     * @param context the PApplet context in which the components are drawn.
     */
    public void handleMousePress(PApplet context) {
        components.forEach(component -> component.handleMousePress(context));
    }

    // --- Getters ---

    /**
     * Returns the list of components in the vertical layout.
     * @return a list of UiComponent objects that are part of the layout.
     */
    public List<UiComponent> getComponents() {
        return components;
    }
}
