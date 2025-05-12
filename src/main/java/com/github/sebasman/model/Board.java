package com.github.sebasman.model;

import com.github.sebasman.config.GameConfig;
import com.github.sebasman.model.common.Position;

import java.util.Objects;

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
    private final int width;
    private final int height;

    /**
     * Constructs a new Board with default dimensions as specified in the
     * GameConfig class. The width and height of the board are initialized
     * to the values of GameConfig.BOARD_WIDTH_CELLS and GameConfig.BOARD_HEIGHT_CELLS,
     * respectively. These values define the size of the board in terms of cells.
     */
    public Board() {
        this.width = GameConfig.BOARD_WIDTH_CELLS;
        this.height = GameConfig.BOARD_HEIGHT_CELLS;
    }

    /**
     * Constructs a new Board with the specified width and height.
     * The width and height define the dimensions of the board.
     * Both values must be positive integers; otherwise, an IllegalArgumentException
     * will be thrown.
     * @param width the width of the board in units; must be a positive integer
     * @param height the height of the board in units; must be a positive integer
     * @throws IllegalArgumentException if width or height is not a positive integer
     */
    public Board(int width, int height) {
        if(width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive integers.");
        }
        this.width = width;
        this.height = height;
    }

    // --- Getters ---

    /**
     * Retrieves the width of the board.
     * @return the width of the board as an integer
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Retrieves the height of the board.
     * @return the height of the board as an integer
     */
    public int getHeight() {
        return this.height;
    }

    // --- Methods ---

    /**
     * Determines whether the specified {@code position} is within the bounds of the board.
     * A position is considered within bounds if its x-coordinate is greater than or equal to 0
     * and less than the board's width, and its y-coordinate is greater than or equal to 0
     * and less than the board's height.
     * @param position the position to check; must not be null
     * @return {@code true} if the position is within the bounds of the board; {@code false} otherwise
     * @throws NullPointerException if the {@code position} is null
     */
    public boolean isWithinBounds(Position position) {
        Objects.requireNonNull(position, "Position cannot be null");
        int x = position.x();
        int y = position.y();
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }

    // --- Overrides ---

    @Override
    public int hashCode() {
        return Objects.hash(this.width, this.height);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Board board = (Board) obj;
        return this.width == board.width && this.height == board.height;
    }

    @Override
    public String toString() {
        return "Board{" +
                "widthInCells=" + this.width +
                ", heightInCells=" + this.height +
                '}';
    }
}
