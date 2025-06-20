package com.github.sebasman;

import com.github.sebasman.view.GameView;
import processing.core.PApplet;

/**
 * Main class that serves as the entry point for the application.
 * Its only function is to launch the Processing sketch (the Game class).
 */
public class Main {
    public static void main(String[] args){
        GameView game = GameFactory.createGame();
        String[] processingArgs = {"ViperVision"};
        PApplet.runSketch(processingArgs, game);
    }
}
