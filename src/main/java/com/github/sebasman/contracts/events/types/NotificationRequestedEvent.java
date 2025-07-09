package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;
import com.github.sebasman.contracts.vo.NotificationType;

/**
 * Is posted when a part of the system needs to display a temporary message to the user.
 * @param message The text of the message to display.
 * @param type The type of notification
 * @param durationMillis The duration in milliseconds the message should remain on screen.
 */
public record NotificationRequestedEvent(String message, NotificationType type, int durationMillis) implements IGameEvent {
}
