package com.github.sebasman.presenter.listeners;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.NewHighScoreEvent;
import com.github.sebasman.contracts.events.types.ScoreUpdatedEvent;
import com.github.sebasman.contracts.presenter.IHUDController;


/**
 * A specific Presenter for the HUD (Heads-Up Display).
 * Its sole responsibility is to maintain the status of the data to be displayed on the UI
 * (such as score text) and provide methods for it to be updated
 * in response to game events. You do not subscribe to yourself.
 */
public final class HUDController implements IHUDController {
    private static final IHUDController INSTANCE = new HUDController();

    private String scoreText;
    private String highScoreText;

    /**
     * Private constructor to prevent instantiation.
     */
    private HUDController() {
        this.scoreText = "0";
        this.highScoreText = "0";
        EventManager.getInstance().subscribe(ScoreUpdatedEvent.class, this::onScoreUpdate);
        EventManager.getInstance().subscribe(NewHighScoreEvent.class, this::onHighScoreUpdate);
    }

    /**
     * Returns the single instance of the HUD driver.
     * @return Singleton instance.
     */
    public static IHUDController getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(int score, int highScore) {
        this.scoreText = String.valueOf(score);
        this.highScoreText = String.valueOf(highScore);
    }


    /**
     * Public method that handles the logic when a score update event occurs.
     * @param event The event containing the new score data.
     */
    private void onScoreUpdate(ScoreUpdatedEvent event) {
        this.scoreText = String.valueOf(event.score());
    }

    /**
     * Public method that handles the logic when a high score update event occurs.
     * @param event The event containing the new high score data.
     */
    private void onHighScoreUpdate(NewHighScoreEvent event) {
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
