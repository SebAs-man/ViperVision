package com.github.sebasman.contracts.vo;

import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Enumeration defining the types of existing notices
 */
public enum NotificationType {
    INFO("Info"),
    ERROR("Error"),
    ACHIEVEMENT("Achievement"),
    WARNING("Warning"),;

    private final String title;

    /**
     * Canonical constructor with predefined values for constants
     * @param title The title of the text message
     */
    NotificationType(String title){
        this.title = title;
    }

    /**
     * Gets the message title.
     * @return A string text.
     */
    public String getTitle() {
        return title;
    }
}
