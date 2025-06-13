package com.github.sebasman;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.State;
import com.github.sebasman.core.interfaces.UiRenderAPI;
import com.github.sebasman.states.StartingState;
import com.github.sebasman.ui.GameUiImpl;
import processing.core.PApplet;

/**
 * Main class that serves as the entry point for the application.
 * Its only function is to launch the Processing sketch (the Game class).
 */
public class Main {
    public static void main(String[] args){
        State initialState = StartingState.getInstance();
        UiRenderAPI render = GameUiImpl.getInstance();
        // Create the game instance with the initial state and the factory for the playing state.
        Game game = new Game(initialState, render);
        String[] processingArgs = {"ViperVision"};
        PApplet.runSketch(processingArgs, game);
    }
}
