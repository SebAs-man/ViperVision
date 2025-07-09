package com.github.sebasman.view.render;

import com.github.sebasman.presenter.listeners.NotificationManager;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.config.ViewConfig;
import processing.core.PApplet;

import java.util.Queue;

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
        Queue<NotificationManager.Notification> notifications = NotificationManager.getInstance().getActiveNotification();
        if(notifications.isEmpty()) return;

        int cont = 0;
        float startY = context.height - ViewConfig.NOTIFICATION_HEIGHT;
        float startX = context.width/2f;

        context.pushStyle();
        for(NotificationManager.Notification notification : notifications){
            int currentAlpha = notification.alpha();
            if(currentAlpha <= 0) return;
            // Draw Modal Background
            context.rectMode(PApplet.CENTER);
            context.noStroke();
            switch (notification.getType()){
                case INFO -> context.fill(30, 224, 255, currentAlpha * .9f);
                case WARNING -> context.fill(255, 202, 70, currentAlpha * .9f);
                case ACHIEVEMENT -> context.fill(101, 255, 70,  currentAlpha * .9f);
                case ERROR -> context.fill(255, 70, 70,  currentAlpha * .9f);
            }
            context.rect(startX, startY - (cont*ViewConfig.NOTIFICATION_HEIGHT*1.45f),
                    ViewConfig.NOTIFICATION_WIDTH, ViewConfig.NOTIFICATION_HEIGHT, ViewConfig.RADIUS);
            context.rect(startX, startY - ViewConfig.NOTIFICATION_HEIGHT*(cont*1.45f + 0.5f),
                    ViewConfig.NOTIFICATION_WIDTH/4f, ViewConfig.NOTIFICATION_HEIGHT/2f, ViewConfig.RADIUS);
            // Draw title
            context.textFont(Assets.titleFont);
            context.textSize(15);
            context.textAlign(PApplet.CENTER, PApplet.CENTER);
            context.fill(0, currentAlpha * 0.9f);
            context.text(notification.getType().getTitle(), startX,
                    startY - ViewConfig.NOTIFICATION_HEIGHT*(cont*1.45f + 0.5f));
            // Draw a message
            context.textFont(Assets.textFont);
            context.textSize(24);
            context.textAlign(PApplet.CENTER, PApplet.CENTER);
            context.text(notification.getMessage(), startX, startY - ViewConfig.NOTIFICATION_HEIGHT*cont*1.45f);
            cont++;
        }
        context.popStyle();
    }
}
