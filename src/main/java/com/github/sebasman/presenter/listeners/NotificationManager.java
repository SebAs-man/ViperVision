package com.github.sebasman.presenter.listeners;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.FrameUpdatedEvent;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.vo.NotificationType;
import com.github.sebasman.model.config.ModelConfig;
import processing.core.PApplet;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Manages the logic of modal notifications. Listens for events and maintains
 * a list of active messages with their remaining lifetime.
 */
public final class NotificationManager {
    private static final NotificationManager INSTANCE = new NotificationManager();
    private final Queue<Notification> notificationQueue;
    private final Queue<Notification> currentNotifications;
    private long lastUpdateTime;

    /**
     * Private constructor to prevent instantiation.
     */
    private NotificationManager() {
        this.notificationQueue = new ArrayDeque<>();
        this.currentNotifications = new ArrayBlockingQueue<>(ModelConfig.MAX_NOTIFICATIONS_SHOW);
        this.lastUpdateTime = System.currentTimeMillis();
        EventManager.getInstance().subscribe(NotificationRequestedEvent.class, this::addNotification);
        EventManager.getInstance().subscribe(FrameUpdatedEvent.class, event -> this.update());
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
        // Updates the queue of notifications shown to the user
        while(true) {
            Notification temp = this.notificationQueue.peek();
            if(temp == null) break;
            boolean added = this.currentNotifications.offer(temp);
            if(!added) break;
            this.notificationQueue.poll();
        }
        // Updates elapsed time of notifications
        long now = System.currentTimeMillis();
        long elapsedTime = now - this.lastUpdateTime;
        this.lastUpdateTime =  now;
        for(Notification notification : this.currentNotifications) {
            notification.updateTimer(elapsedTime);
            if(notification.isExpired()){
                this.currentNotifications.remove(notification);
            }
        }
    }

    /**
     * Returns the currently displayed notification.
     * @return A notification
     */
    public Queue<Notification> getActiveNotification() {
        return this.currentNotifications;
    }

    /**
     * Internal class to represent an active notification.
     */
    public static final class Notification {
        private final String message;
        private final NotificationType type;
        private long timeLeft;
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
