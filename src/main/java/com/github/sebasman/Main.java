package com.github.sebasman;

import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.presenter.states.StartingState;
import com.github.sebasman.view.GameView;
import processing.core.PApplet;

/**
 * Main class that serves as the entry point for the application.
 * Its only function is to launch the Processing sketch (the Game class).
 */
public final class Main {
    /**
     * Initial game input
     * @param args additional arguments required
     */
    public static void main(String[] args){
        // Get the initial instances
        IState initialState = StartingState.getInstance();
        GameView game = new GameView(initialState);
        String[] processingArgs = {"ViperVision"};
        PApplet.runSketch(processingArgs, game);
    }

    /**
     * Private constructor to prevent instantiation, since it is a utility class.
     */
    private Main(){}
}
