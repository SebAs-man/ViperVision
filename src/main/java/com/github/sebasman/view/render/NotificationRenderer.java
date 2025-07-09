package com.github.sebasman.view.render;

import com.github.sebasman.presenter.listeners.NotificationManager;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.config.ColorPalette;
import com.github.sebasman.view.config.ViewConfig;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.geom.Rectangle2D;

/**
 * Singleton renderer that draws active notifications on the screen.
 */
public final class NotificationRenderer {
    private static final NotificationRenderer INSTANCE = new NotificationRenderer();

    /**
     * Private constructor to prevent instantiation.
     */
    private NotificationRenderer() {}

    /**
     * Returns the single instance of the Notification Renderer
     * @return Singleton instance.
     */
    public static NotificationRenderer getInstance() {
        return INSTANCE;
    }

    /**
     * Draw game notifications
     * @param context The PApplet context for drawing operations.
     */
    public void draw(PApplet context){
        NotificationManager.Notification notification = NotificationManager.getInstance().getActiveNotification();
        if(notification == null) return;
        int currentAlpha = notification.alpha();
        if(currentAlpha <= 0) return;

        context.pushStyle();
        context.textSize(ViewConfig.GAME_AREA_PADDING*1.5f);

        float modalWidth = context.textWidth(notification.getMessage()) + ViewConfig.GAME_AREA_PADDING*2;
        float startY = context.height - (ViewConfig.NOTIFICATION_HEIGHT*2.25f);
        float startX = (context.width/2f) - (modalWidth/2f);

        // Draw Modal Background
        context.rectMode(PApplet.CORNER);
        context.noStroke();
        switch (notification.getType()){
            case INFO -> context.fill(30, 224, 255, currentAlpha * 0.6f);
            case WARNING -> context.fill(255, 202, 70, currentAlpha * 0.6f);
            case ACHIEVEMENT -> context.fill(101, 255, 70,  currentAlpha * 0.6f);
            case ERROR -> context.fill(255, 70, 70,  currentAlpha * 0.6f);
        }
        context.rect(startX, startY, modalWidth, ViewConfig.NOTIFICATION_HEIGHT, ViewConfig.RADIUS);
        // Draw title
        context.textFont(Assets.titleFont);
        context.textSize(ViewConfig.GAME_AREA_PADDING*1.15f);
        context.textAlign(PApplet.CENTER, PApplet.TOP);
        context.fill(0, currentAlpha);
        context.text(notification.getType().getTitle(), startX + (modalWidth/2f), startY + 10);
        // Draw icon
        PImage icon = notification.getType().getIcon();
        if(icon != null) {
            context.imageMode(PApplet.CENTER);
            context.image(icon, startX + ViewConfig.GAME_AREA_PADDING + 16, startY + (ViewConfig.NOTIFICATION_HEIGHT/2f), 32, 32);
        }
        // Draw a message
        context.textFont(Assets.textFont);
        context.textSize(ViewConfig.GAME_AREA_PADDING*1.5f);
        context.textAlign(PApplet.CENTER, PApplet.CENTER);
        context.text(notification.getMessage(), startX + (modalWidth/2f), startY + (ViewConfig.NOTIFICATION_HEIGHT/2f) + 10);
        // Drawing and Updating the ‘X’ Close Button
        float closeButtonSize = 14;
        float closeButtonX = startX + modalWidth - closeButtonSize - 10;
        float closeButtonY = startY + 10;
        notification.closeButtonPounds = new Rectangle2D.Float(closeButtonX, closeButtonY, closeButtonSize, closeButtonSize);
        context.strokeWeight(2.5f);
        if(notification.isCloseButtonHovered(context.mouseX, context.mouseY)) {
            context.stroke(255, 50, 50, currentAlpha);
        } else{
            context.stroke(ColorPalette.TEXT_TERTIARY, currentAlpha);
        }
        context.line(closeButtonX, closeButtonY, closeButtonX + closeButtonSize, closeButtonY + closeButtonSize);
        context.line(closeButtonX, closeButtonY + closeButtonSize, closeButtonX + closeButtonSize, closeButtonY);

        context.popStyle();
    }

    /**
     * Propagates the mouse click event to the NotificationManager.
     * @param mouseX The X coordinate of the click.
     * @param mouseY The Y coordinate of the click.
     */
    public void handleMousePress(int mouseX, int mouseY) {
        NotificationManager.getInstance().handleMousePress(mouseX, mouseY);
    }
}
