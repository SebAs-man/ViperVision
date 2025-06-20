package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;

import java.util.Objects;

/**
 *
 * @param key
 * @param value
 * @param <T>
 */
public record ConfigurationChangedEvent<T>(String key, T value) implements IGameEvent {
    /**
     * Constructor of a new Event
     * @param key String that contains the key of the configuration
     * @param value Generic value that contains the value of the configuration
     */
    public ConfigurationChangedEvent(String key, T value) {
        this.key = Objects.requireNonNull(key, "Key cannot be null");
        this.value = Objects.requireNonNull(value, "Value cannot be null");
    }
}
