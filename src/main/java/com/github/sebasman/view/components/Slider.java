package com.github.sebasman.view.components;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.ConfigurationChangedEvent;
import com.github.sebasman.contracts.view.IUiComponent;
import com.github.sebasman.contracts.vo.ComponentState;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.config.ColorPalette;
import com.github.sebasman.view.config.ViewConfig;
import processing.core.PApplet;

import java.util.Objects;

/**
 * A UI component that allows the user to select a value from a given range
 * by dragging a handle. It manages its own state (idle, hover, dragging)
 * and posts an event when its value is confirmed (on mouse release).
 */
public final class Slider implements IUiComponent {
    private final String label;
    private final String configKey;
    private final float min, max;
    private float currentValue;
    // Position of the component on the screen
    private float x, y;
    // State of this component
    private ComponentState state;
    private boolean isDragging;

    /**
     * Build a new SliderComponent.
     * @param label The text to display next to the slider.
     * @param configKey A unique key to identify the value of this slider in events (e.g. “AI_SPEED”).
     * @param min The minimum value of the slider.
     * @param max The maximum value of the slider.
     * @param initialValue The initial value of the slider.
     */
    public Slider(String label, String configKey, float min, float max, float initialValue) {
        this.label = label;
        this.configKey = Objects.requireNonNull(configKey, "configKey cannot be null");
        this.min = Math.max(min, 0);
        this.max = Math.max(min+0.1f, max);
        if(initialValue >= this.min && initialValue <= this.max) {
            this.currentValue = initialValue;
        }  else {
            this.currentValue = this.min;
        }
        this.state = ComponentState.IDLE;
        this.isDragging = false;
    }

    @Override
    public float draw(PApplet context, float x, float y) {
        this.x = x;
        this.y = y;

        context.pushStyle();

        context.textFont(Assets.textFont);
        context.textSize(ViewConfig.SLIDER_HEIGHT*2.25f);
        context.fill(ColorPalette.TEXT_PRIMARY);
        context.textAlign(PApplet.CENTER, PApplet.TOP);
        // Draws the label and the current formatted value
        String valueText = String.format("%.1f", currentValue);
        context.text(label + " [" + valueText + "]", x + ViewConfig.BUTTON_WIDTH/2f, y);
        // Draw the horizontal bar of the slider
        context.strokeWeight(ViewConfig.SLIDER_HEIGHT);
        context.stroke(ColorPalette.COMPONENT_STROKE);
        context.line(x, y + ViewConfig.SLIDER_HEIGHT*2.5f, x + ViewConfig.SLIDER_WIDTH, y + ViewConfig.SLIDER_HEIGHT*2.5f);
        // Calculates the X position of the handler based on the current value
        float handleX = PApplet.map(this.currentValue, this.min, this.max, x, x + ViewConfig.SLIDER_WIDTH);
        // Draw the handle (circle)
        context.strokeWeight(2);
        switch (this.state){
            case HOVER -> context.fill(ColorPalette.COMPONENT_HOVER_FILL);
            case ACTIVE -> context.fill(ColorPalette.COMPONENT_ACTIVE_FILL);
            case IDLE -> context.fill(ColorPalette.COMPONENT_FILL);
        }
        context.ellipse(handleX, y + ViewConfig.SLIDER_HEIGHT*2.5f, ViewConfig.SLIDER_SIZE_INDICATOR, ViewConfig.SLIDER_SIZE_INDICATOR);

        context.popStyle();

        return ViewConfig.SLIDER_HEIGHT*3;
    }

    @Override
    public void handleMousePress(int mouseX, int mouseY) {
        if(this.isMouseOverHandle(mouseX, mouseY)) {
            this.isDragging = true;
        }
    }

    @Override
    public void update(int mouseX, int mouseY, boolean isMousePressed) {
        // If the user is dragging...
        if(this.isDragging) {
            if(isMousePressed) {
                // ...we update the value based on the mouse position.
                this.currentValue = PApplet.map(mouseX, x, x + ViewConfig.SLIDER_WIDTH, this.min, this.max);
                this.currentValue = PApplet.constrain(this.currentValue, this.min, this.max);
            } else{
                this.isDragging = false;
                EventManager.getInstance().notify(new ConfigurationChangedEvent(this.configKey, this.currentValue));
            }
        }
        // Logic for visual state (hover/active)
        if (this.isMouseOverHandle(mouseX, mouseY) || this.isDragging) {
            this.state = isMousePressed ? ComponentState.ACTIVE : ComponentState.HOVER;
        } else {
            this.state = ComponentState.IDLE;
        }
    }

    @Override
    public boolean isHovered() {
        return this.state == ComponentState.HOVER || this.state == ComponentState.ACTIVE;
    }

    /**
     * Checks if the mouse cursor is over the slider handle.
     * @param mouseX Coordinate of the mouse on the X axis
     * @param mouseY Coordinate of the mouse on the Y axis
     * @return true if the mouse is over the button, false otherwise
     */
    private boolean isMouseOverHandle(int mouseX, int mouseY) {
        float handleX = PApplet.map(this.currentValue, this.min, this.max, x, x + ViewConfig.SLIDER_WIDTH);
        float barY = y + ViewConfig.SLIDER_HEIGHT*2.5f;
        // We use PApplet's dist() function to see if the mouse is inside the radius of the circle.
        return PApplet.dist(mouseX, mouseY, handleX, barY) < ViewConfig.SLIDER_SIZE_INDICATOR;
    }
}
