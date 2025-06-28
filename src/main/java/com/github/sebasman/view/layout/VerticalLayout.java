package com.github.sebasman.view.layout;

import com.github.sebasman.contracts.view.ILayout;
import com.github.sebasman.contracts.view.IUiComponent;
import com.github.sebasman.view.config.ViewConfig;
import processing.core.PApplet;

import java.util.LinkedList;
import java.util.List;

/**
 * Manages a list of components and draws them in a vertical layout.
 */
public class VerticalLayout implements ILayout {
    // The list of components to be drawn in the layout
    private final List<IUiComponent> components;
    // The x and y coordinate where the layout starts
    private final int x;
    private final int y;

    /**
     * Creates a new VerticalLayout with the specified starting coordinates.
     * @param x coordinate, where the layout starts on the x-axis.
     * @param y coordinate, where the layout starts on the y-axis.
     */
    public VerticalLayout(int x, int y){
        this.x = Math.max(x, 0);
        this.y = Math.max(y, 0);
        this.components = new LinkedList<>();
    }

    @Override
    public void add(IUiComponent component){
        this.components.add(component);
    }

    @Override
    public void draw(PApplet context){
        float currentY = this.y;
        for (IUiComponent component : components) {
            float temp = component.draw(context, this.x, currentY);
            currentY += (temp*1.5f);
        }
    }

    @Override
    public void handleMousePress(int mouseX, int mouseY) {
        components.forEach(component ->  component.handleMousePress(mouseX, mouseY));
    }

    @Override
    public List<IUiComponent> getComponents() {
        return components;
    }
}
