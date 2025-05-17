package com.github.sebasman.model;

/**
 * Represents the various states of a game.
 * This enum defines the lifecycle stages of the game, starting from initialization
 * to its completion. Each state signifies a specific phase within the game flow.
 * The GameState enum can be used to track and control the progression of the game.
 * The states include:
 * - INITIALIZING: The game is initializing and setting up resources.
 * - MENU: The main menu or game options screen is displayed.
 * - PREPARING: Preparation phase, such as before starting gameplay.
 * - PLAYING: The main gameplay phase is in progress.
 * - PAUSE: The game is paused and the current state is preserved.
 * - GAME_OVER: The game has ended with a loss or failure.
 * - WIN: The game has ended successfully with a victory.
 * - ENDING: The final phase of the game, such as closing screens or animations.
 */
public enum GameState {
    INITIALIZING,
    MENU,
    PREPARING,
    PLAYING,
    PAUSE,
    GAME_OVER,
    WIN,
    ENDING
}
