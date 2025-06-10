package com.github.sebasman;

import processing.core.PApplet;

import java.util.*;


/**
 * Represents the snake. Contains its body, direction and the logic
 * to move and draw itself.
 */
public class Snake {
    private final PApplet parent;
    private final List<Position> body;
    private final Set<Position> bodySet;
    private Direction currentDirection;
    private final int boxSize;
    private boolean isGrowing;
    private final Queue<Direction> inputQueue;
    private static final int MAX_INPUT_QUEUE_SIZE = 3;


    /**
     * Snake constructor.
     * @param parent The reference to the PApplet (the Game class).
     * @param start The initial position of the head.
     */
    public Snake(final PApplet parent, final Position start) {
        Objects.requireNonNull(parent, "Parent cannot be null");
        Objects.requireNonNull(start, "Start position cannot be null");
        this.parent = parent;
        this.boxSize = ((Game) parent).getBoxSize();
        this.isGrowing = false;

        this.body = new LinkedList<>();
        this.bodySet = new HashSet<>();
        this.body.add(start);
        this.bodySet.add(start);

        this.currentDirection = Direction.RIGHT;
        this.inputQueue = new LinkedList<>();
    }

    /**
     * Buffers a new direction for the snake.
     * @param newDirection The new direction to buffer.
     */
    public void bufferDirection(Direction newDirection){
        Objects.requireNonNull(newDirection, "New direction cannot be null");
        if(inputQueue.size() < MAX_INPUT_QUEUE_SIZE){
            inputQueue.add(newDirection);
        }
    }

    /**
     * Updates the snake's position by moving its head in the current direction
     */
    public void update() {
        // If there are buffered inputs, process the next one
        if(!inputQueue.isEmpty()){
            Direction nextDirection = inputQueue.poll();
            if(nextDirection != this.currentDirection.opposite()){
                this.currentDirection = nextDirection;
            }
        }
        // Calculate the new head position based on the current direction
        Position currentHead = this.getHead();
        Position newHead = currentHead.add(new Position(currentDirection.getDx(), currentDirection.getDy()));
        // Add the new head to the front of the body
        this.body.addFirst(newHead);
        this.bodySet.add(newHead);
        // If the snake should grow, do not remove the tail.
        // If not, remove it to simulate movement.
        if(this.isGrowing){
            this.isGrowing = false; // Reset the growth flag after growing
        } else {
            Position tail = this.body.removeLast(); // Remove the tail segment to simulate movement
            this.bodySet.remove(tail); // Remove the tail from the set
        }
    }

    /**
     * Draws the snake on the screen.
     */
    public void draw(){
        this.parent.fill(0, 255, 0);
        this.parent.noStroke();
        for(Position pos : this.body) {
            // Calculate the position in pixels based on the box size
            int x = pos.x() * boxSize;
            int y = pos.y() * boxSize;
            // Draw a rectangle for each segment of the snake
            this.parent.rect(x, y, boxSize, boxSize);
        }
    }

    /**
     * Grows the snake by one segment.
     */
    public void grow(){
        this.isGrowing = true;
    }

    /**
     * Checks if the snake collides with itself.
     * @return True if the snake collides with itself, false otherwise.
     */
    public boolean checkCollisionWithWall() {
        Position head = this.getHead();
        return head.x() < 0 || head.x() >= ((Game) this.parent).getGridWidth() ||
               head.y() < 0 || head.y() >= ((Game) this.parent).getGridHeight();
    }

    /**
     * Checks if the snake collides with itself.
     * @return True if the snake collides with itself, false otherwise.
     */
    public boolean checkCollisionWithSelf() {
        Position head = this.getHead();
        // Check if the head collides with any other segment of the body
        for (int i = 1; i < this.body.size(); i++) {
            if (head.equals(this.body.get(i))) {
                return true; // Collision detected
            }
        }
        return false; // No collision
    }

    // --- Getters ---

    /**
     * Returns the set of positions occupied by the snake's body.
     * @return A set of positions representing the snake's body.
     */
    public Set<Position> getBodySet() {
        return bodySet;
    }

    /**
     * Returns the current direction of the snake.
     * @return The current direction of the snake.
     */
    public Position getHead() {
        return this.body.getFirst();
    }
}
