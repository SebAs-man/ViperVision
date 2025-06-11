package com.github.sebasman;

import com.github.sebasman.core.Food;
import com.github.sebasman.core.GameConfig;
import com.github.sebasman.core.Snake;
import com.github.sebasman.states.PlayingState;
import com.github.sebasman.states.StartingState;
import com.github.sebasman.states.State;
import com.github.sebasman.strategies.ControlStrategy;
import com.github.sebasman.ui.Assets;
import com.github.sebasman.ui.ColorPalette;
import processing.core.PApplet;

import java.util.Objects;

/**
 * The main class of the game that manages the window, the game loop (draw),
 * and user events (keyPressed). Extends PApplet from Processing.
 */
public class Game extends PApplet {
    // The last-played strategy, used to switch between strategies
    private ControlStrategy lastPlayedStrategy;
    // Variables for game components
    private State state;
    private Snake snake;
    private Food food;
    // Variables for game loop timing
    private long lastTime;
    private double nsPerTick;
    private double delta;

    @Override
    public void settings() {
        // Set the size of the window based on the grid dimensions and box size
        super.size(GameConfig.GRID_WIDTH * GameConfig.BOX_SIZE, GameConfig.GRID_HEIGHT * GameConfig.BOX_SIZE);
    }

    @Override
    public void setup() {
        // Set the frame rate
        super.frameRate(60);
        // Initialize the timing variables for the game loop
        int ticksPerSecond = GameConfig.STARTING_FRAME_RATE;
        this.nsPerTick = 1_000_000_000.0 / ticksPerSecond; // Convert ticks per second to nanoseconds per tick
        this.lastTime = System.nanoTime();
        this.delta = 0;
        // Set the alignment for the game
        super.textAlign(CENTER, CENTER);
        // Load assets such as images and fonts
        Assets.load(this);
        ColorPalette.load(this);
        // Initialize the game state to the starting state
        this.setState(StartingState.getInstance());
    }

    /**
     * Starts a new game with the specified control strategy.
     * @param strategy The control strategy to use for the game (e.g., human control, AI).
     */
    public void starGame(ControlStrategy strategy){
        Objects.requireNonNull(strategy, "Strategy cannot be null");
        this.lastPlayedStrategy = strategy;
        this.snake = new Snake(new Position(GameConfig.GRID_WIDTH/4, GameConfig.GRID_HEIGHT/2), 3);
        this.food = new Food();
        this.food.spawn(this.snake.getBodySet());

        PlayingState playingState = PlayingState.getInstance();
        playingState.setControlStrategy(strategy);
        this.setState(playingState);
    }

    /**
     * Retries the last played game by restarting with the last used strategy.
     */
    public void retryGame(){
        if(this.lastPlayedStrategy != null){
            this.starGame(this.lastPlayedStrategy);
        } else {
            throw new IllegalStateException(
                    "Cannot retry the game without a previously played strategy."
            );
        }
    }

    /**
     * The main game loop that runs continuously.
     */
    private void update() {
        if(this.state == null) {
            throw new IllegalStateException(
                    "The current state of the game cannot be null and void."
            );
        }
        this.state.update(this);
    }

    /**
     * Renders the game state at a specific interpolation value.
     * @param interpolation The interpolation value used for rendering, typically between 0 and 1.
     */
    private void render(float interpolation) {
        if(this.state == null) {
            throw new IllegalStateException(
                    "The current state of the game cannot be null and void."
            );
        }
        this.state.draw(this, interpolation);
    }

    @Override
    public void draw() {
        long now = System.nanoTime();
        delta += (now - lastTime) / nsPerTick;
        lastTime = now;
        // Loop to update logic at a fixed rate
        // Ensures that logic is not speeded up on fast computers
        while (delta >= 1) {
            update();
            delta--;
        }
        // Rendering occurs as fast as possible, with interpolation
        // The 'delta' here is the percentage of progress towards the next tick (0.0 to 1.0).
        render((float) delta);
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

    @Override
    public void mousePressed() {
        if(this.state == null) {
            throw new IllegalStateException(
                    "The current state of the game cannot be null and void."
            );
        }
        this.state.mousePressed(this);
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
