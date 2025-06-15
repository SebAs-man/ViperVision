package com.github.sebasman.ui.components;

import com.github.sebasman.core.interfaces.ui.UiComponent;
import com.github.sebasman.core.vo.ComponentState;
import com.github.sebasman.core.interfaces.ui.Command;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.utils.ColorPalette;
import com.github.sebasman.utils.GameConfig;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * Represents a button in the game's menu. It can display a label and an icon,
 * and it handles mouse hover effects.
 */
public class Button implements UiComponent {
    // Fields for the button's label, position, size, and icon
    private final String label;
    private final PImage icon;
    private final Command action;
    // Position of the button on the screen
    private float x, y;
    // State of the button, used for hover effects
    private ComponentState state;

    /**
     * Constructor for the MenuButton class.
     * @param label the text label of the button
     * @param icon the icon image of the button can be null
     * @param command the command to be executed when the button is clicked
     */
    public Button(String label, PImage icon, Command command) {
        this.label = label;
        this.icon = icon;
        this.action = command;
        this.state = ComponentState.IDLE;
    }

    @Override
    public void draw(PApplet p, float x, float y){
        this.x = x;
        this.y = y;

        p.pushStyle();
        p.rectMode(PConstants.CENTER);
        p.imageMode(PConstants.CENTER);
        switch (this.state){
            case HOVER -> p.fill(ColorPalette.BUTTON_HOVER_FILL);
            case ACTIVE -> p.fill(ColorPalette.BUTTON_ACTIVE_FILL);
            case IDLE -> p.fill(ColorPalette.BUTTON_FILL);
        }
        // Draw the button rectangle
        p.stroke(ColorPalette.BUTTON_STROKE);
        p.strokeWeight(2);
        p.rect(x, y, GameConfig.COMPONENT_WIDTH, GameConfig.COMPONENT_HEIGHT, GameConfig.RADIUS);
        // Draw the icon if it is not null
        float centerX = this.x;
        if(icon != null){
            p.image(icon, this.x - (GameConfig.COMPONENT_WIDTH/3f), this.y, Math.min(32, GameConfig.COMPONENT_WIDTH*0.15f), Math.min(32, GameConfig.COMPONENT_HEIGHT*0.65f));
        } else {
            centerX -= Math.min(32, GameConfig.COMPONENT_WIDTH*0.15f);
        }
        // Draw the label text
        p.textFont(Assets.textFont);
        p.fill(ColorPalette.TEXT_TERTIARY);
        p.textSize(24);
        p.text(label, centerX + 32, this.y);

        p.popStyle();
    }

    @Override
    public void handleMousePress(int mouseX, int mouseY) {
        if(this.isMouseOver(mouseX, mouseY)) {
            this.action.execute();
        }
    }

    @Override
    public void update(int mouseX, int mouseY, boolean isMousePressed) {
        if(this.isMouseOver(mouseX, mouseY)){
            this.state = isMousePressed ? ComponentState.ACTIVE : ComponentState.HOVER;
        } else{
            this.state = ComponentState.IDLE;
        }
    }

    @Override
    public boolean isHovered() {
        return this.state == ComponentState.HOVER || this.state == ComponentState.ACTIVE;
    }

    /**
     * Checks if the mouse is over the button.
     * @param mouseX Coordinate of the mouse on the X axis
     * @param mouseY Coordinate of the mouse on the Y axis
     * @return true if the mouse is over the button, false otherwise
     */
    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= (this.x - (GameConfig.COMPONENT_WIDTH / 2f)) && mouseX <= (this.x + (GameConfig.COMPONENT_WIDTH / 2f)) &&
                mouseY >= (this.y - (GameConfig.COMPONENT_HEIGHT / 2f)) && mouseY <= (this.y + (GameConfig.COMPONENT_HEIGHT / 2f));
    }
}
