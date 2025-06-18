package com.github.sebasman.core.events.listeners;


import com.github.sebasman.core.Game;
import com.github.sebasman.core.events.EventManager;
import com.github.sebasman.core.events.messages.FoodEatenEvent;
import com.github.sebasman.core.events.messages.SnakeDiedEvent;
import com.github.sebasman.states.GameOverState;

/**
 * Contains the business logic of the game that events trigger.
 * This class does NOT subscribe to itself. It acts as a provider of logical methods
 * that are subscribed and unsubscribed by the state that manages the game session (PlayingState).
 */
public class GameLogicCoordinator {
    private final Game game;

    /**
     * Creates a logic coordinator for a specific game session.
     * @param game The game instance for this session.
     */
    public GameLogicCoordinator(Game game) {
        if(game == null){
            throw new NullPointerException("Game cannot be null");
        }
        this.game = game;
    }

    /**
     * The logic to execute when the snake eats.
     * @param event The event containing the relevant data.
     */
    public void onFoodEaten(FoodEatenEvent event) {
        game.incrementScore(event.food.getScoreValue());
        game.getSnake().grow();
        game.getFood().spawn(game.getSnake().getBodySet());
    }

    /**
     * The logic to execute when the snake dies.
     * @param event The event of the snake's death.
     */
    public void onSnakeDied(SnakeDiedEvent event) {
        game.changeState(GameOverState.getInstance());
    }
}
