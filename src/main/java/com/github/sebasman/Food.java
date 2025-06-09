package com.github.sebasman;

import processing.core.PApplet;

import java.util.List;
import java.util.Objects;

public class Food {
    private final PApplet parent;
    private Position position;
    private final int boxSize;

    /**
     * Food constructor.
     * @param parent The reference to the PApplet (the Game class).
     */
    public Food(PApplet parent) {
        Objects.requireNonNull(parent, "Parent cannot be null");
        this.parent = parent;
        this.boxSize = ((Game) this.parent).getBoxSize();
    }

    public void draw(){
        if(this.position == null) return; // Ensure position is set before drawing
        this.parent.fill(255, 0, 0);
        this.parent.noStroke();
        this.parent.rect(position.x() * boxSize, position.y() * boxSize, boxSize, boxSize);
    }

    /**
     * Spawns food at a random position on the grid, ensuring it does not overlap with the snake's body.
     * @param snakeBody A list of positions representing the snake's body.
     */
    public void spawn(List<Position> snakeBody) {
        int gridWidth = ((Game) this.parent).getGridWidth();
        int gridHeight = ((Game) this.parent).getGridHeight();
        do{
            // Generate a random position for the food within the grid
            int x = (int) this.parent.random(gridWidth);
            int y = (int) this.parent.random(gridHeight);
            this.position = new Position(x, y);
        } while (snakeBody != null && snakeBody.contains(this.position));
    }

    // --- Getters ---

    /**
     * Spawns food at a random position on the grid.
     * @return The position of the spawned food.
     */
    public Position getPosition() {
        return this.position;
    }
}
