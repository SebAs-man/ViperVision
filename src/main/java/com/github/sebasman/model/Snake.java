package com.github.sebasman.model;

import com.github.sebasman.exceptions.EntityException;
import com.github.sebasman.model.common.Direction;
import com.github.sebasman.model.common.EntityType;
import com.github.sebasman.model.common.Position;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Represents a snake entity in a game. The snake is characterized by its body,
 * direction, and active state within the game. The body consists of a deque of
 * {@code Position} objects, with the head being the first element in the deque.
 * The snake's direction specifies its current movement direction within the game world.
 * This class provides functionality to interact with and manipulate the snake's
 * state, including retrieving the body, head position, and direction, as well as
 * setting a new direction for movement. The snake's active status can also be managed
 * following the rules established in the parent {@code GameEntity} class.
 */
public class Snake extends GameEntity{
    // Fields
    private final Deque<Position> body;
    private Direction direction;

    /**
     * Constructs a new Snake entity with the specified initial head position and initial direction.
     * The snake's head is initialized at the given position, and its movement direction is set based
     * on the provided direction. The snake's body is managed as a deque, with the head being the
     * first element.
     * @param initialHeadPosition the initial position of the snake's head; must not be null
     * @param initialDirection the initial direction of the snake's movement; must not be null
     * @throws NullPointerException if the initial head position or direction is null
     */
    public Snake(Position initialHeadPosition, Direction initialDirection) {
        super(EntityType.SNAKE);
        Objects.requireNonNull(initialHeadPosition, "Initial head position cannot be null");
        this.body = new LinkedBlockingDeque<>();
        body.addFirst(initialHeadPosition);
        this.direction = Objects.requireNonNull(initialDirection, "Initial direction cannot be null");
    }

    // --- Getters ---

    /**
     * Retrieves a copy of the snake's body as a deque of {@code Position} objects.
     * The deque represents the positions occupied by the snake, starting with the head
     * at the first element and progressing to the tail. The returned deque is a defensive
     * copy, ensuring the internal state of the snake is not directly modifiable.
     * @return a deque containing the {@code Position} objects representing the snake's body
     */
    public Deque<Position> getBody() { return new LinkedBlockingDeque<>(body); }

    /**
     * Retrieves the current head position of the snake.
     * The head position represents the first element in the deque that
     * tracks the snake's body coordinates.
     * @return the position of the snake's head, or null if the body is empty
     */
    public Position getHead() { return body.peekFirst(); }

    /**
     * Retrieves the current direction of the snake's movement.
     * The direction determines the axis along which the snake moves
     * in the game world, such as UP, DOWN, LEFT, or RIGHT.
     * @return the current {@code Direction} of the snake's movement
     */
    public Direction getDirection() { return direction; }

    // --- Setters ---

    /**
     * Updates the direction of the snake's movement.
     * The snake's direction is only updated if the new direction is not
     * opposite to its current direction, provided the snake has more than one segment.
     * @param direction the new {@code Direction} to set for the snake; must not be null
     *                  and must not be the opposite of the current direction
     *                  when the snake's body has more than one segment.
     * @throws NullPointerException if the provided {@code direction} is null
     */
    public void setDirection(Direction direction) {
        Objects.requireNonNull(direction, "Direction cannot be null");
        if(body.size() > 1 && this.direction.opposite() == direction){
            return;
        }
        this.direction = direction;
    }

    // --- Override Methods ---

    @Override
    public List<Position> getPositions() {
        return new LinkedList<>(body);
    }

    @Override
    public void setActive(boolean active) {
        if(active && body.isEmpty()){
            throw new EntityException("Snake cannot be activated with an empty body (internal state error).");
        }
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Snake snake = (Snake) o;
        // Una comparación completa del cuerpo puede ser costosa.
        // Depende de la semántica de igualdad que necesites.
        return direction == snake.direction && Objects.equals(body, snake.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), body, direction);
    }

    @Override
    public String toString() {
        return "Snake{" +
                "type=" + getType() +
                ", head=" + (body.isEmpty() ? "null" : getHead()) +
                ", direction=" + direction +
                ", length=" + body.size() +
                ", active=" + isActive() +
                '}';
    }
}
