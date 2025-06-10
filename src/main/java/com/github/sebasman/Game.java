package com.github.sebasman;

import com.github.sebasman.states.StartingState;
import com.github.sebasman.states.State;
import processing.core.PApplet;

/**
 * The main class of the game that manages the window, the game loop (draw),
 * and user events (keyPressed). Extends PApplet from Processing.
 */
public class Game extends PApplet {
    // Constats for game configuration
    private static final int BOX_SIZE = 25;
    private static final int GRID_WIDTH = 25;
    private static final int GRID_HEIGHT = 25;

    // Variables for game state and grid
    private State state;
    private Snake snake;
    private Food food;

    @Override
    public void settings() {
        // Set the size of the window based on the grid dimensions and box size
        super.size(GRID_WIDTH * BOX_SIZE, GRID_HEIGHT * BOX_SIZE);
    }

    @Override
    public void setup() {
        super.frameRate(10);
        super.textAlign(CENTER, CENTER);
        // Initialize game components
        this.resetGame();
        this.setState(StartingState.getInstance());
    }

    /**
     * Resets the game to its initial state.
     */
    public void resetGame() {
        this.snake = new Snake(this, new Position(GRID_WIDTH/4, GRID_HEIGHT/2));
        this.food = new Food(this);
        this.food.spawn(this.snake.getBodySet());
    }

    @Override
    public void draw() {
        if(this.state == null) {
            throw new IllegalStateException(
                    "The current state of the game cannot be null and void."
            );
        }
        this.state.update(this);
        this.state.draw(this);
    }

    @Override
    public void keyPressed() {
        if(this.state == null) {
            throw new IllegalStateException(
                    "The current state of the game cannot be null and void."
            );
        }
        this.state.keyPressed(this, keyCode);
    }

    // --- Getters ---

    /**
     * Returns the Snake object representing the snake in the game.
     * @return The Snake object.
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Returns the Food object representing the food in the game.
     * @return The Food object.
     */
    public Food getFood() {
        return food;
    }

    /**
     * Returns the width of the grid in boxes.
     * @return The width of the grid in boxes.
     */
    public int getBoxSize() {
        return BOX_SIZE;
    }

    /**
     * Returns the width of the grid in boxes.
     * @return The width of the grid in boxes.
     */
    public int getGridWidth() {
        return GRID_WIDTH;
    }

    /**
     * Returns the height of the grid in boxes.
     * @return The height of the grid in boxes.
     */
    public  int getGridHeight() {
        return GRID_HEIGHT;
    }

    // --- Setters ---

    /**
     * Sets the current game state.
     * @param state The new game state to set.
     */
    public void setState(State state) {
        this.state = state;
        this.state.onEnter(this); // Notify the state that it has been entered
    }
}
