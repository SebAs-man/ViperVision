package com.github.sebasman.core.interfaces.gamemodel;

import com.github.sebasman.core.vo.Direction;
import com.github.sebasman.core.vo.Position;

import java.util.Set;

/**
 * The SnakeAPI interface defines the contract for snake-related operations in the game.
 * It extends the Drawable and Updatable interfaces to allow the snake to be drawn on the game board
 * and updated during each game tick.
 */
public interface SnakeAPI extends Drawable, Updatable {
    /**
     * Sets the direction of the snake's movement.
     * @param direction the direction to set for the snake
     */
    void bufferDirection(Direction direction);

    /**
     * Grows the snake by one segment.
     */
    void grow();

    /**
     * Checks if the snake collides with a wall.
     * @return true if there is a collision, false otherwise
     */
    boolean checkCollisionWithWall();

    /**
     * Checks if the snake collides with itself.
     * @return true if there is a collision, false otherwise
     */
    boolean checkCollisionWithSelf();

    /**
     * Returns the current direction of the snake.
     * @return the current direction of the snake
     */
    Position getHead();

    /**
     * Returns the set of positions occupied by the snake's body.
     * @return a set of positions representing the snake's body
     */
    Set<Position> getBodySet();
}
