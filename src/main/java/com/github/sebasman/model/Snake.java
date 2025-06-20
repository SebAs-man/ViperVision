package com.github.sebasman.model;

import com.github.sebasman.contracts.model.ISnakeAPI;
import com.github.sebasman.contracts.vo.Direction;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;

import java.util.*;


/**
 * The SnakeImpl class represents the snake in the game.
 */
public class Snake implements ISnakeAPI {
    private final List<Position> body;
    private final List<Position> previousBody; // Stores the position of the body in the previous logical frame.
    private final Set<Position> bodySet;
    private Direction currentDirection;
    private boolean isGrowing;
    private final Queue<Direction> inputQueue;


    /**
     * Snake constructor.
     * @param start The initial position of the head.
     */
    public Snake(final Position start, int initialSize) {
        this.isGrowing = false;

        this.body = new LinkedList<>();
        this.previousBody = new LinkedList<>();
        this.bodySet = new HashSet<>();
        this.createBody(start, initialSize);

        this.currentDirection = Direction.RIGHT;
        this.inputQueue = new LinkedList<>();
    }

    /**
     * Creates the initial body of the snake starting from a given position.
     * @param start The starting position of the snake's head.
     * @param initialSize The initial size of the snake's body.
     */
    private void createBody(final Position start, int initialSize) {
        Objects.requireNonNull(start, "Start position cannot be null");
        int size = Math.max(initialSize, 3); // Ensure a minimum size of 3
        for(int i = 0; i < size; i++) {
            // Create the body segments to the left of the starting position
            Position segment = new Position(start.x() - i, start.y());
            this.body.add(segment);
            this.previousBody.add(segment);
            this.bodySet.add(segment);
        }
    }

    @Override
    public void update() {
        // If there are buffered inputs, process the next one
        if(!inputQueue.isEmpty()){
            Direction nextDirection = inputQueue.poll();
            if(nextDirection != this.currentDirection.opposite()){
                this.currentDirection = nextDirection;
            }
        }
        // Store the current body positions before updating
        this.previousBody.clear();
        this.previousBody.addAll(this.body);
        // Calculate the new head position based on the current direction
        Position currentHead = this.getHead();
        Position newHead = currentHead.add(new Position(currentDirection.getDx(), currentDirection.getDy()));
        // If the snake should grow, do not remove the tail.
        // If not, remove it to simulate movement.
        if(this.isGrowing){
            this.isGrowing = false; // Reset the growth flag after growing
        } else {
            Position tail = this.body.removeLast(); // Remove the tail segment to simulate movement
            this.bodySet.remove(tail); // Remove the tail from the set
        }
        // Add the new head to the front of the body
        this.body.addFirst(newHead);
        this.bodySet.add(newHead);
    }

    @Override
    public void bufferDirection(Direction newDirection){
        Objects.requireNonNull(newDirection, "New direction cannot be null");
        if(inputQueue.size() < ModelConfig.INPUT_BUFFER_LIMIT){
            inputQueue.add(newDirection);
        }
    }

    @Override
    public void grow(){
        this.isGrowing = true;
    }

    @Override
    public boolean checkCollisionWithWall() {
        Position head = this.getHead();
        return head.x() < 0 || head.x() >= ModelConfig.GRID_WIDTH ||
               head.y() < 0 || head.y() >= ModelConfig.GRID_HEIGHT;
    }

    @Override
    public boolean checkCollisionWithSelf() {
        return this.body.size() != this.bodySet.size();
    }

    @Override
    public Position getHead() {
        return this.body.getFirst();
    }

    @Override
    public List<Position> getBody() {
        return body;
    }

    @Override
    public List<Position> getPreviousBody() {
        return previousBody;
    }

    @Override
    public Set<Position> getBodySet() {
        return bodySet;
    }

    @Override
    public Direction getDirection() {
        return currentDirection;
    }
}
