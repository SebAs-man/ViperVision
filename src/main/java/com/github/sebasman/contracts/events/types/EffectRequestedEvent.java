package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;
import com.github.sebasman.contracts.model.effects.IEffect;

/**
 * Issued when an entity (such as a food) requests that an effect be applied
 * to the game state. This event carries the effect as a Command object.
 * @param effect The specific effect to apply.
 */
public record EffectRequestedEvent(IEffect effect) implements IGameEvent {
}
