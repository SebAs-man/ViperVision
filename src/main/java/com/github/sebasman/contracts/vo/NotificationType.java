package com.github.sebasman.contracts.vo;

import com.github.sebasman.view.assets.Assets;
import processing.core.PImage;

/**
 * Enumeration defining the types of existing notices
 */
public enum NotificationType {
    INFO("Info", null),
    ERROR("Error", Assets.appleImage),
    ACHIEVEMENT("Achievement", Assets.appleImage),
    WARNING("Warning", null),;

    private final String title;
    private final PImage icon;

    /**
     * Canonical constructor with predefined values for constants
     * @param title The title of the text message
     * @param icon The text message icon
     */
    NotificationType(String title, PImage icon){
        this.title = title;
        this.icon = icon;
    }

    /**
     * Gets the message title.
     * @return A string text.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the message icon.
     * @return An image.
     */
    public PImage getIcon() {
        return icon;
    }
}
