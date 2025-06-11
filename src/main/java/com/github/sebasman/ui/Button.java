package com.github.sebasman.ui;

import com.github.sebasman.core.GameConfig;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * Represents a button in the game's menu. It can display a label and an icon,
 * and it handles mouse hover effects.
 */
public class Button {
    // Fields for the button's label, position, size, and icon
    private final String label;
    private final int x, y;
    private final PImage icon;

    /**
     * Constructor for the MenuButton class.
     * @param label the text label of the button
     * @param icon the icon image of the button can be null
     * @param x the X position of the button on the screen
     * @param y the Y position of the button on the screen
     */
    public Button(String label, PImage icon, int x, int y) {
        this.label = label;
        this.icon = icon;
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the button on the game screen.
     * @param p the PApplet instance used for drawing
     */
    public void draw(PApplet p){
        boolean isHover = isMouseOver(p.mouseX, p.mouseY);

        p.pushStyle(); // Save the current style
        p.rectMode(PConstants.CENTER);
        p.imageMode(PConstants.CENTER);
        // Set the background color based on hover state
        if(isHover){
            p.fill(ColorPalette.BUTTON_HOVER_FILL);
        } else {
            p.fill(ColorPalette.BUTTON_FILL);
        }
        p.stroke(ColorPalette.BUTTON_STROKE);
        p.strokeWeight(2);
        p.rect(x, y, GameConfig.BUTTON_WIDTH, GameConfig.BUTTON_HEIGHT, 24);
        // Draw the icon if it is not null
        if(icon != null){
            p.image(icon, x - (GameConfig.BUTTON_WIDTH/3f), y, 32, 32);
        }
        // Draw the label text
        p.textFont(Assets.textFont);
        p.fill(ColorPalette.TEXT_TERTIARY);
        p.textSize(24);
        p.text(label, x + 32, y);

        p.popStyle();
    }

    /**
     * Checks if the mouse is over the button.
     * @param MouseX Position of the mouse on the X axis
     * @param MouseY Position of the mouse on the Y axis
     * @return true if the mouse is over the button, false otherwise
     */
    public boolean isMouseOver(int MouseX, int MouseY) {
        return MouseX >= (x - GameConfig.BUTTON_WIDTH/2) && MouseX <= (x + GameConfig.BUTTON_WIDTH/2) &&
                MouseY >= (y - GameConfig.BUTTON_HEIGHT/2) && MouseY <= (y + GameConfig.BUTTON_HEIGHT/2);
    }
}
