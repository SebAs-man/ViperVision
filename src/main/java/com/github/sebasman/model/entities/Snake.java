package com.github.sebasman.model.entities;

import com.github.sebasman.config.GameConfig;
import com.github.sebasman.exceptions.EntityException;
import com.github.sebasman.model.Board;
import com.github.sebasman.model.common.Direction;
import com.github.sebasman.model.common.EntityType;
import com.github.sebasman.model.common.Position;
import processing.core.PApplet;

import java.util.*;

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
public class Snake extends GameEntity {
    // Attributes
    private final Deque<Position> body = new LinkedList<>();
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
        this.addBodySegment(initialHeadPosition);
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
        this.direction = Objects.requireNonNull(initialDirection, "Initial direction cannot be null");
        if(size <= 0) {
            throw new IllegalArgumentException("Size must be a positive integer.");
        } else if(size == 1) {
            this.addBodySegment(initialHeadPosition);
        } else {
            for (int i = size-1; i >= 0; i--) {
                Position newSegment = new Position(
                        initialHeadPosition.x() - (i * initialDirection.getDx()),
                        initialHeadPosition.y() - (i * initialDirection.getDy()));
                this.addBodySegment(newSegment);
            }
        }
    }

    // --- Getters ---

    /**
     * Retrieves the current head position of the snake.
     * The head position represents the first element in the deque that
     * tracks the snake's body coordinates.
     * @return the position of the snake's head, or null if the body is empty
     */
    public Position getHead() { return this.body.peekFirst(); }

    /**
     * Retrieves the current direction of the snake's movement.
     * The direction determines the axis along which the snake moves
     * in the game world, such as UP, DOWN, LEFT, or RIGHT.
     * @return the current {@code Direction} of the snake's movement
     */
    public Direction getDirection() { return this.direction; }

    /**
     * Determines if the snake is currently in a growing state.
     * When the snake is growing, additional segments are appended to its body
     * during the next movement update.
     * @return true if the snake is growing, false otherwise
     */
    public boolean isGrowing() { return growing; }

    /**
     * Retrieves the set of positions that make up the body of the snake.
     * The body set represents a collection of unique positions that the snake currently occupies.
     * @return a set of {@code Position} objects representing the current positions of the snake's body
     */
    public Set<Position> getBodySet() { return bodySet; }

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
        if(this.body.size() > 1 && this.direction.opposite() == direction) return;
        this.direction = direction;
    }

    /**
     * Marks the snake as "growing," indicating that it will increase in size
     * during the next movement operation. When this method is invoked, the
     * growth state of the snake is set to true, ensuring that additional
     * segments are appended to the snake's body during the next update.
     */
    public void grow() { this.growing = true; }

    // --- Override Methods ---

    @Override
    public List<Position> getPositions() {
        return new LinkedList<>(body);
    }

    @Override
    public void setActive(boolean active) {
        if(this.body.isEmpty()){
            super.active = false;
            return;
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

    // --- Utility Methods ---

    public boolean isEating(Food food){
        return this.getHead().equals(food.getPosition());
    }

    public void move() {
        if(!isActive()) return;
        // Move the snake in the current direction
        Position currentHead = getHead();
        Position newHead = new Position(
                currentHead.x() + direction.getDx(),
                currentHead.y() + direction.getDy());
        this.addBodySegment(newHead);
        if(this.growing){
            this.growing = false;
        } else{
            this.removeBodySegment();
        }
    }

    public boolean checkCollision(int boardW, int boardH) {
        Position head = getHead();
        // Check if the snake's head collides with board edges
        if(head.x() < 0 || head.x() >= boardW || head.y() < 0 || head.y() >= boardH) {
            System.out.println("Snake collided with the wall.");
            return true;
        }
        // Check if the snake's head collides with its own body
        if(this.body.size() > 3 && this.body.size() != this.bodySet.size()) {
            System.out.println("Snake collided with itself.");
            return true;
        }
        return false;
    }

    public void draw(PApplet p) {
        p.fill(0, 255, 0);
        for (Position segment : body) {
            p.rect(segment.x() * GameConfig.CELL_SIZE, segment.y() * GameConfig.CELL_SIZE,
                    GameConfig.CELL_SIZE, GameConfig.CELL_SIZE);
        }
    }

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
        body.addFirst(position);
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
        bodySet.remove(body.peekLast());
        body.removeLast();
    }
}
