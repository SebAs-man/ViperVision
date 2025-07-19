package com.github.sebasman.presenter.states;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.presenter.engine.GameLoopTimer;
import com.github.sebasman.presenter.listeners.GameLogicCoordinator;
import com.github.sebasman.contracts.events.types.FoodEatenEvent;
import com.github.sebasman.contracts.presenter.IControlStrategy;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.contracts.model.entities.IFoodAPI;
import com.github.sebasman.contracts.model.entities.ISnakeAPI;
import com.github.sebasman.view.render.GameUiStatic;
import com.github.sebasman.view.render.GameWorldRenderer;
import com.github.sebasman.view.render.HUDRenderer;
import processing.core.PApplet;

import java.util.Objects;

/**
 * The playing state of the game, where the player controls the snake and interacts with food.
 */
public final class PlayingState implements IState {
    // The control strategy for handling user input.
    private final IControlStrategy controlStrategy;
    // Listeners
    private GameLoopTimer timer;
    private GameLogicCoordinator logicCoordinator;

    /**
     * Constructor for the PlayingState.
     * @param controlStrategy the control strategy to use for handling user input
     */
    public PlayingState(IControlStrategy controlStrategy) {
        this.controlStrategy = Objects.requireNonNull(controlStrategy, "Control strategy cannot be null");
    }

    @Override
    public void onEnter(IGameContext game) {
        System.out.println("¡Starting Game!");
        this.logicCoordinator = new GameLogicCoordinator(game);
        this.logicCoordinator.subscribeToEvents();
        this.timer = new GameLoopTimer(controlStrategy.getDesiredSpeed());
        this.timer.subscribeToEvents();
    }

    @Override
    public void onExit(IGameContext game) {
        this.logicCoordinator.unsubscribeFromEvents();
        this.timer.unsubscribeToEvents();
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
            this.checkCollisions(game.getSession());
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
     * @param session the current game session instance
     */
    private void checkCollisions(IGameSession session) {
        if(session == null) return;

        ISnakeAPI snake = session.getSnake();
        // Checks for collision with walls, body and obstacles...
        snake.handleCollision(session);
        // Check for collision with ANY of the meals on the board.
        IFoodAPI eatenFood = null;
        for(IFoodAPI food : session.getFoods()) {
            if(snake.getHead().equals(food.getPosition())){
                eatenFood = food;
                break;
            }
        }
        if(eatenFood != null) {
            EventManager.getInstance().notify(new FoodEatenEvent(eatenFood));
        }
    }
}
