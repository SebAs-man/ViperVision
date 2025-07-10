package com.github.sebasman.presenter.listeners;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.EffectRequestedEvent;
import com.github.sebasman.contracts.model.IGameSession;

import java.util.function.Consumer;

/**
 * A dedicated listener that manages the application of all the effects in the game.
 * Listens to EffectRequestedEvent and executes the contained effect on the
 * current game session.
 */
public class EffectManager {
    private final IGameSession session;
    // Listeners
    private final Consumer<EffectRequestedEvent> onHandleEffectRequest;

    /**
     * Creates a new listener that applies the effects of the game session.
     * @param session The gaming session
     */
    public EffectManager(IGameSession session) {
        this.session = session;
        // Create instances of listeners
        this.onHandleEffectRequest = this::handleEffectRequest;
    }

    /**
     * Subscribe to the event of request of effects.
     */
    public void suscribeEvents(){
        EventManager.getInstance().subscribe(EffectRequestedEvent.class, this.onHandleEffectRequest);
    }

    /**
     * Unsubscribes to prevent memory leaks.
     */
    public void unSuscribeEvents(){
        EventManager.getInstance().unsubscribe(EffectRequestedEvent.class, this.onHandleEffectRequest);
    }

    /**
     * Applies the effect of the event
     * @param event The event listened
     */
    private void handleEffectRequest(EffectRequestedEvent event) {
        if(this.session != null){
            event.effect().apply(this.session);
        }
    }
}
