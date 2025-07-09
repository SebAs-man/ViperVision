package com.github.sebasman.presenter.listeners;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.FrameUpdatedEvent;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.vo.NotificationType;
import processing.core.PApplet;

import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * Manages the logic of modal notifications. Listens for events and maintains
 * a list of active messages with their remaining lifetime.
 */
public final class NotificationManager {
    private static final NotificationManager INSTANCE = new NotificationManager();
    private final Queue<Notification> notificationQueue;
    private Notification currentNotification;
    private long lastUpdateTime;

    /**
     * Private constructor to prevent instantiation.
     */
    private NotificationManager() {
        this.notificationQueue = new LinkedList<>();
        this.currentNotification = null;
        this.lastUpdateTime = System.currentTimeMillis();
        EventManager.getInstance().subscribe(NotificationRequestedEvent.class, this::addNotification);
        EventManager.getInstance().subscribe(FrameUpdatedEvent.class, _ -> this.update());
    }

    /**
     * Returns the single instance of the Notification Manager
     * @return A singleton instance.
     */
    public static NotificationManager getInstance() {
        return INSTANCE;
    }

    /**
     * Adds a new notification to the list of pending notifications
     * @param event The event that calls for action
     */
    private void addNotification(NotificationRequestedEvent event) {
        this.notificationQueue.offer(new Notification(event.message(), event.type(), event.durationMillis()));
    }

    /**
     * Updates the lifetime of all active notifications.
     * This method must be called on each frame.
     */
    private void update(){
        long now = System.currentTimeMillis();
        long elapsedTime = now - lastUpdateTime;
        this.lastUpdateTime = now;
        // If there is no current notification but there is in the queue, it displays the next one.
        if(this.currentNotification == null && !this.notificationQueue.isEmpty()) {
            this.currentNotification = this.notificationQueue.poll();
        }
        // If there is an active notification, it updates its timer.
        if(this.currentNotification != null){
            this.currentNotification.updateTimer(elapsedTime);
            if(this.currentNotification.isExpired()){
                this.currentNotification = null;
            }
        }
    }

    /**
     * Handles mouse clicks. If the close button is clicked
     * of the current notification, it discards it.
     * @param mouseX x mouse coordinates
     * @param mouseY y mouse coordinates
     */
    public void handleMousePress(int mouseX, int mouseY) {
        if(this.currentNotification != null && this.currentNotification.isCloseButtonHovered(mouseX, mouseY)){
            currentNotification = null;
        }
    }

    /**
     * Returns the currently displayed notification.
     * @return A notification
     */
    public Notification getActiveNotification() {
        return this.currentNotification;
    }

    /**
     * Internal class to represent an active notification.
     */
    public static final class Notification {
        private final String message;
        private final NotificationType type;
        private long timeLeft;
        public Rectangle2D.Float closeButtonPounds; // Closing button click area
        // Constant
        private static final int FADE_DURATION_MS = 600;

        /**
         * Build a new notification
         * @param message The message saved in the notification.
         * @param type The type of notification.
         * @param durationMillis the time that expired.
         */
        Notification(String message, NotificationType type, int durationMillis) {
            this.message = message;
            this.type = Objects.requireNonNull(type, "Type cannot be null.");
            this.timeLeft = durationMillis;
        }

        /**
         * Verify whether the expiration time has passed or not.
         * @return True if already passed, false otherwise
         */
        public boolean isExpired() {
            return this.timeLeft <= 0;
        }

        /**
         * Calculates the current opacity (alpha) of the notification.
         * Remains fully opaque and then fades out during the last FADE_DURATION_MS.
         * @return an opacity value between 0 (invisible) and 255 (fully visible).
         */
        public int alpha(){
            if (timeLeft > FADE_DURATION_MS) return 255;
            if (timeLeft <= 0) return 0;
            return (int) PApplet.map(timeLeft, 0, FADE_DURATION_MS, 0, 255);
        }

        public void updateTimer(long elapsedTimer){
            this.timeLeft -= elapsedTimer;
        }

        public boolean isCloseButtonHovered(int mouseX, int mouseY) {
            return this.closeButtonPounds != null && this.closeButtonPounds.contains(mouseX, mouseY);
        }

        // --- Getters ---

        /**
         * Return message
         * @return A text string
         */
        public String getMessage() {
            return this.message;
        }

        /**
         * Return the type's message.
         * @return A NotificationType instance.
         */
        public NotificationType getType() {
            return this.type;
        }
    }
}
