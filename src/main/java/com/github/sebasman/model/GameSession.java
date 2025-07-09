package com.github.sebasman.model;

import com.github.sebasman.contracts.model.IBoardAPI;
import com.github.sebasman.contracts.model.IFoodAPI;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.ISnakeAPI;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;

/**
 * Contains all relevant status for a single game session.
 * Acts as the single source of truth for an ongoing game.
 */
public final class GameSession implements IGameSession {
    private final ISnakeAPI snake;
    private final IFoodAPI food;
    private final Board board;
    private int score;

    /**
     * Build a new Game Session
     */
    public GameSession() {
        this.snake = new Snake(new Position(ModelConfig.GRID_WIDTH/4, ModelConfig.GRID_HEIGHT/2), 3);
        this.food = new Food(1, new Position(3*ModelConfig.GRID_WIDTH/4, ModelConfig.GRID_HEIGHT/2));
        this.board = new Board();
        this.score = 0;
    }

    @Override
    public ISnakeAPI getSnake() {
        return this.snake;
    }

    @Override
    public IFoodAPI getFood() {
        return this.food;
    }

    @Override
    public IBoardAPI getBoard(){ return this.board; }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public void incrementScore(int points) { this.score += points; }
}
