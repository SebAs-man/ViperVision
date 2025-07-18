package com.github.sebasman.model;

import com.github.sebasman.contracts.model.entities.IBoardAPI;
import com.github.sebasman.contracts.model.entities.IFoodAPI;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.entities.ISnakeAPI;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.model.entities.Board;
import com.github.sebasman.model.entities.Snake;
import com.github.sebasman.model.entities.foods.AppleFood;

import java.util.*;

/**
 * Contains all relevant status for a single game session.
 * Acts as the single source of truth for an ongoing game.
 */
public final class GameSession implements IGameSession {
    // --- Entities ---
    private final ISnakeAPI snake;
    private final Set<IFoodAPI> foods;
    private final Board board;
    // --- Statistics ---
    private int score;

    /**
     * Build a new Game Session
     */
    public GameSession() {
        this.snake = new Snake(new Position(ModelConfig.GRID_WIDTH/4, ModelConfig.GRID_HEIGHT/2), 3);
        this.foods = new HashSet<>();
        this.board = new Board();
        // Define default values
        this.addFood(new AppleFood(new Position(3*ModelConfig.GRID_WIDTH/4, ModelConfig.GRID_HEIGHT/2)));
        this.score = 0;

    }

    @Override
    public ISnakeAPI getSnake() {
        return this.snake;
    }

    @Override
    public Set<IFoodAPI> getFoods() {
        return this.foods;
    }

    @Override
    public IBoardAPI getBoard(){ return this.board; }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public void addFood(IFoodAPI food) {
        if(food != null) this.foods.add(food);
    }

    @Override
    public void removeFood(IFoodAPI food) {
        if(food != null) this.foods.remove(food);
    }

    @Override
    public void incrementScore(int points) { this.score += points; }
}
