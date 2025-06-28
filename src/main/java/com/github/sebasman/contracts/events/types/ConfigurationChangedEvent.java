package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;

import java.util.Objects;

/**
 * Published when a configuration value is changed through a UI component.
 * It uses a key to identify the setting and an Object to hold the new value.
 * @param key   A unique String identifier for the configuration (e.g., "SNAKE_SPEED").
 * @param value The new value of the configuration (e.g., a Float, Boolean, etc.).
 */
public record ConfigurationChangedEvent(String key, Object value) implements IGameEvent {
    /**
     * Constructor of a new Event
     * @param key String that contains the key of the configuration
     * @param value Generic value that contains the value of the configuration
     */
    public ConfigurationChangedEvent(String key, Object value) {
        this.key = Objects.requireNonNull(key, "Key cannot be null");
        this.value = Objects.requireNonNull(value, "Value cannot be null");
    }
}
