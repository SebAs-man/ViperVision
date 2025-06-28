package com.github.sebasman.presenter.strategies;

import com.github.sebasman.contracts.configuration.CheckBoxConfigParameter;
import com.github.sebasman.contracts.configuration.IConfigParameter;
import com.github.sebasman.contracts.configuration.SliderConfigParameter;
import com.github.sebasman.contracts.model.ISnakeAPI;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.view.IUiComponent;
import com.github.sebasman.contracts.presenter.IUiProvider;
import com.github.sebasman.contracts.vo.Direction;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.contracts.presenter.IControlStrategy;
import com.github.sebasman.view.components.Button;
import com.github.sebasman.view.components.CheckBox;

import java.util.LinkedList;
import java.util.List;

/**
 * A strategy for controlling the snake to follow the food.
 */
public final class FollowFoodStrategy implements IControlStrategy, IUiProvider {
    /**
     * Public builder. Each AI game will have its own strategy instance.
     */
    public FollowFoodStrategy() {}

    @Override
    public void update(IGameContext game, ISnakeAPI snake) {
        Position head = snake.getHead();
        if(game.getSession() == null) throw new RuntimeException("Game session is null");
        Position food = game.getSession().getFood().getPosition();

        int dx = food.x() - head.x();
        int dy = food.y() - head.y();

        if (Math.abs(dx) > Math.abs(dy)) {
            // If the horizontal distance is greater, prioritize horizontal movement
            if (dx > 0) {
                snake.bufferDirection(Direction.RIGHT);
            } else {
                snake.bufferDirection(Direction.LEFT);
            }
        } else {
            // If the vertical distance is greater, prioritize vertical movement
            if (dy > 0) {
                snake.bufferDirection(Direction.DOWN);
            } else {
                snake.bufferDirection(Direction.UP);
            }
        }
    }

    @Override
    public void keyPressed(IGameContext game, ISnakeAPI snake, int keyCode) {
        // The AI doesn't respond to the keyboard, so this method is empty.
    }

    @Override
    public List<IConfigParameter> getConfigurationParameters() {
        return List.of(
            new CheckBoxConfigParameter("AI_SHOW_PATH", "demo1", false),
            new CheckBoxConfigParameter("AI_SHOW", "demo2", true),
            new SliderConfigParameter("SPEED", "speed_snake", 1, 10, 2),
                new SliderConfigParameter("SPEED", "food_snake", 5, 10, 8)
        );
    }

    @Override
    public boolean isGameStartAction(int keyCode) {
        // The AI does not start the game with a key, but with a UI button.
        return false;
    }
}
