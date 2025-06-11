package com.github.sebasman;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.State;
import com.github.sebasman.states.StartingState;
import processing.core.PApplet;

/**
 * Main class that serves as the entry point for the application.
 * Its only function is to launch the Processing sketch (the Game class).
 */
public class Main {
    public static void main(String[] args){
        State initialState = StartingState.getInstance();
        // Create the game instance with the initial state and the factory for the playing state.
        Game game = new Game(initialState);
        String[] processingArgs = {"ViperVision"};
        PApplet.runSketch(processingArgs, game);
    }
}
