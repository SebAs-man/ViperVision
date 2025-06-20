package com.github.sebasman;

import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.presenter.states.StartingState;
import com.github.sebasman.view.GameView;
import com.github.sebasman.view.audio.SoundManager;
import processing.core.PApplet;

/**
 * Main class that serves as the entry point for the application.
 * Its only function is to launch the Processing sketch (the Game class).
 */
public class Main {
    public static void main(String[] args){
        // Initialize global systems such as the sound system.
        // The SoundManager subscribes to events in its own constructor.
        new SoundManager();
        // Get the initial instances
        IState initialState = StartingState.getInstance();
        GameView game = new GameView(initialState);
        String[] processingArgs = {"ViperVision"};
        PApplet.runSketch(processingArgs, game);
    }
}
