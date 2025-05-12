package com.github.sebasman.model;

import com.github.sebasman.exceptions.EntityException;
import com.github.sebasman.model.common.Direction;
import com.github.sebasman.model.common.EntityType;
import com.github.sebasman.model.common.Position;

import java.util.*;
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
    // Attributes
    private final Deque<Position> body = new LinkedBlockingDeque<>();
    private final Set<Position> bodySet = new HashSet<>();
    private Direction direction;
    private boolean growing = false;

    /**
     * Constructs a Snake entity with an initial head position and direction.
     * The snake is initialized with a single segment at the specified position,
     * and it moves in the provided initial direction. The type of the entity is
     * set to {@code EntityType.SNAKE}.
     * @param initialHeadPosition the starting position of the snake's head; must not be null
     * @param initialDirection the initial direction of the snake's movement; must not be null
     * @throws NullPointerException if {@code initialHeadPosition} or {@code initialDirection} is null
     */
    public Snake(Position initialHeadPosition, Direction initialDirection) {
        super(EntityType.SNAKE);
        addBodySegment(initialHeadPosition);
        this.direction = Objects.requireNonNull(initialDirection, "Initial direction cannot be null");
    }

    /**
     * Constructs a Snake entity with an initial head position, size, and direction.
     * The snake is initialized with a specified number of segments starting
     * from the given head position and extending in the opposite direction
     * of the provided initial direction. The first segment of the snake will
     * be at the head position, and the rest will follow in a line determined
     * by the direction. The type of the entity is set to {@code EntityType.SNAKE}.
     * @param initialHeadPosition the starting position of the snake's head; must not be null
     * @param size the initial number of segments the snake should have; must be a positive integer
     * @param initialDirection the initial direction of the snake's movement; must not be null
     * @throws NullPointerException if {@code initialHeadPosition} or {@code initialDirection} is null
     * @throws IllegalArgumentException if {@code size} is not a positive integer
     */
    public Snake(Position initialHeadPosition, int size, Direction initialDirection) {
        super(EntityType.SNAKE);
        Objects.requireNonNull(initialHeadPosition, "Initial head position cannot be null");
        if(size <= 0) {
            throw new IllegalArgumentException("Size must be a positive integer.");
        } else if(size == 1) {
            addBodySegment(initialHeadPosition);
            return;
        } else {
            for (int i = 0; i < size; i++) {
                Position newSegment = new Position(
                        initialHeadPosition.x() - (i * initialDirection.getDx()),
                        initialHeadPosition.y() - (i * initialDirection.getDy()));
                addBodySegment(newSegment);
            }
        }
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
     * Retrieves the current set of positions occupied by the snake's body.
     * The set represents unique positions where each segment of the snake exists.
     * This can be useful for collision detection, ensuring no two segments
     * of the snake occupy the same space.
     * @return a set of {@code Position} objects representing the snake's body segments
     */
    public Set<Position> getBodySet() { return bodySet; }

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

    /**
     * Marks the snake as "growing," indicating that it will increase in size
     * during the next movement operation. When this method is invoked, the
     * growth state of the snake is set to true, ensuring that additional
     * segments are appended to the snake's body during the next update.
     */
    public void grow() { this.growing = true; }

    // --- Methods ---

    /**
     * Adds a new body segment to the snake's body at the specified position.
     * This method verifies that the specified position is valid and not already
     * occupied by any existing body segment. If the position is occupied, an
     * {@code EntityException} is thrown.
     * @param position the {@code Position} where the new body segment should be added;
     *                 must not be null and must not yet be occupied by another segment
     * @throws NullPointerException if the provided {@code position} is null
     * @throws EntityException if the specified {@code position} is already occupied by a body segment
     */
    private void addBodySegment(Position position) {
        Objects.requireNonNull(position, "Position cannot be null");
        body.addLast(position);
        bodySet.add(position);
    }

    /**
     * Removes the last segment of the snake's body. This method updates the body deque
     * by removing the last element and ensures that the position of the removed segment
     * is also removed from the set of occupied positions.
     * The removal operation maintains the consistency of the body and bodySet fields,
     * ensuring no stale or incorrect references remain.
     */
    private void removeBodySegment() {
        body.removeFirst();
        bodySet.remove(body.peekLast());
    }

    /**
     * Moves the snake in the current direction by updating the positions of its body segments.
     * The new head position is calculated based on the current head position and the direction of movement.
     * This new position is added to the front of the body deque, representing the new head.
     * If the snake is marked as growing, its body size increases by retaining the tail segment during this move.
     * Otherwise, the last segment of the body is removed, maintaining the snake's current size.
     * If the snake is inactive, the method exits without modifying the state.
     */
    public void move() {
        if(!isActive()) return;

        Position currentHead = getHead();
        Position newHead = new Position(
                currentHead.x() + direction.getDx(),
                currentHead.y() + direction.getDy());
        addBodySegment(newHead);
        if(growing){
            this.growing = false;
        } else{
            removeBodySegment();
        }
    }

    /**
     * Checks if the snake has collided with itself by determining if any body
     * segments overlap. A self-collision occurs when the size of the snake's body
     * is greater than 1 and there are duplicate positions in the body.
     * @return {@code true} if the snake's body contains overlapping segments indicating
     *         a self-collision, {@code false} otherwise
     */
    public boolean checkSelfCollision() {
        if(this.body.size() <= 1) return false;
        return this.bodySet.size() < this.body.size();
    }

    // --- Override Methods ---

    @Override
    public List<Position> getPositions() {
        return new LinkedList<>(body);
    }

    @Override
    public void setActive(boolean active) {
        if(super.active && body.isEmpty()){
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
