package com.github.sebasman.presenter;

import com.github.sebasman.contracts.events.types.ScoreUpdatedEvent;


/**
 * A specific Presenter for the HUD (Heads-Up Display).
 * Its sole responsibility is to maintain the status of the data to be displayed on the UI
 * (such as score text) and provide methods for it to be updated
 * in response to game events. You do not subscribe to yourself.
 */
public final class HUDController {
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

    /**
     * Returns the current score text, ready to be rendered.
     * @return The current score as a String.
     */
    public String getScoreText() {
        return scoreText;
    }

    /**
     * Returns the text of the current high score, ready to be rendered.
     * @return Highest score as a String.
     */
    public String getHighScoreText() {
        return highScoreText;
    }
}
