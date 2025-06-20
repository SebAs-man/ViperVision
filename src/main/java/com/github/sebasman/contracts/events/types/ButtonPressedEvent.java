package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;
import com.github.sebasman.contracts.view.IUiComponent;

/**
 * Event that is published when any UI component is pressed.
 * @param component The component that originated the event
 */
public record ButtonPressedEvent(IUiComponent component) implements IGameEvent {
}
