package com.github.sebasman;

import com.github.sebasman.config.GameConfig;
import com.github.sebasman.model.Board;
import com.github.sebasman.model.GameState;
import com.github.sebasman.model.common.Direction;
import com.github.sebasman.model.common.Position;
import com.github.sebasman.model.entities.Food;
import com.github.sebasman.model.entities.Snake;
import processing.core.PApplet;
import processing.core.PGraphics;

public class ViperVisionGame extends PApplet {
    // Dependencies
    private Board board;
    private Snake snake;
    private Food food;
    // Attributes
    private GameState state;

    // --- Override Methods ---

    @Override
    public void settings() {
        size(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        System.out.println("Window: " + super.width + "x" + super.height);
    }

    @Override
    public void setup() {
        // Initialize game
        this.state = GameState.INITIALIZING;
        System.out.println("Starting game... State: " + state);
        // Initialize environment
        frameRate(10);
        background(30,30,30);
        // Initialize grid
        this.board = new Board(GameConfig.BOARD_WIDTH_CELLS, GameConfig.BOARD_HEIGHT_CELLS);
        System.out.println("Game set up. Board: " + board.getCols() + "x" + board.getRows());
        // Initialize snake
        this.snake = new Snake(new Position(GameConfig.BOARD_WIDTH_CELLS/2, GameConfig.BOARD_HEIGHT_CELLS/2),
                GameConfig.INITIAL_SNAKE_LENGTH, Direction.RIGHT);
        System.out.println("Snake set up. Head: " + snake.getHead() + ", Direction: " + snake.getDirection());
        // Initialize food
        this.food = new Food(1);
        this.food.setPosition(new Position(3*GameConfig.BOARD_WIDTH_CELLS/4, GameConfig.BOARD_HEIGHT_CELLS/2));
        System.out.println("Food set up. Position: " + food.getPosition());

        // Finish setup
        this.state = GameState.MENU;
        System.out.println("Game set up. State: " + state);
    }

    @Override
    public void draw() {
        switch (this.state) {
            case PREPARING:
                background(200);
                textAlign(CENTER, CENTER);
                textSize(24);
                fill(0);
                text("Press any key to start", (float) width / 2, (float) height / 2);
                break;
            case PLAYING:
                update();
                render();
                break;
            case PAUSE:
                textAlign(CENTER, CENTER);
                textSize(32);
                fill(255, 255, 0);
                text("PAUSA", (float) width / 2, (float) GameConfig.GAME_AREA_HEIGHT / 2);
                break;
            case GAME_OVER:
                textAlign(CENTER, CENTER);
                textSize(32);
                fill(255, 0, 0);
                text("GAME OVER", (float) width / 2, (float) GameConfig.GAME_AREA_HEIGHT / 2);
                textSize(20);
                text("Presiona 'M' para volver al Menú", (float) width /2, (float) GameConfig.GAME_AREA_HEIGHT / 2 + 40);
                break;
            case MENU:
                textAlign(CENTER,CENTER);
                textSize(40);
                fill(255);
                text("SNAKE GAME", (float) width /2, (float) height /3);
                textSize(24);
                text("1. Jugar", (float) width /2, (float) height /2);
                text("2. Salir", (float) width /2, (float) height /2 + 50);
                break;
        }
    }

    @Override
    public void keyPressed() {
        switch (this.state) {
            case PREPARING:
                this.state = GameState.PLAYING;
                System.out.println("Estado actual: PLAYING");
                break;
            case PLAYING:
                if (key == CODED) {
                    if (keyCode == UP) {
                        snake.setDirection(Direction.UP);
                    } else if (keyCode == DOWN) {
                        snake.setDirection(Direction.DOWN);
                    } else if (keyCode == LEFT) {
                        snake.setDirection(Direction.LEFT);
                    } else if (keyCode == RIGHT) {
                        snake.setDirection(Direction.RIGHT);
                    }
                } else if (key == 'p' || key == 'P' || key == ' ') { // Espacio para pausar
                    this.state = GameState.PAUSE;
                    System.out.println("Estado actual: PAUSED");
                }
                break;
            case PAUSE:
                if (key == 'p' || key == 'P' || key == ' ') { // Espacio para reanudar
                    this.state = GameState.PLAYING;
                    System.out.println("Estado actual: PLAYING");
                }
                break;
            case GAME_OVER:
                if (key == 'm' || key == 'M') {
                    setup();
                    this.state = GameState.MENU;
                    System.out.println("Estado actual: MENU");
                }
                break;
            case MENU:
                if (key == '1') {
                    this.state = GameState.PREPARING;
                } else if (key == '2') {
                    this.state = GameState.ENDING;
                    System.out.println("Estado actual: ENDING");
                    exit();
                }
                break;
        }
    }

    // --- Utility Methods ---

    private void update() {
        this.snake.move();
        if(this.snake.isEating(this.food)){
            this.snake.grow();
            this.food.spawn(this.board.getCols(), this.board.getRows(), this.snake.getBodySet());
        }
        if(this.snake.checkCollision(this.board.getCols(), this.board.getRows())){
            this.state = GameState.GAME_OVER;
            System.out.println("Game Over. Snake collided with the wall or itself.");
        }
    }

    private void render() {
        background(230, 240, 210);
        this.board.draw(this);
        this.snake.draw(this);
        this.food.draw(this);

        // Draw UI
        fill(50);
        rect(0, 0, width, GameConfig.UI_AREA_SCORE);
        fill(255); // Texto blanco
        textSize(20);
        textAlign(LEFT, TOP);
        text("Puntuación: " + (snake.getBodySet().size()), 10, 15);
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
