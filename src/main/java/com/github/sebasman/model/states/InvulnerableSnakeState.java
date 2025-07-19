package com.github.sebasman.model.states;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.entities.IExpirable;
import com.github.sebasman.contracts.model.entities.ISnakeAPI;
import com.github.sebasman.contracts.model.states.ISnakeState;

/**
 * Defines the state of the snake when it enters madness,
 * this allows it to bypass all possible logic in the game.
 */
public final class InvulnerableSnakeState implements ISnakeState, IExpirable {
    private long timeLeft;

    /**
     * Creates a new instance of the state with a predetermined duration
     * @param durationMillis The duration of this state.
     */
    public InvulnerableSnakeState(long durationMillis){
        this.timeLeft = durationMillis;
    }

    @Override
    public void handleCollision(ISnakeAPI snake, IGameSession session) {
        // In the invulnerable state, collisions are ignored.
    }

    @Override
    public void update(long elapsedTime) {
        this.timeLeft -= elapsedTime;
    }

    @Override
    public boolean isExpired() {
        return this.timeLeft <= 0;
    }
}
