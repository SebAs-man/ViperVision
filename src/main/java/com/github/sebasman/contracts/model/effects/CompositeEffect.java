package com.github.sebasman.contracts.model.effects;

import com.github.sebasman.contracts.model.IGameSession;

import java.util.Set;

/**
 * Implements the Composite pattern for game effects.
 * This allows treating a collection of effects as a single effect.
 * When applied, it applies all the effects it contains in order.
 */
public final class CompositeEffect implements IEffect {
    private final Set<IEffect> effects;

    /**
     * Constructs a CompositeEffect that groups multiple individual effects.
     * @param effects The effects to be applied together.
     */
    public CompositeEffect(IEffect... effects) {
        if(effects.length == 0) throw new IllegalArgumentException("Effect must have at least one effect");
        this.effects = Set.of(effects);
    }

    @Override
    public void apply(IGameSession session) {
        for(IEffect effect : effects) {
            effect.apply(session);
        }
    }
}
