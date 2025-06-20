package com.github.sebasman.view.audio;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.ButtonPressedEvent;
import com.github.sebasman.contracts.events.types.FoodEatenEvent;
import com.github.sebasman.contracts.events.types.SnakeDiedEvent;

/**
 * Manages the playback of all sounds and sound effects in the game.
 * Subscribes to EventManager events to react in a decoupled way
 * to actions occurring in the game and the UI.
 */
public final class SoundManager {
    /**
     * Builds the SoundManager and immediately subscribes to game events.
     */
    public SoundManager(){
        this.subscribeToEvents();
    }

    /**
     * Subscribe to events that play sounds
     */
    private void subscribeToEvents() {
        EventManager manager = EventManager.getInstance();
        manager.subscribe(ButtonPressedEvent.class, _ -> this.playButtonClickSound());
        manager.subscribe(FoodEatenEvent.class, _ -> playEatSound());
        manager.subscribe(SnakeDiedEvent.class, _ -> playGameOverSound());
    }

    /**
     * Action executed when a button is pressed
     */
    private void playButtonClickSound() {
        System.out.println("DEBUG: *Click* (Sonido de botón presionado)");
    }

    /**
     * Action performed when food is consumed
     */
    private void playEatSound() {
        System.out.println("DEBUG: *Crunch* (Sonido de comer)");
    }

    /**
     * Action to be performed when the snake dies
     */
    private void playGameOverSound() {
        System.out.println("DEBUG: *Womp womp* (Sonido de Game Over)");
    }
}
