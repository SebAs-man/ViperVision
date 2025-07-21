package com.github.sebasman.view.audio;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.ButtonPressedEvent;
import com.github.sebasman.contracts.events.types.FoodEatenEvent;
import com.github.sebasman.contracts.events.types.SnakeDiedEvent;
import processing.core.PApplet;
import processing.sound.SoundFile;

/**
 * Manages the playback of all sounds and sound effects in the game.
 * Subscribes to EventManager events to react in a decoupled way
 * to actions occurring in the game and the UI.
 */
public final class SoundManager {
    // Flag to ensure assets are loaded only once
    private static boolean isLoaded = false;
    // Sound used in the game
    private static SoundFile clickSound;
    private static SoundFile eatSound;
    private static SoundFile gameOverSound;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private SoundManager() {}

    /**
     * Loads all game sounds. Must be called once
     * at startup, from the main class that extends PApplet.
     * @param context The instance of PApplet to use its load methods.
     */
    public static void load(PApplet context){
        if(isLoaded){
            System.err.println("Assets already loaded, skipping...");
            return; // Sounds are already loaded, skip loading
        }
        // Set the flag to true to prevent reloading
        isLoaded = true;
        // Load sounds, handle exceptions if any
        System.out.println("Loading sounds...");
        clickSound = new SoundFile(context, "sounds/click.wav");
        eatSound = new SoundFile(context, "sounds/eat.wav");
        gameOverSound = new SoundFile(context, "sounds/gameover.wav");
        try{
            subscribeToEvents();
            System.out.println("Sound system initialized.");
        } catch (Exception e){
            System.err.println("Failed to properly load sound");
        }
    }

    /**
     * Subscribes the sound playback methods to the global events of the game.
     * Must be called only once from the application factory.
     */
    private static void subscribeToEvents() {
        EventManager eventManager = EventManager.getInstance();
        eventManager.subscribe(ButtonPressedEvent.class, event -> onButtonPressed());
        eventManager.subscribe(FoodEatenEvent.class, event -> onFoodEaten());
        eventManager.subscribe(SnakeDiedEvent.class, event -> onSnakeDied());
    }

    /**
     * Action executed when a button is pressed
     */
    private static void onButtonPressed() {
        if (clickSound != null) {
            clickSound.play();
        }
    }

    /**
     * Action performed when food is consumed
     */
    private static void onFoodEaten() {
        if (eatSound != null) {
            eatSound.play();
        }
    }

    /**
     * Action to be performed when the snake dies
     */
    private static void onSnakeDied() {
        if (gameOverSound != null) {
            gameOverSound.play();
        }
    }
}
