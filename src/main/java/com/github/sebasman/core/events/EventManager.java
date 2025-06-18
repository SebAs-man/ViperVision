package com.github.sebasman.core.events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Implementation of a centralized, type-safe event bus.
 * Uses the Singleton pattern to be globally accessible and Java generics
 * To provide strongly typed notifications without the need for instanceof
 * or ‘casts’ on subscribers.
 */
public class EventManager {
    private static final EventManager INSTANCE = new EventManager();
    // The map of listeners
    private final Map<Class<? extends GameEvent>, List<Consumer<?>>> listeners = new HashMap<>();

    /**
     * Constructor privado para forzar el uso del patrón Singleton.
     */
    private EventManager() {}

    /**
     * Obtiene la instancia única del EventManager.
     * @return La instancia Singleton del EventManager.
     */
    public static EventManager getInstance() {
        return INSTANCE;
    }

    /**
     * Subscribes an action (listener) to a specific event type.
     * The use of generic <T> ensures that the type of the event class
     * matches the type of the consumer, ensuring compile-time safety.
     * @param eventType The class of the event to be subscribed to (e.g., ScoreChangedEvent.class).
     * @param listener The action (lambda or method reference) to be executed when the event occurs.
     * @param <T> The type of the event, inferred from the eventType argument.
     */
    public <T extends GameEvent> void subscribe(Class<T> eventType, Consumer<T> listener) {
        // computeIfAbsent gets the existing list or creates a new one if it does not exist.
        List<Consumer<?>> eventListeners = this.listeners.computeIfAbsent(eventType, _ -> new LinkedList<>());
        eventListeners.add(listener);
    }

    public <T extends GameEvent> void unsubscribe(Class<T> eventType, Consumer<T> listener) {
        List<Consumer<?>> eventListeners = this.listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }

    /**
     * Publishes an event, notifying all interested subscribers.
     * This method is safe because it only retrieves the list associated with the exact class
     * of the event object being notified.
     * @param event The event object to be notified.
     * @param <T> The type of the event.
     */
    @SuppressWarnings("unchecked") // This cast is safe by design logic.
    public <T extends GameEvent> void notify(T event) {
        // Get the list of listeners for the exact class of this event.
        List<Consumer<?>> eventListeners = this.listeners.get(event.getClass());
        if (eventListeners != null) {
            for (Consumer<?> listener : new LinkedList<>(eventListeners)) {
                // We do a safe cast because we know that the map guarantees that this
                // listener was subscribed for this exact type of event (T).
                // The compiler can't know, but we can, so we suppress the warning.
                final Consumer<T> typedListener = (Consumer<T>) listener;
                typedListener.accept(event);
            }
        }
    }
}
