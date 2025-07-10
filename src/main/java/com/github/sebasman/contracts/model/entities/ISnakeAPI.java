package com.github.sebasman.contracts.model.entities;

import com.github.sebasman.contracts.model.IUpdatable;
import com.github.sebasman.contracts.vo.Direction;
import com.github.sebasman.contracts.vo.Position;

import java.util.List;
import java.util.Set;

/**
 * The SnakeAPI interface defines the contract for snake-related operations in the game.
 * It extends the Drawable and Updatable interfaces to allow the snake to be drawn on the game board
 * and updated during each game tick.
 */
public interface ISnakeAPI extends IUpdatable {
    /**
     * Sets the direction of the snake's movement.
     * @param direction the direction to set for the snake
     */
    void bufferDirection(Direction direction);

    /**
     * Grows the snake by one segment.
     * @param amount the number of segments to grow from the snake
     */
    void grow(int amount);

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
     * Return the current position of the tail's snake
     * @return The current position of the tail's snake
     */
    Position getTail();

    /**
     * Return the current position occupied by the snake's body
     * @return a list of positions representing the current snake's body
     */
    List<Position> getBody();

    /**
     * Return the previous positions occupied by the snake's body
     * @return a list of position representing the previous snake's body
     */
    List<Position> getPreviousBody();

    /**
     * Returns the set of positions occupied by the snake's body.
     * @return a set of positions representing the snake's body
     */
    Set<Position> getBodySet();

    /**
     * Returns the current direction in which the serpent is moving
     * @return The current direction
     */
    Direction getDirection();
}
