package com.github.sebasman.presenter.listeners;


import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.ScoreUpdatedEvent;
import com.github.sebasman.model.GameSession;
import com.github.sebasman.model.UserProfile;
import com.github.sebasman.view.GameView;
import com.github.sebasman.contracts.events.types.FoodEatenEvent;
import com.github.sebasman.presenter.states.GameOverState;

/**
 * Contains the business logic of the game that events trigger.
 * Updates model state (GameSession, UserProfile) and publishes new
 * events as a result (e.g., ScoreUpdatedEvent).
 */
public class GameLogicCoordinator {
    private final GameView game;

    /**
     * Creates a logic coordinator for a specific game session.
     * @param game The main instance of the application, used as context.
     */
    public GameLogicCoordinator(GameView game) {
        if(game == null){
            throw new NullPointerException("Game cannot be null");
        }
        this.game = game;
    }

    /**
     * Logic to be executed when the snake eats.
     * Gets the current state of the session from the game context.
     * @param event The event that contains the relevant data (although in this version it is not used).
     */
    public void onFoodEaten(FoodEatenEvent event) {
        GameSession session = game.getSession();
        UserProfile profile = game.getProfile();
        if (session == null || profile == null) return;
        // Increases the score of the current session.
        session.incrementScore(event.food().getScoreValue());
        // Compare with the high score and update the profile if necessary.
        if(session.getScore() > profile.getHighScore()){
            profile.setHighScore(session.getScore());
        }
        // Notifies a new event with BOTH scores for the UI to receive.
        EventManager.getInstance().notify(new ScoreUpdatedEvent(session.getScore(), profile.getHighScore()));
        // Execute the rest of the game logic.
        session.getSnake().grow();
        session.getFood().spawn(session.getSnake().getBodySet());
    }

    /**
     * The logic to execute when the snake dies.
     */
    public void onSnakeDied() {
        game.changeState(GameOverState.getInstance());
    }
}
