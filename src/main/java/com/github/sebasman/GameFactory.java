package com.github.sebasman;

import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.presenter.states.StartingState;
import com.github.sebasman.view.GameView;
import com.github.sebasman.view.audio.SoundManager;

/**
 * Applies the Factory pattern to encapsulate the creation and initial configuration logic of the application.
 * Initial configuration logic of the application. Acts as the centralized Composition Root.
 */
public final class GameFactory {
    /**
     * Creates and configures all the objects needed to start the game.
     * @return a GameView instance ready to be executed by PApplet.
     */
    public static GameView createGame(){
        // Initialize global systems such as the sound system.
        // The SoundManager subscribes to events in its own constructor.
        new SoundManager();
        // Get the initial instances
        IState initialState = StartingState.getInstance();
        // Create the main instance of the game, injecting the dependencies.
        return new GameView(initialState);
    }
}
