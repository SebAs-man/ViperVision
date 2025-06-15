package com.github.sebasman.ui.layouts;

import com.github.sebasman.core.interfaces.ui.Layout;
import com.github.sebasman.core.interfaces.ui.UiComponent;
import com.github.sebasman.utils.GameConfig;
import processing.core.PApplet;

import java.util.LinkedList;
import java.util.List;

/**
 * Manages a list of components and draws them in a vertical layout.
 */
public class VerticalLayout implements Layout {
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

    @Override
    public void add(UiComponent component){
        this.components.add(component);
    }

    @Override
    public void draw(PApplet context){
        float currentY = this.y;
        for (UiComponent component : components) {
            component.draw(context, this.x, currentY);
            currentY += (GameConfig.COMPONENT_HEIGHT*1.5f);
        }
    }

    @Override
    public void handleMousePress(int mouseX, int mouseY) {
        components.forEach(component ->  component.handleMousePress(mouseX, mouseY));
    }

    @Override
    public List<UiComponent> getComponents() {
        return components;
    }
}
