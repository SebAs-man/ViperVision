package com.github.sebasman.core;

import com.github.sebasman.Direction;
import com.github.sebasman.Position;
import com.github.sebasman.ui.ColorPalette;
import processing.core.PApplet;

import java.util.*;


/**
 * Represents the snake. Contains its body, direction and the logic
 * to move and draw itself.
 */
public class Snake implements Drawable, Updatable {
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

    /**
     * Updates the snake's position by moving its head in the current direction
     */
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
    @Override
    public void draw(PApplet context) {
        this.draw(context, 0f);
    }

    /**
     * Draws the snake on the screen with interpolation for smooth rendering.
     * @param p The PApplet instance used for drawing.
     * @param interpolation The interpolation factor for smooth rendering, typically between 0 and 1.
     */
    public void draw(PApplet p, float interpolation) {
        p.pushStyle();
        p.noStroke();
        // Draw each body part independently
        // The loop now only draws the INSIDE body segments.
        for (int i = 1; i < body.size() - 1; i++) {
            drawSegment(p, i, interpolation);
        }
        /* Now draw the head and tail separately to handle them as special cases
        We make sure there is more than one segment so that the tail is not drawn over the head
        If the body has grown, there may not be a 'previousPos' for the new segment.
        */
        if (!body.isEmpty()) {
            drawEnd(p, 0, interpolation, true); // Draw the head
        }
        if (body.size() > 1) {
            drawEnd(p, body.size() - 1, interpolation, false); // Draw the tail
        }

        p.popStyle();
    }

    /**
     * Draws a segment of the snake's body.
     * @param p The PApplet instance used for drawing.
     * @param index The index of the segment to draw.
     * @param interpolation The interpolation factor for smooth rendering.
     */
    private void drawSegment(PApplet p, int index, float interpolation) {
        Position currentPos = body.get(index);
        Position previousPos = (index < previousBody.size()) ? previousBody.get(index) : currentPos;

        float renderX = PApplet.lerp(previousPos.x(), currentPos.x(), interpolation) * GameConfig.BOX_SIZE;
        float renderY = PApplet.lerp(previousPos.y(), currentPos.y(), interpolation) * GameConfig.BOX_SIZE;

        p.fill(ColorPalette.SNAKE_BODY);
        p.rect(renderX, renderY, GameConfig.BOX_SIZE, GameConfig.BOX_SIZE);
    }

