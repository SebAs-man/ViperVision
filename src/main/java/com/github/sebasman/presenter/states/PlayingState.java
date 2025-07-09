package com.github.sebasman.presenter.states;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.presenter.engine.GameLoopTimer;
import com.github.sebasman.presenter.engine.GameLogicCoordinator;
import com.github.sebasman.contracts.events.types.FoodEatenEvent;
import com.github.sebasman.contracts.events.types.SnakeDiedEvent;
import com.github.sebasman.contracts.presenter.IControlStrategy;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.contracts.model.IFoodAPI;
import com.github.sebasman.contracts.model.ISnakeAPI;
import com.github.sebasman.view.render.GameUiStatic;
import com.github.sebasman.view.render.GameWorldRenderer;
import com.github.sebasman.view.render.HUDRenderer;
import processing.core.PApplet;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * The playing state of the game, where the player controls the snake and interacts with food.
 */
public final class PlayingState implements IState {
    // The control strategy for handling user input.
    private final IControlStrategy controlStrategy;
    private GameLoopTimer timer;
    // Game messages coordinator
    private GameLogicCoordinator logicCoordinator;
    // References to listeners
    private final Consumer<FoodEatenEvent> onFoodEatenListener;
    private final Consumer<SnakeDiedEvent> onSnakeDiedListener;

    /**
     * Constructor for the PlayingState.
     * @param controlStrategy the control strategy to use for handling user input
     */
    public PlayingState(IControlStrategy controlStrategy) {
        Objects.requireNonNull(controlStrategy, "Control strategy cannot be null");
        this.controlStrategy = controlStrategy;
        this.onFoodEatenListener = event -> logicCoordinator.onFoodEaten(event);
        this.onSnakeDiedListener = _ -> logicCoordinator.onSnakeDied();
    }

    @Override
    public void onEnter(IGameContext game) {
        System.out.println("¡Starting Game!");
        this.logicCoordinator = new GameLogicCoordinator(game);
        this.timer = new GameLoopTimer((int) controlStrategy.getDesiredSpeed());
        // Listeners are subscribed to the global EventManager.
        EventManager eventManager = EventManager.getInstance();
        eventManager.subscribe(FoodEatenEvent.class, onFoodEatenListener);
        eventManager.subscribe(SnakeDiedEvent.class, onSnakeDiedListener);
    }

    @Override
    public void onExit(IGameContext game) {
        // Listeners are unsubscribed to avoid “zombie listeners” and memory leaks.
        EventManager eventManager = EventManager.getInstance();
        eventManager.unsubscribe(FoodEatenEvent.class, onFoodEatenListener);
        eventManager.unsubscribe(SnakeDiedEvent.class, onSnakeDiedListener);
    }

    @Override
    public void update(IGameContext game) {
        this.timer.update();
        // As long as there are pending ticks, we execute the game logic.
        while(timer.shouldTick()){
            IGameSession session = game.getSession();
            this.controlStrategy.update(game, session.getSnake());
            // Update the snake's position based on the current direction.
            session.getSnake().update();
            this.checkCollisions(game);
        }
    }

    @Override
    public void draw(IGameContext game) {
        PApplet renderer = game.getRenderer();
        GameUiStatic.getInstance().render(renderer);
        float interpolation = this.timer.getInterpolation();
        GameWorldRenderer.getInstance().render(game, interpolation);
        HUDRenderer.getInstance().render(renderer);
    }

    @Override
    public void keyPressed(IGameContext game, int keyCode) {
        PApplet renderer = game.getRenderer();
        if (Character.toLowerCase(renderer.key) == 'p' || renderer.key == ' ') {
            game.pushState(PausedState.getInstance());
            return;
        }
        // Delegate the key press to the control strategy.
        this.controlStrategy.keyPressed(game, game.getSession().getSnake(), keyCode);
    }

    @Override
    public void mousePressed(IGameContext game, int mouseX, int mouseY) {
        // This state does not handle mouse presses, so this method can be empty.
    }

    /**
     * Checks for collisions between the snake and the walls or itself,
     * @param game the current game instance
     */
    private void checkCollisions(IGameContext game) {
        IGameSession session = game.getSession();
        if(session == null) return;
        ISnakeAPI snake = session.getSnake();
        IFoodAPI food = session.getFood();

        if (snake.checkCollisionWithWall() || snake.checkCollisionWithSelf()) {
            EventManager.getInstance().notify(new SnakeDiedEvent());
            return;
        }

        if (snake.getHead().equals(food.getPosition())) {
            EventManager.getInstance().notify(new FoodEatenEvent(food, snake));
        }
    }
}
