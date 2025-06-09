package com.github.sebasman;

import processing.core.PApplet;
import processing.core.PVector;

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
    private GameState state;
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
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame() {
        this.snake = new Snake(this, new Position(GRID_WIDTH/4, GRID_HEIGHT/2));
        this.food = new Food(this);
        this.food.spawn(this.snake.getBody());
        this.state = GameState.PLAYING;
    }

    @Override
    public void draw() {
        switch (this.state) {
            case STARTING:
                this.drawPresentation();
                break;
            case PLAYING:
                // Logic for updating the game state
                this.snake.update();
                this.checkCollisions();
                // Render the game components
                super.background(0);
                this.snake.draw();
                food.draw();
                break;
            case GAME_OVER:
                this.drawGameOver();
                break;
            case PAUSED:
                break;
            default:
                super.background(150);
                break;
        }
    }

    /**
     * Draws the main menu of the game.
     */
    private void drawPresentation(){
        super.background(25, 25, 112);
        super.fill(255);
        super.textSize(50);
        super.text("VIPER VISION", width / 2f, height / 3f);
        super.textSize(20);
        super.text("Press any key for start", width / 2f, height / 2f);
    }

    /**
     * Draws the game over the screen.
     */
    private void drawGameOver(){
        super.background(80, 0, 0); // Fondo rojo oscuro
        super.fill(255, 0, 0);
        super.textSize(50);
        super.text("GAME OVER", width / 2f, height / 3f);
        super.textSize(20);
        super.fill(255);
        super.text("Press 'Enter' to restart", width / 2f, height / 2f);
    }

    /**
     * Checks for collisions between the snake and the walls, itself, or food.
     */
    private void checkCollisions(){
        // Check if the snake's head collides with the wall
        if(this.snake.checkCollisionWithWall()){
            this.state = GameState.GAME_OVER;
            return;
        }
        // Check if the snake's head collides with itself
        if(this.snake.checkCollisionWithSelf()){
            this.state = GameState.GAME_OVER;
            return;
        }
        // Check if the snake's head collides with the food
        if(this.snake.getHead().equals(this.food.getPosition())){
            this.snake.grow(); // Grow the snake
            this.food.spawn(this.snake.getBody()); // Spawn new food
        }
    }

    @Override
    public void keyPressed() {
        switch (this.state) {
            case STARTING:
                this.state = GameState.PLAYING;
                break;
            case PLAYING:
                if (keyCode == UP) {
                    this.snake.setDirection(Direction.UP);
                } else if (keyCode == DOWN) {
                    this.snake.setDirection(Direction.DOWN);
                } else if (keyCode == LEFT) {
                    this.snake.setDirection(Direction.LEFT);
                } else if (keyCode == RIGHT) {
                    this.snake.setDirection(Direction.RIGHT);
                }
                break;
            case GAME_OVER:
                if (keyCode == ENTER) {
                    this.resetGame(); // Reset the game when Enter is pressed
                    this.state = GameState.PLAYING; // Change state to playing
                }
                break;
        }
    }

    // Getters

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
}
