package com.github.sebasman;

import processing.core.PApplet;

import java.util.*;

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
    public void spawn(Set<Position> snakeBody) {
        int gridWidth = ((Game) this.parent).getGridWidth();
        int gridHeight = ((Game) this.parent).getGridHeight();
        int totalSpots = gridWidth * gridHeight;
        int availableSpots = totalSpots - snakeBody.size();

        if(availableSpots <= 0){
            System.out.println("¡Victory! You have eaten all the food!");
            this.position = null;
            return;
        }

        int targetEmptySpot = (int) this.parent.random(availableSpots);
        int emptySpotCount = 0;

        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                Position currentPos = new Position(x, y);
                if (!snakeBody.contains(currentPos)) {
                    if (emptySpotCount == targetEmptySpot) {
                        this.position = currentPos;
                        return;
                    }
                    emptySpotCount++;
                }
            }
        }
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
