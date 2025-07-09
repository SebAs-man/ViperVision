package com.github.sebasman.contracts.presenter;

/**
 * Defines the contract for a HUD driver.
 * Exposes the data that the View (HUDRenderer) needs to draw.
 */
public interface IHUDController {
    /**
     * Initializes or resets the HUD values. This method can be called
     * at the start of a new game to ensure that the score is shown as ‘0.’
     * @param score The initial score.
     * @param highScore The current high score.
     */
    void initialize(int score, int highScore);

    /**
     * Returns the current score text, ready to be rendered.
     * @return The current score as a String.
     */
    String getScoreText();

    /**
     * Returns the text of the current high score, ready to be rendered.
     * @return Highest score as a String.
     */
    String getHighScoreText();
}
