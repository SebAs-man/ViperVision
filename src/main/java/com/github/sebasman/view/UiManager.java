package com.github.sebasman.view;

import com.github.sebasman.contracts.view.IUiComponent;
import com.github.sebasman.contracts.view.ILayout;
import processing.core.PApplet;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Manages a set of UI layouts, centralizing the drawing,
 * event management, and cursor update.
 */
public class UiManager {
    // List of layouts to be managed by the UiManager.
    private final List<ILayout> layouts;

    /**
     * Constructor for UiManager.
     */
    public  UiManager() {
        this.layouts = new LinkedList<>();
    }

    /**
     * Adds a layout to the UiManager.
     * @param layout the layout to be added
     */
    public void addLayout(ILayout layout) {
        this.layouts.add(Objects.requireNonNull(layout, "Layout cannot be null"));
    }

    /**
     * Updates all components in the UiManager based on mouse position and state.
     * @param context the PApplet context to handle mouse events
     */
    public void update(PApplet context){
        boolean isHoveringAny = false;
        for(ILayout layout : this.layouts) {
            for(IUiComponent component : layout.getComponents()) {
                component.update(context.mouseX, context.mouseY, context.mousePressed);
                if(component.isHovered()){
                    isHoveringAny = true;
                }
            }
        }
        context.cursor(isHoveringAny ? PApplet.HAND : PApplet.ARROW);
    }

    /**
     * Draws all layouts in the UiManager.
     * @param context the PApplet context to handle the drawing
     */
    public void draw(PApplet context){
        for(ILayout layout : this.layouts) {
            layout.draw(context);
        }
    }

    /**
     * Handles mouse press events for all layouts in the UiManager.
     * @param mouseX the x-coordinate of the mouse press event
     * @param mouseY the y-coordinate of the mouse press event
     */
    public void handleMousePress(int mouseX, int mouseY) {
        for(ILayout layout : this.layouts) {
            layout.handleMousePress(mouseX, mouseY);
        }
    }
}
