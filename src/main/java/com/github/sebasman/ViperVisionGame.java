package com.github.sebasman;

import com.github.sebasman.config.GameConfig;
import com.github.sebasman.model.Board;
import com.github.sebasman.model.Food;
import com.github.sebasman.model.Snake;
import com.github.sebasman.model.common.Direction;
import com.github.sebasman.model.common.Position;
import processing.core.PApplet;

import java.util.List;

public class ViperVisionGame extends PApplet {
    // Dependencies
    private Board board;
    private Snake snake;
    private Food food;
    // Attributes
    private long lastUpdateTime;

    // --- Override Methods ---

    @Override
    public void settings() {
        size(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        System.out.println("Window: " + GameConfig.WINDOW_WIDTH + "x" + GameConfig.WINDOW_HEIGHT);
    }

    @Override
    public void setup() {
        frameRate(60);
        this.lastUpdateTime = millis();
        // Initialize game components
        this.board = new Board();

        Position snakePos = new Position(board.getWidth() / 4, board.getHeight() / 2);
        this.snake = new Snake(snakePos, Direction.RIGHT);

        Position foodPos = new Position(3 * board.getWidth() / 4, board.getHeight() / 2);
        this.food = new Food(foodPos, 1);

        System.out.println("Game set up. Board: " + board.getWidth() + "x" + board.getHeight());
        System.out.println("Initial snake: " + snake.getHead());
        System.out.println("Initial food: " + food.getPosition());
    }

    @Override
    public void draw() {
        long currentTime = millis();
        if (currentTime - lastUpdateTime >= GameConfig.INITIAL_SNAKE_SPEED_MS) {
            updateGame();
            lastUpdateTime = currentTime;
        }
        renderGame();
    }

    @Override
    public void keyPressed() { // El parámetro es KeyEvent
        if (!snake.isActive()) {
            if (key == 'r' || key == 'R') {
                setup(); // Reiniciar el juego
            }
            return;
        }

        switch (keyCode) {
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
        }
        // Para teclas no especiales como 'P' para pausa (si implementas)
        switch (key) {
            case 'p':
            case 'P':
                // Lógica de pausa
                System.out.println("Pausa (no implementado aún)");
                break;
        }
    }

    // --- Utility Methods ---

    /**
     * Updates the state of the game during each iteration of the game loop.
     * This method is responsible for handling the movement of the snake, interactions
     * with the food, and collision detection. If the snake is inactive, the method exits
     * early without performing any updates.
     * Functionality includes:
     * 1. Moving the snake in its current direction.
     * 2. Detecting and handling food consumption, updating the snake's size, and repositioning the food.
     * 3. Detecting collisions with the bounds of the board and deactivating the snake if such a collision occurs.
     * 4. Checking for self-collisions (when the snake intersects itself) and deactivating the snake if necessary.
     * Game-ending conditions:
     * - Collision with the board's boundaries results in the snake being deactivated.
     * - A self-collision results in the snake being deactivated.
     */
    private void updateGame() {
        if(!snake.isActive()) return;
        snake.move();
        // Check for collisions with food
        if(snake.getHead().equals(food.getPosition())) {
            snake.grow();
            int foodX = (food.getPosition().x() + 7) % board.getWidth();
            int foodY = (food.getPosition().y() + 5) % board.getHeight();
            food.setPosition(new Position(foodX, foodY));
            System.out.println("Food eaten! New position: " + food.getPosition());
        }
        // Check for collisions with walls
        if(!board.isWithinBounds(snake.getHead())){
            System.out.println("Collision with the edge! Pos: " + snake.getHead());
            snake.setActive(false);
            return;
        }
        // Check for collisions with itself
        if(snake.checkSelfCollision()){
            System.out.println("Self-collision!");
            snake.setActive(false); // Game Over
        }
    }

    /**
     * Renders the current state of the game onto the screen. This method is responsible
     * for visually updating the game elements, including the game board, the snake, the
     * food, and handling the game-over state.
     * Functionalities:
     * - Sets the background color of the game.
     * - Draws the game board grid.
     * - Displays the food on the board if it is active, using a distinct color.
     * - Draws the snake on the board if it is active, rendering each of its segments
     *   in a specific color.
     * - Displays a "Game Over" message and instructions to restart if the snake is
     *   no longer active.
     * The method uses the board dimensions, cell size, and the active states of the
     * snake and food entities to determine how and where to render game elements.
     */
    private void renderGame() {
        background(30,30,30);
        drawBoardGrid();
        if(food.isActive()){
            fill(255, 60, 60); // Rojo para la comida
            Position foodPos = food.getPosition();
            rect(foodPos.x() * GameConfig.CELL_SIZE,
                    foodPos.y() * GameConfig.CELL_SIZE,
                    GameConfig.CELL_SIZE,
                    GameConfig.CELL_SIZE);
        }
        // Dibujar serpiente
        if (snake.isActive()) {
            fill(60, 255, 60); // Verde para la serpiente
            List<Position> snakeSegments = snake.getPositions(); // Usa el método de GameEntity
            for (Position segmentPos : snakeSegments) {
                rect(segmentPos.x() * GameConfig.CELL_SIZE,
                        segmentPos.y() * GameConfig.CELL_SIZE,
                        GameConfig.CELL_SIZE,
                        GameConfig.CELL_SIZE);
            }
        } else {
            // Mostrar mensaje de Game Over
            fill(255);
            textAlign(CENTER, CENTER);
            textSize(32);
            text("GAME OVER", width / 2f, height / 2f);
            textSize(16);
            text("Press 'R' to Restart", width / 2f, height / 2f + 40);
        }
    }

    /**
     * Draws the grid lines of the game board on the screen.
     * This method visually divides the game board into cells using a grid framework.
     * Grid lines are drawn based on the dimensions of the board and the cell size
     * defined in the game configuration. Vertical lines are drawn first, followed
     * by horizontal lines.
     * Functional Details:
     * - The method retrieves the width and height of the board using the `board` object.
     * - The stroke color and weight are configured to visually define the grid lines.
     * - For vertical lines, the method iterates over the board width, drawing lines
     *   at intervals determined by the cell size.
     * - For horizontal lines, the method iterates over the board height, drawing lines
     *   similarly based on the cell size.
     */
    private void drawBoardGrid() {
        stroke(50); // Color de la línea de la cuadrícula
        strokeWeight(1);
        for (int x = 0; x < board.getWidth(); x++) {
            line(x * GameConfig.CELL_SIZE, 0,
                    x * GameConfig.CELL_SIZE, height);
        }
        for (int y = 0; y < board.getHeight(); y++) {
            line(0, y * GameConfig.CELL_SIZE,
                    width, y * GameConfig.CELL_SIZE);
        }
    }

    /**
     * The entry point of the application. This method is responsible for launching
     * the ViperVisionGame using the Processing framework. It initializes and starts
     * the game by invoking the main method of the PApplet class.
     * @param args an array of command-line arguments passed to the program; this is
     *             not used in the current implementation.
     */
    public static void main(String[] args) {
        PApplet.main(ViperVisionGame.class.getName());
    }
}
