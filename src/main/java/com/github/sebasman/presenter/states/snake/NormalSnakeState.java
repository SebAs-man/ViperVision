package com.github.sebasman.presenter.states.snake;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.SnakeDiedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.effects.IEffect;
import com.github.sebasman.contracts.model.entities.ISnakeAPI;
import com.github.sebasman.contracts.presenter.ISnakeState;
import com.github.sebasman.contracts.view.ISnakeRenderStyle;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.view.config.ColorPalette;

import java.util.Iterator;

/**
 * Defines the state of the snake in its normal state,
 * allows for logical behavior in the game.
 */
public final class NormalSnakeState implements ISnakeState {
    private static final NormalSnakeState INSTANCE = new NormalSnakeState();

    /**
     * Private builder to avoid creation.
     */
    private NormalSnakeState(){}

    /**
     * Returns the single instance of the class
     * @return Its own unique instance
     */
    public static NormalSnakeState getInstance() {
        return INSTANCE;
    }

    @Override
    public void handleCollision(ISnakeAPI snake, IGameSession session) {
        if (this.checkCollisionWithWall(snake) ||
                this.checkCollisionWithSelf(snake) ||
                session.getBoard().isObstacle(snake.getHead())) {
            EventManager.getInstance().notify(new SnakeDiedEvent());
        }
    }

    @Override
    public ISnakeRenderStyle getRenderStyle() {
        return pApplet -> ColorPalette.SNAKE_BODY;
    }

    @Override
    public IEffect processEffect(IEffect effect) {
        // In the normal state, all effects are allowed to pass through unmodified.
        return effect;
    }

    /**
     * Checks if the snake collides with a wall.
     * @param snake The snake to validate
     * @return true if there is a collision, false otherwise
     */
    private boolean checkCollisionWithWall(ISnakeAPI snake) {
        Position head = snake.getHead();
        return head.x() < 0 || head.x() >= ModelConfig.GRID_WIDTH ||
                head.y() < 0 || head.y() >= ModelConfig.GRID_HEIGHT;
    }

    /**
     * Checks if the snake collides with itself.
     * @param snake The snake to validate
     * @return true if there is a collision, false otherwise
     */
    private boolean checkCollisionWithSelf(ISnakeAPI snake) {
        Position head = snake.getHead();
        Iterator<Position> iterator = snake.getBody().iterator();
        // The iterator is advanced once to skip the head.
        if(iterator.hasNext()) {
            iterator.next();
        }
        // Now it is checked if any of the remaining body parts are equal to the head.
        while(iterator.hasNext()) {
            if(head.equals(iterator.next())) {
                return true;
            }
        }
        return false;
    }
}
