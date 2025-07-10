package com.github.sebasman.presenter.listeners;


import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.NewHighScoreEvent;
import com.github.sebasman.contracts.events.types.ScoreUpdatedEvent;
import com.github.sebasman.contracts.events.types.SnakeDiedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.IUserProfile;
import com.github.sebasman.contracts.model.entities.IFoodAPI;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.events.types.FoodEatenEvent;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.entities.foods.FoodFactory;
import com.github.sebasman.presenter.states.GameOverState;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Contains the business logic of the game that events trigger.
 * Updates model state (GameSession, UserProfile) and publishes new
 * events as a result (e.g., ScoreUpdatedEvent).
 */
public final class GameLogicCoordinator {
    private final IGameContext game;
    private final FoodFactory factory;
    private final Random random;
    // Listeners
    private final Consumer<FoodEatenEvent> onFoodEatenListener;
    private final Consumer<SnakeDiedEvent> onSnakeDiedListener;
    private final Consumer<ScoreUpdatedEvent> onScoreUpdatedListener;

    /**
     * Creates a logic coordinator for a specific game session.
     * @param game The main instance of the application, used as context.
     */
    public GameLogicCoordinator(IGameContext game) {
        if(game == null){
            throw new NullPointerException("Game cannot be null");
        }
        this.game = game;
        this.factory = new FoodFactory();
        this.random = new Random();
        // Create instances of listeners
        this.onFoodEatenListener = this::onFoodEaten;
        this.onSnakeDiedListener = _ -> this.onSnakeDied();
        this.onScoreUpdatedListener = this::onSessionScoreUpdated;
    }

    /**
     * Subscribes to the corresponding events
     */
    public void subscribeToEvents() {
        EventManager manager = EventManager.getInstance();
        manager.subscribe(FoodEatenEvent.class, this.onFoodEatenListener);
        manager.subscribe(SnakeDiedEvent.class, this.onSnakeDiedListener);
        manager.subscribe(ScoreUpdatedEvent.class, this.onScoreUpdatedListener);
    }

    /**
     * Unsubscribes to subscribe events to avoid ghost events
     */
    public void unsubscribeFromEvents() {
        EventManager manager = EventManager.getInstance();
        manager.unsubscribe(FoodEatenEvent.class, this.onFoodEatenListener);
        manager.unsubscribe(SnakeDiedEvent.class, this.onSnakeDiedListener);
        manager.unsubscribe(ScoreUpdatedEvent.class, this.onScoreUpdatedListener);
    }

    /**
     * This method is activated EVERY TIME the score of the session changes.
     * Your only responsibility is to check if the high score has been exceeded.
     */
    private void onSessionScoreUpdated(ScoreUpdatedEvent event) {
        IUserProfile profile = game.getProfile();
        if (event.score() > profile.getHighScore()) {
            profile.setHighScore(event.score());
            EventManager.getInstance().notify(new NewHighScoreEvent(profile.getHighScore()));
        }
    }

    /**
     * Logic to be executed when the snake eats.
     * Gets the current state of the session from the game context.
     * @param event The event that contains the relevant data (although in this version it is not used).
     */
    private void onFoodEaten(FoodEatenEvent event) {
        IGameSession session = game.getSession();
        if (session == null) return;

        IFoodAPI food = event.food();
        // Apply the effect of the food that has just been eaten.
        food.applyEffect(session);
        // Removes consumed food from the game world list.
        session.removeFood(food);
        // Uses the factory to create new food.
        Set<Position> allOccupiedSpots = new HashSet<>(session.getSnake().getBodySet());
        allOccupiedSpots.addAll(session.getBoard().getObstacles());
        if(session.getFoods().isEmpty()){
            int amount = this.random.nextInt(3) + 1;
            for(int i = 0; i < amount; i++){
                IFoodAPI newFood = factory.createRandomFood(allOccupiedSpots);
                if(newFood == null) break;
                session.addFood(newFood);
            }
        }
    }

    /**
     * The logic to execute when the snake dies.
     */
    private void onSnakeDied() {
        game.changeState(GameOverState.getInstance());
    }
}
