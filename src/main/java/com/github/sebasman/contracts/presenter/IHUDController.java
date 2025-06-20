package com.github.sebasman.contracts.presenter;

/**
 * Defines the contract for a HUD driver.
 * Exposes the data that the View (HUDRenderer) needs to draw.
 */
public interface IHUDController {
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