    /**
     * Draws the head or tail of the snake with rounded corners based on its direction.
     * @param p The PApplet instance used for drawing.
     * @param index The index of the segment to draw (0 for head, last for tail).
     * @param interpolation The interpolation factor for smooth rendering.
     * @param isHead True if drawing the head, false if drawing the tail.
     */
    private void drawEnd(PApplet p, int index, float interpolation, boolean isHead) {
        Position currentPos = body.get(index);
        Position previousPos = (index < previousBody.size()) ? previousBody.get(index) : currentPos;

        float renderX = PApplet.lerp(previousPos.x(), currentPos.x(), interpolation) * GameConfig.BOX_SIZE;
        float renderY = PApplet.lerp(previousPos.y(), currentPos.y(), interpolation) * GameConfig.BOX_SIZE;

        // Determine the position of the adjacent segment to orient the corners correctly
        Position adjacentPos;
        if (isHead) {
            // The head is oriented with respect to the neck (the second segment).
            adjacentPos = (body.size() > 1) ? body.get(1) : currentPos;
        } else {
            // The tail is oriented with respect to the penultimate segment
            adjacentPos = body.get(index - 1);
        }

        float tl = 0, tr = 0, br = 0, bl = 0;
        int cornerRadius = 24;

        // Determine which corners to round based on the direction of movement
        if (currentPos.x() > adjacentPos.x()) { // Moving right
            tr = cornerRadius; br = cornerRadius;
        } else if (currentPos.x() < adjacentPos.x()) { // Moving left
            tl = cornerRadius; bl = cornerRadius;
        } else if (currentPos.y() > adjacentPos.y()) { // Moving down
            bl = cornerRadius; br = cornerRadius;
        } else if (currentPos.y() < adjacentPos.y()) { // Moving up
            tl = cornerRadius; tr = cornerRadius;
        }

        p.fill(ColorPalette.SNAKE_BODY);
        p.rect(renderX, renderY, GameConfig.BOX_SIZE, GameConfig.BOX_SIZE, tl, tr, br, bl);

        // Draw eyes only if it's the head
        if (isHead) {
            float eyeSize = GameConfig.BOX_SIZE*0.29f;
            float pupilSize = GameConfig.BOX_SIZE*0.13f;
            float eyeOffsetX1 = 0, eyeOffsetY1 = 0; // eye 1
            float eyeOffsetX2 = 0, eyeOffsetY2 = 0; // eye 2
            float pupilOffsetX = 0, pupilOffsetY = 0;

            switch (this.currentDirection) {
                case UP:
                    eyeOffsetX1 = GameConfig.BOX_SIZE * 0.25f; eyeOffsetY1 = GameConfig.BOX_SIZE * 0.35f;
                    eyeOffsetX2 = GameConfig.BOX_SIZE * 0.75f; eyeOffsetY2 = GameConfig.BOX_SIZE * 0.35f;
                    pupilOffsetY = -2; // Move pupils up
                    break;
                case DOWN:
                    eyeOffsetX1 = GameConfig.BOX_SIZE * 0.25f; eyeOffsetY1 = GameConfig.BOX_SIZE * 0.65f;
                    eyeOffsetX2 = GameConfig.BOX_SIZE * 0.75f; eyeOffsetY2 = GameConfig.BOX_SIZE * 0.65f;
                    pupilOffsetY = 2;  // Move pupils down
                    break;
                case LEFT:
                    eyeOffsetX1 = GameConfig.BOX_SIZE * 0.35f; eyeOffsetY1 = GameConfig.BOX_SIZE * 0.25f;
                    eyeOffsetX2 = GameConfig.BOX_SIZE * 0.35f; eyeOffsetY2 = GameConfig.BOX_SIZE * 0.75f;
                    pupilOffsetX = -2; // Move pupils left
                    break;
                case RIGHT:
                    eyeOffsetX1 = GameConfig.BOX_SIZE * 0.65f; eyeOffsetY1 = GameConfig.BOX_SIZE * 0.25f;
                    eyeOffsetX2 = GameConfig.BOX_SIZE * 0.65f; eyeOffsetY2 = GameConfig.BOX_SIZE * 0.75f;
                    pupilOffsetX = 2; // Move pupils right
                    break;
            }

            // Draw eyes (the white part)
            p.fill(ColorPalette.SNAKE_EYES);
            p.ellipse(renderX + eyeOffsetX1, renderY + eyeOffsetY1, eyeSize, eyeSize);
            p.ellipse(renderX + eyeOffsetX2, renderY + eyeOffsetY2, eyeSize, eyeSize);

            // Draw pupils (the black part)
            p.fill(0);
            p.ellipse(renderX + eyeOffsetX1 + pupilOffsetX, renderY + eyeOffsetY1 + pupilOffsetY, pupilSize, pupilSize);
            p.ellipse(renderX + eyeOffsetX2 + pupilOffsetX, renderY + eyeOffsetY2 + pupilOffsetY, pupilSize, pupilSize);
        }
    }

    /**
     * Buffers a new direction for the snake.
     * @param newDirection The new direction to buffer.
     */
    public void bufferDirection(Direction newDirection){
        Objects.requireNonNull(newDirection, "New direction cannot be null");
        if(inputQueue.size() < GameConfig.INPUT_BUFFER_LIMIT){
            inputQueue.add(newDirection);
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
        return head.x() < 0 || head.x() >= GameConfig.GRID_WIDTH ||
               head.y() < 0 || head.y() >= GameConfig.GRID_HEIGHT;
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
