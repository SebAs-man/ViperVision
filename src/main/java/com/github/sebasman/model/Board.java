package com.github.sebasman.model;

import com.github.sebasman.config.GameConfig;
import com.github.sebasman.model.common.Position;
import com.github.sebasman.model.entities.GameEntity;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.*;

/**
 * Represents a game board with specified dimensions for width and height.
 * The board serves as the primary playing area in the game.
 * The dimensions of the board determine its size in the game,
 * and they must be positive integers at all times.
 * This class provides constructors to define the board with default or custom dimensions
 * and methods to query its dimensions.
 */
public class Board {
    // --- Attributes ---
    private final int cols;
    private final int rows;

    /**
     * Constructs a new Board with the specified dimensions.
     * The dimensions define the width and height of the board in terms of cells.
     * @param cols the number of columns for the board; must be a positive integer
     * @param rows the number of rows for the board; must be a positive integer
     * @throws IllegalArgumentException if either {@code cols} or {@code rows} is less than or equal to 0
     */
    public Board(int cols, int rows) {
        if(cols <= 0 || rows <= 0) {
            throw new IllegalArgumentException("Width and height must be positive integers.");
        }
        this.cols = cols;
        this.rows = rows;
    }

    // --- Getters ---

    /**
     * Retrieves the number of columns for the board.
     * @return the number of columns in the board
     */
    public int getCols() {
        return cols;
    }

    /**
     * Retrieves the number of rows for the board.
     * @return the number of rows in the board
     */
    public int getRows() {
        return rows;
    }

    // --- Overrides ---

    @Override
    public int hashCode() {
        return Objects.hash(this.cols, this.rows);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Board board = (Board) obj;
        return this.cols == board.cols && this.rows == board.rows;
    }

    @Override
    public String toString() {
        return "Board{" +
                "widthInCells=" + this.cols +
                ", heightInCells=" + this.rows +
                '}';
    }

    // --- Utility Methods ---

    public void draw(PApplet p){
        p.stroke(50);
        p.strokeWeight(1);
        for (int x = 0; x < getCols(); x++) {
            p.line(x * GameConfig.CELL_SIZE, GameConfig.UI_AREA_SCORE,
                    x * GameConfig.CELL_SIZE, p.height);
        }
        for (int y = 0; y < getRows(); y++) {
            p.line(0, (y * GameConfig.CELL_SIZE) + GameConfig.UI_AREA_SCORE,
                    p.width, (y * GameConfig.CELL_SIZE) + GameConfig.UI_AREA_SCORE);
        }
    }
}
