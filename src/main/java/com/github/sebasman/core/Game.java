package com.github.sebasman.core;

import com.github.sebasman.core.interfaces.*;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.utils.ColorPalette;
import com.github.sebasman.utils.GameConfig;
import processing.core.PApplet;

import java.util.Objects;
import java.util.Stack;

/**
 * The main class of the game that manages the window, the game loop (render),
 * and user events (keyPressed). Extends PApplet from Processing.
 */
public class Game extends PApplet {
    // The last-played strategy, used to switch between strategies
    private ControlStrategy lastPlayedStrategy;
    // The stack of game states, allowing for state management
    private final Stack<State> states;
    // The snake and food instances, which are part of the game state
    private SnakeAPI snake;
    private FoodAPI food;
    private final UiRenderAPI render;
    // Variables for game loop timing
    private long lastTime;
    private double nsPerTick;
    private double delta;
    // Score and high score variables
    private int score;
    private int highScore;

    /**
     * Constructor for the Game class.
     * @param initialState The initial state of the game to start with.
     */
    public Game(State initialState, UiRenderAPI render) {
        Objects.requireNonNull(render);
        this.states = new Stack<>();
        this.render = render;
        this.pushState(initialState);
    }

    @Override
    public void settings() {
        int boardPixelWidth = GameConfig.GRID_WIDTH * GameConfig.BOX_SIZE;
        int boardPixelHeight = GameConfig.GRID_HEIGHT * GameConfig.BOX_SIZE;
        // Set the size of the window based on the grid dimensions and box size
        super.size(boardPixelWidth + (GameConfig.GAME_AREA_PADDING*4) + GameConfig.SIDE_PANEL_WIDTH,
                boardPixelHeight + GameConfig.GAME_AREA_PADDING*3 + GameConfig.TOP_BAR_HEIGHT);
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
        this.render.initialize(this);
    }

    /**
     * Pushes a new game state onto the stack and notifies it.
     * @param state The new state to be pushed onto the stack.
     */
    public void pushState(State state) {
        Objects.requireNonNull(state, "State cannot be null");
        this.states.push(state);
        state.onEnter(this);
    }

    /**
     * Changes the current game state to a new state.
     * @param newState The new state to switch to.
     */
    public void changeState(State newState) {
        this.popState(); // Remove the current state
        this.pushState(newState);
    }

    /**
     * Removes the current state from the stack.
     */
    public void popState() {
        if (!states.isEmpty()) {
            states.pop();
        }
    }

    /**
     * Resets the score to zero.
     */
    public void resetScore() {
        this.score = 0;
    }

    /**
     * Increments the score by a specified number of points.
     * @param points The number of points to add to the score.
     */
    public void incrementScore(int points) {
        this.score += points;
        if (this.score > this.highScore) {
            this.highScore = this.score;
        }
    }

    @Override
    public void draw() {
        State currentState = this.states.peek();
        if(currentState == null) {
            throw new IllegalStateException(
                    "The current state of the game cannot be null and void."
            );
        }
        long now = System.nanoTime();
        delta += (now - lastTime) / nsPerTick;
        lastTime = now;
        // Loop to update logic at a fixed rate
        // Ensures that logic is not speeded up on fast computers
        while (delta >= 1) {
            currentState.update(this);
            delta--;
        }
        // Rendering occurs as fast as possible, with interpolation
        // The 'delta' here is the percentage of progress towards the next tick (0.0 to 1.0).
        currentState.draw(this, (float) delta);
    }

    @Override
    public void keyPressed() {
        State currentState = this.states.peek();
        if(currentState == null) {
            throw new IllegalStateException(
                    "The current state of the game cannot be null and void."
            );
        }
        currentState.keyPressed(this, keyCode);
    }

    @Override
    public void mousePressed() {
        State currentState = this.states.peek();
        if(currentState == null) {
            throw new IllegalStateException(
                    "The current state of the game cannot be null and void."
            );
        }
        currentState.mousePressed(this);
    }

    // --- Getters ---

    /**
     * Returns the current snake instance in the game.
     * @return The SnakeAPI instance representing the snake in the game.
     */
    public SnakeAPI getSnake() {
        return snake;
    }

    /**
     * Returns the current food instance in the game.
     * @return The FoodAPI instance representing the food in the game.
     */
    public FoodAPI getFood() {
        return food;
    }

    /**
     * Returns the current game state.
     * @return The current State object representing the game state.
     */
    public ControlStrategy getLastPlayedStrategy() {
        return lastPlayedStrategy;
    }

    /**
     * Returns the current score of the game.
     * @return The current score as an integer.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the high score of the game.
     * @return The high score as an integer.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Returns the render API used for UI rendering.
     * @return The UiRenderAPI instance used for rendering the game UI.
     */
    public UiRenderAPI getRender() {
        return render;
    }

    // --- Setters ---

    /**
     * Sets the snake instance for the game.
     * @param snake The SnakeAPI instance representing the snake in the game.
     */
    public void setSnake(SnakeAPI snake) {
        this.snake = snake;
    }

    /**
     * Sets the food instance for the game.
     * @param food The FoodAPI instance representing the food in the game.
     */
    public void setFood(FoodAPI food) {
        this.food = food;
    }

    /**
     * Sets the last played strategy for the game.
     * @param lastPlayedStrategy The ControlStrategy instance representing the last played strategy.
     */
    public void setLastPlayedStrategy(ControlStrategy lastPlayedStrategy) {
        Objects.requireNonNull(lastPlayedStrategy, "Last played strategy cannot be null");
        this.lastPlayedStrategy = lastPlayedStrategy;
    }
}
