package com.github.sebasman.presenter.listeners;

import com.github.sebasman.contracts.events.types.ScoreUpdatedEvent;
import com.github.sebasman.contracts.presenter.IHUDController;


/**
 * A specific Presenter for the HUD (Heads-Up Display).
 * Its sole responsibility is to maintain the status of the data to be displayed on the UI
 * (such as score text) and provide methods for it to be updated
 * in response to game events. You do not subscribe to yourself.
 */
public final class HUDController implements IHUDController {
    private String scoreText;
    private String highScoreText;

    /**
     * Builds the HUD controller.
     * @param initialHighScore The initial high score to display at startup.
     */
    public HUDController(int initialScore, int initialHighScore) {
        this.scoreText = String.valueOf(initialScore);
        this.highScoreText = String.valueOf(initialHighScore);
    }

    /**
     * Public method that handles the logic when a score update event occurs.
     * This method will be called by the listener that the ‘PlayingState’ subscribes to.
     * @param event The event containing the new score data.
     */
    public void onScoreUpdate(ScoreUpdatedEvent event) {
        this.scoreText = String.valueOf(event.score());
        this.highScoreText = String.valueOf(event.highScore());
    }

    @Override
    public String getScoreText() {
        return scoreText;
    }

    @Override
    public String getHighScoreText() {
        return highScoreText;
    }
}
