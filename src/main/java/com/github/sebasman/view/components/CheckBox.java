package com.github.sebasman.view.components;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.ConfigurationChangedEvent;
import com.github.sebasman.contracts.view.IUiComponent;
import com.github.sebasman.contracts.vo.ComponentState;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.config.ColorPalette;
import com.github.sebasman.view.config.ViewConfig;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.Objects;

/**
 * A UI component representing a checkbox that can be toggled on or off.
 * It publishes an event when its state is changed by the user.
 */
public final class CheckBox implements IUiComponent {
    private final String label;
    private final String configKey;
    private boolean isChecked;
    // Position of the component on the screen
    private float x, y;
    // State of this component
    private ComponentState state;

    /**
     * Constructs a new CheckboxComponent.
     * @param label The text to display next to the checkbox.
     * @param configKey A unique key for this configuration option (e.g., "AI_SHOW_PATH").
     * @param initialState The initially checked state (true for checked, false for unchecked).
     */
    public CheckBox(String label, String configKey, boolean initialState) {
        this.label = label;
        this.configKey = Objects.requireNonNull(configKey, "configKey cannot be null");
        this.isChecked = initialState;
        this.state = ComponentState.IDLE;
    }

    @Override
    public float draw(PApplet context, float x, float y) {
        this.x = x;
        this.y = y;

        context.pushStyle();
        context.rectMode(PConstants.CORNER);
        context.imageMode(PConstants.CENTER);
        switch (this.state){
            case HOVER -> context.fill(ColorPalette.COMPONENT_HOVER_FILL);
            case ACTIVE -> context.fill(ColorPalette.COMPONENT_ACTIVE_FILL);
            case IDLE -> context.fill(ColorPalette.COMPONENT_FILL);
        }
        // Draw the button rectangle
        context.strokeWeight(2);
        context.stroke(ColorPalette.COMPONENT_STROKE);
        context.rect(x, y, ViewConfig.CHECKBOX_SIZE, ViewConfig.CHECKBOX_SIZE, ViewConfig.RADIUS);
        // If checked, draw the check mark
        if (isChecked) {
            context.image(Assets.checkImage, x + ViewConfig.CHECKBOX_SIZE/2f, y + ViewConfig.CHECKBOX_SIZE/2f,
                    Math.min(32, ViewConfig.CHECKBOX_SIZE *0.95f), Math.min(32, ViewConfig.BUTTON_HEIGHT *0.95f));
        }
        // Draw the label
        context.textFont(Assets.textFont);
        context.fill(ColorPalette.TEXT_PRIMARY);
        context.textSize(ViewConfig.CHECKBOX_SIZE/1.5f);
        context.textAlign(PConstants.LEFT, PConstants.CENTER);
        context.text(label, x + (ViewConfig.CHECKBOX_SIZE*1.5f), y + ViewConfig.CHECKBOX_SIZE/2f);
        context.popStyle();

        return ViewConfig.CHECKBOX_SIZE;
    }

    @Override
    public void handleMousePress(int mouseX, int mouseY) {
        if (isMouseOver(mouseX, mouseY)) {
            // Invierte el estado
            this.isChecked = !this.isChecked;
            // Publica el evento con la clave de configuración y el nuevo valor booleano
            EventManager.getInstance().notify(new ConfigurationChangedEvent(this.configKey, this.isChecked));
        }
    }

    @Override
    public void update(int mouseX, int mouseY, boolean isMousePressed, int mouseButton) {
        if (isMouseOver(mouseX, mouseY)) {
            this.state = (isMousePressed && mouseButton == PConstants.LEFT) ? ComponentState.ACTIVE : ComponentState.HOVER;
        } else {
            this.state = ComponentState.IDLE;
        }
    }

    @Override
    public boolean isHovered() {
        return this.state == ComponentState.HOVER || this.state == ComponentState.ACTIVE;
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + ViewConfig.CHECKBOX_SIZE &&
                mouseY >= this.y && mouseY <= this.y + ViewConfig.CHECKBOX_SIZE;
    }
}
