package com.github.sebasman.presenter.strategies;

import com.github.sebasman.contracts.configuration.CheckBoxConfigParameter;
import com.github.sebasman.contracts.configuration.IConfigParameter;
import com.github.sebasman.contracts.configuration.SliderConfigParameter;
import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.ConfigurationChangedEvent;
import com.github.sebasman.contracts.model.ISnakeAPI;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.presenter.IUiProvider;
import com.github.sebasman.contracts.vo.Direction;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.contracts.presenter.IControlStrategy;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A strategy for controlling the snake to follow the food.
 */
public final class FollowFoodStrategy implements IControlStrategy, IUiProvider {
    // --- Configurable AI Parameters ---
    private float aiSpeed = 5.0f;
    private boolean showPath = false;
    // --- Map of Configuration Handlers ---
    private final Map<String, Consumer<Object>> configHandlers;

    /**
     * Public builder. Each AI game will have its own strategy instance.
     */
    public FollowFoodStrategy() {
        this.configHandlers = Map.of(
                "AI_SPEED", value -> this.setAiSpeed((Float) value),
                "AI_SHOW_PATH", value -> this.setShowPath((Boolean) value)
        );
        EventManager.getInstance().subscribe(ConfigurationChangedEvent.class, this::handleConfigurationChange);
    }

    /**
     * The central configuration event handler.
     * Searches for the event key in the handler map and executes the corresponding action.
     * @param event The configuration change event published by a UI component.
     */
    private void handleConfigurationChange(ConfigurationChangedEvent event){
        Consumer<Object> handler = configHandlers.get(event.key());
        // If it exists, it executes it, passing it the new value.
        if(handler != null){
            handler.accept(event.value());
        }
    }

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
            new SliderConfigParameter("AI_SPEED", "Speed Snake", 1, 10, this.aiSpeed),
            new CheckBoxConfigParameter("AI_SHOW_PATH", "Show Path", this.showPath)
        );
    }

    @Override
    public boolean isGameStartAction(int keyCode) {
        // The AI does not start the game with a key, but with a UI button.
        return false;
    }

    // --- Setters ---

    /**
     * Change the speed snake
     * @param aiSpeed the new value for the speed
     */
    public void setAiSpeed(float aiSpeed) {
        this.aiSpeed = aiSpeed;
        System.out.println("DEBUG: Velocidad de la IA actualizada a: " + this.aiSpeed);
    }

    /**
     * Change the show path of the snake
     * @param showPath true or false
     */
    public void setShowPath(boolean showPath) {
        this.showPath = showPath;
        System.out.println("DEBUG: Visualizar ruta de la IA: " + this.showPath);
    }
}
