package com.github.sebasman.entities;

import com.github.sebasman.entities.vo.Position;
import com.github.sebasman.interfaces.Drawable;
import com.github.sebasman.core.GameConfig;
import com.github.sebasman.ui.Assets;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.*;

/**
 * The Food class represents the food in the game.
 */
public class Food implements Drawable {
    // Attributes
    private Position position;
    // Random number generator to select a random position for the food
    private final Random random;

    /**
     * Constructor for the Food class.
     */
    public Food(){
        this.random = new Random();
    }

    @Override
    public void draw(PApplet context) {
        if(this.position == null) return; // Ensure position is set before drawing
        PImage appleImage = Assets.appleImage;
        int x = this.position.x() * GameConfig.BOX_SIZE;
        int y = this.position.y() * GameConfig.BOX_SIZE;
        context.image(appleImage, x, y, GameConfig.BOX_SIZE, GameConfig.BOX_SIZE);
    }

    /**
     * Spawns food at a random position on the grid, ensuring it does not overlap with the snake's body.
     * @param snakeBody A list of positions representing the snake's body.
     */
    public void spawn(Set<Position> snakeBody) {
        int gridWidth = GameConfig.GRID_WIDTH;
        int gridHeight = GameConfig.GRID_HEIGHT;
        int totalSpots = gridWidth * gridHeight;
        int availableSpots = totalSpots - snakeBody.size();

        if(availableSpots <= 0){
            System.out.println("¡Victory! You have eaten all the food!");
            this.position = null;
            return;
        }

        int targetEmptySpot = this.random.nextInt(availableSpots);
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
