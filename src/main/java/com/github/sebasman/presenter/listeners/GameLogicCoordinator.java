package com.github.sebasman.presenter.listeners;


import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.*;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.IUserProfile;
import com.github.sebasman.contracts.model.effects.IEffect;
import com.github.sebasman.contracts.model.effects.ITimedEffect;
import com.github.sebasman.contracts.model.entities.IFoodAPI;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.FoodFactory;
import com.github.sebasman.model.entities.foods.ExpirableFood;
import com.github.sebasman.presenter.states.GameOverState;

import java.util.*;
import java.util.function.Consumer;

/**
 * The central coordinator of the game logic.
 * It subscribes to all business and effect events, and is solely
 * responsible for modifying the model state (GameSession, UserProfile).
 */
public final class GameLogicCoordinator {
    private final IGameContext game;
    private long lastUpdateTime;
    // Listeners
    private final Consumer<FoodEatenEvent> onFoodEatenListener;
    private final Consumer<SnakeDiedEvent> onSnakeDiedListener;
    private final Consumer<ScoreUpdatedEvent> onScoreUpdatedListener;
    private final Consumer<EffectRequestedEvent> onEffectRequestedListener;
    /**
     * The maximum time allowed for a frame in nanoseconds before it is considered
     * an anomalous “jump.” If the elapsed time is greater than this,
     * the timer will be reset to avoid the “catch-up.”
     */
    private static final long MAX_FRAME_TIME_NS = 100_000_000; // 100ms

    /**
     * Creates a logic coordinator for a specific game session.
     * @param game The main instance of the application, used as context.
     */
    public GameLogicCoordinator(IGameContext game) {
        this.game = Objects.requireNonNull(game, "The game must not be null.");
        this.lastUpdateTime = System.currentTimeMillis();
        // Create instances of listeners
        this.onFoodEatenListener = this::onFoodEaten;
        this.onEffectRequestedListener =this::onEffectRequested;
        this.onScoreUpdatedListener = this::onSessionScoreUpdated;
        this.onSnakeDiedListener = event -> this.onSnakeDied();
    }

    /**
     * Subscribes to the corresponding events
     */
    public void subscribeToEvents() {
        EventManager manager = EventManager.getInstance();
        manager.subscribe(FoodEatenEvent.class, this.onFoodEatenListener);
        manager.subscribe(SnakeDiedEvent.class, this.onSnakeDiedListener);
        manager.subscribe(EffectRequestedEvent.class, this.onEffectRequestedListener);
        manager.subscribe(ScoreUpdatedEvent.class, this.onScoreUpdatedListener);
    }

    /**
     * Unsubscribes to subscribe events to avoid ghost events
     */
    public void unsubscribeFromEvents() {
        EventManager manager = EventManager.getInstance();
        manager.unsubscribe(FoodEatenEvent.class, this.onFoodEatenListener);
        manager.unsubscribe(SnakeDiedEvent.class, this.onSnakeDiedListener);
        manager.unsubscribe(EffectRequestedEvent.class, this.onEffectRequestedListener);
        manager.unsubscribe(ScoreUpdatedEvent.class, this.onScoreUpdatedListener);
    }

    /**
     * Called on every frame. Updates the timers for all expirable food items.
     * Use an explicit Iterator to safely remove items from the list while iterating,
     * thus preventing ConcurrentModificationException.
     */
    public void update() {
        IGameSession session = game.getSession();
        if(session == null || session.getFoods().isEmpty()) return;

        long now = System.currentTimeMillis();
        long elapsedTime = now - this.lastUpdateTime;

        if(elapsedTime > MAX_FRAME_TIME_NS / 1_000_000) {
            this.lastUpdateTime = now;
            return;
        }

        this.lastUpdateTime = now;

        // --- Temporary effects management ---

        Iterator<ITimedEffect> effectIterator = session.getActiveEffects().iterator();
        while(effectIterator.hasNext()){
            ITimedEffect effect = effectIterator.next();
            effect.update(elapsedTime);
            if(effect.isExpired()){
                effect.onFinish(session);
                effectIterator.remove();
            }
        }

        // --- Expired food management ---

        Iterator<IFoodAPI> iterator = session.getFoods().iterator();
        while (iterator.hasNext()) {
            IFoodAPI food = iterator.next();
            if(food instanceof ExpirableFood expirableFood){
                expirableFood.update(elapsedTime);
                if(expirableFood.isExpired()) {
                    iterator.remove();
                    boolean positiveFoodRemains = session.getFoods().stream()
                            .anyMatch(IFoodAPI::countsForRespawn);
                    if(!positiveFoodRemains){
                        this.spawnFood();
                    }
                }
            }
        }
    }

    /**
     * Applies the effect of the event
     * @param event The event listened
     */
    private void onEffectRequested(EffectRequestedEvent event) {
        IGameSession session = game.getSession();
        if (session == null) return;

        IEffect effect = event.effect();
        if(effect instanceof ITimedEffect){
            session.addTimedEffect((ITimedEffect) effect);
        }
        effect.apply(session);
    }

    /**
     * Logic to be executed when the snake eats.
     * Gets the current state of the session from the game context.
     * @param event The event that contains the relevant data (although in this version it is not used).
     */
    private void onFoodEaten(FoodEatenEvent event) {
        IGameSession session = game.getSession();
        if (session == null) return;

        IFoodAPI eatenFood = event.food();
        // Apply the effect of the food that has just been eaten.
        eatenFood.applyEffect(session);
        // Removes consumed food from the game world list.
        session.removeFood(eatenFood);
        // It only generates a new batch if there are no positive food left.
        boolean positiveFoodRemains = session.getFoods().stream()
                .anyMatch(IFoodAPI::countsForRespawn);
        if(!positiveFoodRemains){
            this.spawnFood();
        }
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
     * The logic to execute when the snake dies.
     */
    private void onSnakeDied() {
        game.changeState(GameOverState.getInstance());
    }

    /**
     * Generates a new batch of random meals.
     */
    private void spawnFood(){
        IGameSession session = game.getSession();

        Set<Position> allOccupiedSpots = new HashSet<>(session.getSnake().getBodySet());
        allOccupiedSpots.addAll(session.getBoard().getObstacles());

        Set<IFoodAPI> newBatch = FoodFactory.getInstance().createFoodBatch(allOccupiedSpots, session.getFoods());
        newBatch.forEach(session::addFood);
    }
}
