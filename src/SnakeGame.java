import Elements.Apple;
import Elements.Button;
import Elements.Snake;
import Vo.GameState;
import processing.core.*;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Clase principal que extiende PApplet y representa el entorno del juego.
 * Maneja la configuración de la ventana, el dibujo de la cuadrícula del
 * área de juego, y otros aspectos visuales del sketch.
 */
public final class SnakeGame extends PApplet {
    // --- Constantes (personalizables) ---
    private static final int CELL_SIZE = 40;
    public static final int ROWS = 20;
    public static final int COLS = 20;
    private static final int TOP_BAR_HEIGHT = 50;
    private static final int GAME_AREA_PADDING = 15;
    private final static int UPDATE_INTERVAL = 100; // 200 ms
    // --- Constantes de la ventana ---
    private static final int GAME_AREA_WIDTH = COLS * CELL_SIZE;
    private static final int GAME_AREA_HEIGHT = ROWS * CELL_SIZE;
    private static final int SCREEN_WIDTH = GAME_AREA_WIDTH + (GAME_AREA_PADDING * 2);
    private static final int SCREEN_HEIGHT = GAME_AREA_HEIGHT + TOP_BAR_HEIGHT + (GAME_AREA_PADDING * 2);
    // --- Recursos externos ---
    private final Map<String, PFont> fonts = new HashMap<>();
    private final Map<String, PImage> images = new HashMap<>();
    // --- Elementos del juego ---
    private final List<Button> menuButtons = new LinkedList<>();
    private final List<Button> gameOverButtons = new LinkedList<>();
    private Apple apple;
    private Snake snake;
    private PGraphics staticBackground;
    // --- Colores predefinidos ---
    private int backgroundColor1;
    private int backgroundColor2;
    private int snakeColor;
    private int buttonColor;
    // --- Variables de control ---
    private long lastUpdateTime = 0;
    private final Queue<PVector> directionQueue = new LinkedBlockingQueue<>();
    // --- Estado del juego ---
    private GameState currentState;
    private GameState previousState;
    private int score;
    private int highScore;

    /**
     * Configura los parámetros iniciales de la ventana de la aplicación.
     * Permite la personalización de parámetros como el tamaño del lienzo
     * u otros ajustes del sketch antes de que se inicie el sketch.
     */
    @Override
    public void settings() {
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * Método de inicialización del sketch. Se ejecuta una vez al inicio
     * del programa y se utiliza para configurar el entorno de dibujo,
     * inicializar variables y preparar el estado inicial del juego.
     */
    @Override
    public void setup() {
        frameRate(60);
        // Inicialización de los colores
        int topBarColor = color(53, 115, 36);
        int background = color(62, 150, 45);
        backgroundColor1 = color(170, 215, 81);
        backgroundColor2 = color(162, 209, 73);
        snakeColor = color(97, 74, 240);
        buttonColor = color(63, 15, 208);
        // Inicialización de los recursos
        String[] fontNames = {"text-base", "text-title"};
        try {
            for(String name : fontNames) {
                fonts.put(name, createFont("resources/fonts/" + name + ".ttf", 24) );
            }
            textFont(fonts.get("text-base"));
            System.out.println("Fuentes cargadas correctamente.");
        } catch (Exception e) {
            System.err.println("Error al cargar el recurso: " + e.getMessage());
        }
        String[] imgNames = {"apple","play","watch","config","trophy","retry","home"};
        try{
            for(String name : imgNames) {
                images.put(name, loadImage("resources/images/" + name + ".png"));
            }
            System.out.println("Imágenes cargadas correctamente.");
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }
        // Inicialización del estado del juego
        currentState = GameState.MENU;
        initializeButtons();
        // Crear el buffer para el fondo estático
        staticBackground = createGraphics(SCREEN_WIDTH, SCREEN_HEIGHT);
        staticBackground.beginDraw();
        staticBackground.background(background);
        // Dibujar la barra superior
        staticBackground.noStroke();
        staticBackground.rectMode(PConstants.CORNER);
        staticBackground.fill(topBarColor);
        staticBackground.rect(0, 0, SCREEN_WIDTH, TOP_BAR_HEIGHT);
        // Dibujar el área de juego
        drawCheckerBoardGrid();
        staticBackground.endDraw();
        // Configuraciones de dibujo iniciales
        rectMode(PConstants.CORNER);
        ellipseMode(PConstants.CENTER);
        imageMode(PConstants.CENTER);
        noStroke();
    }

    /**
     * Inicializa los botones interactivos para el menú y las pantallas de juego.
     * El método define y añade botones a dos listas correspondientes: `menuButtons` para el menú principal
     * y `gameOverButtons` para el menú de juego. A cada botón se le asignan propiedades como etiqueta,
     * posición, imagen asociada, color, y una acción específica a realizar cuando se pulsa el botón.
     */
    private void initializeButtons(){
        menuButtons.add(new Button(images.get("play"), "Jugar",  buttonColor, SCREEN_WIDTH/2, SCREEN_HEIGHT/2,
                () -> startGame(GameState.PLAYING_PLAYER), this));
        menuButtons.add(new Button(images.get("watch"), "Ver IA Jugar",  buttonColor, SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + Button.HEIGHT + 20,
                () -> startGame(GameState.PLAYING_AI), this));
        menuButtons.add(new Button(images.get("config"), "Configuración",  buttonColor,  SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + (Button.HEIGHT*2) + 40,
                () -> System.out.println("(Nada que hacer...)"),this));
        // Botones de Game Over
        gameOverButtons.add(new Button(images.get("retry"), "Reintentar",  buttonColor, SCREEN_WIDTH/2, (3*SCREEN_HEIGHT)/4,
                () -> {
                    resetGame();
                    if(previousState == GameState.PLAYING_PLAYER || previousState == GameState.PLAYING_AI) {
                        startGame(previousState);
                    } else{
                        throw new RuntimeException("Estado de juego no válido: " + previousState);
                    }
                },this));
        gameOverButtons.add(new Button(images.get("home"), "Menú",  buttonColor, SCREEN_WIDTH/2, (3*SCREEN_HEIGHT)/4 + Button.HEIGHT + 20,
                () -> {
                    currentState = GameState.MENU;
                    resetGame();
                },this));
    }

    /**
     * Dibuja un patrón de damero en un área de cuadrícula definida.
     * Este método crea una rejilla compuesta de cuadrados de colores alternados
     * que se asemejan a un tablero de ajedrez dentro de los límites especificados.
     * Los colores de los cuadrados se alternan en función de su posición en la cuadrícula.
     * El método utiliza dos colores de fondo predefinidos para el patrón.
     */
    private void drawCheckerBoardGrid(){
        for (int y = TOP_BAR_HEIGHT+GAME_AREA_PADDING; y < SCREEN_HEIGHT-GAME_AREA_PADDING; y += CELL_SIZE) {
            for (int x = GAME_AREA_PADDING; x < SCREEN_WIDTH-GAME_AREA_PADDING; x += CELL_SIZE) {
                if (((x / CELL_SIZE) + (y / CELL_SIZE)) % 2 == 0) {
                    staticBackground.fill(backgroundColor1);
                } else {
                    staticBackground.fill(backgroundColor2);
                }
                staticBackground.rect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    /**
     * Maneja la lógica de dibujo y renderizado para la aplicación Game.SnakeGame.
     * Este método es invocado repetidamente en el bucle principal del juego para actualizar los elementos visuales mostrados en la pantalla.
     * elementos visuales que aparecen en la pantalla. Ajusta su comportamiento basado en el
     * estado actual del juego.
     * El método dibujar establece el cursor en una flecha por defecto y realiza
     * diferentes tareas de renderizado dependiendo del estado actual del juego:
     */
    @Override
    public void draw() {
        boolean isOverAnyButton = false;
        List<Button> currentButtonsToCheck = null;
        switch (currentState) {
            case GameState.MENU:
                drawMenu();
                currentButtonsToCheck = menuButtons;
                break;
            case GameState.GAME_OVER:
                gameOverScreen();
                currentButtonsToCheck = gameOverButtons;
                break;
            case GameState.PLAYING_PLAYER:
            case GameState.PLAYING_AI:
                drawGameScreen();
                break;
            default:
                break;
        }
        // Muestra el cursor de la mano si el mouse está sobre un botón
        if(currentButtonsToCheck != null) {
            for(Button button : currentButtonsToCheck) {
                if(button.isMouseOver(mouseX, mouseY)) {
                    isOverAnyButton = true;
                    break;
                }
            }
        }
        cursor(isOverAnyButton ? HAND : ARROW);
    }

    /**
     * Dibuja el menú principal del juego.
     * Este método superpone un fondo semi-transparente al área de juego existente.
     * Internamente, este método utiliza otros métodos como `drawGameScreen()`
     * para renderizar primero el área de juego y `drawButton()` para dibujar los botones de menú.
     */
    private void drawMenu() {
        drawGameScreen();
        // Dibuja el fondo del menú
        fill(0, 0, 0, 175);
        rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        // Título
        textFont(fonts.get("text-title"));
        textSize(80);
        fill(242, 25, 25);
        textAlign(PConstants.CENTER, PConstants.CORNER);
        text("Snake Game", (float) SCREEN_WIDTH / 2, (float) SCREEN_HEIGHT / 4);
        // Dibujar botones
        textFont(fonts.get("text-base"));
        for(Button button : menuButtons) {
            button.draw();
        }
    }

    /**
     * Renderiza la pantalla principal del juego, incluyendo el área de juego, patrón de fondo,
     * y el relleno alrededor del área de juego.
     */
    private void drawGameScreen() {
        long now = millis();
        long elapsed = now - lastUpdateTime;
        boolean updatedThisFrame = false;
        float animationProgress;
        // Lógica de actualización basada en tiempo
        if (currentState == GameState.PLAYING_PLAYER || currentState == GameState.PLAYING_AI) {
            if(elapsed > UPDATE_INTERVAL) {
                if (!directionQueue.isEmpty()) {
                    PVector nextDirection = directionQueue.poll();
                    snake.setDirection(nextDirection);
                }
                snake.update();
                checkCollisions();
                lastUpdateTime = millis();
                updatedThisFrame = true;
            }
            animationProgress = updatedThisFrame ? 0.f : constrain(elapsed / (float)UPDATE_INTERVAL, 0f, 1f);
        } else{
            animationProgress = 0.0f;
        }
        // Lógica de dibujo
        imageMode(PConstants.CORNER);
        image(staticBackground, 0, 0);
        imageMode(PConstants.CENTER);
        drawTextScore(30, TOP_BAR_HEIGHT / 2.0f);
        if(apple != null) apple.draw(images.get("apple"));
        if(snake != null) snake.draw(snakeColor, animationProgress);
    }

    /**
     * Dibuja la puntuación basada en texto y sus iconos asociados en la barra superior de la pantalla del juego.
     * Este método renderiza la puntuación actual y la puntuación más alta junto con los iconos gráficos
     * (manzana y trofeo) que los representan. Si las imágenes respectivas son nulas, las formas
     * (círculos de colores) se dibujan en su lugar. El texto se alinea y estiliza adecuadamente
     * para ajustarse al diseño de la barra superior.
     */
    private void drawTextScore(float iconX, float iconY) {
        float iconSize = TOP_BAR_HEIGHT * 0.7f;
        // Verificar si la imagen de la manzana es nula
        if(!images.containsKey("apple")) {
            fill(255, 0, 0);
            circle(iconX, iconY, iconSize);
        } else{
            image(images.get("apple"), iconX, iconY, iconSize, iconSize);
        }

        fill(255);
        textFont(fonts.get("text-base"));
        textSize(24);
        textAlign(PConstants.LEFT, PConstants.CENTER);
        text(score, iconX + iconSize, iconY);

        // Verificar si la imagen del trofeo es nula
        if(!images.containsKey("trophy")) {
            fill(254, 201, 0);
            circle(iconX + iconSize + 75, iconY, iconSize);
        } else{
            image(images.get("trophy"), iconX + iconSize + 75, iconY, iconSize, iconSize);
        }

        fill(255);
        textFont(fonts.get("text-base"));
        textSize(24);
        text(highScore, iconX + (iconSize * 2) + 75, iconY);
    }

    /**
     * Comprueba las colisiones durante el juego.
     * Este método se encarga de la detección de colisiones entre la serpiente y la manzana, así como
     * entre la serpiente y los límites del juego o ella misma. Basado en el tipo de colisión detectada,
     * activa los eventos apropiados del juego, como actualizar la puntuación, hacer crecer la serpiente,
     * generar una nueva manzana, o terminar el juego.
     */
    private void checkCollisions() {
        PVector snakeHead = snake.getBody().getLast();
        // Comprobar colisión con la manzana
        if(snakeHead.x == apple.getCol() && snakeHead.y == apple.getRow()) {
            score++;
            highScore = max(score, highScore);
            snake.grow();
            apple.spawn(snake, ROWS, COLS);
        }
        // Comprobar colisión con los bordes.
        boolean wallCollision = snakeHead.x < 0 || snakeHead.x >= COLS || snakeHead.y < 0 || snakeHead.y >= ROWS;
        if(wallCollision || snake.checkSelfCollision()) {
            previousState = currentState;
            currentState = GameState.GAME_OVER;
        }
    }

    /**
     * Renderiza la pantalla de Game Over de la aplicación.
     * Este método se invoca cuando el juego pasa al estado GAME_OVER.
     * La pantalla incluye un fondo semitransparente, un título «GAME OVER»,
     * la puntuación del usuario, y dos botones interactivos para «Reintentar» y «Menú».
     * También maneja efectos hover para los botones, cambiando el cursor
     * para indicar interactividad.
     */
    private void gameOverScreen() {
        drawGameScreen();
        // Dibujar el fondo del menú
        fill(0, 0, 0, 200);
        rect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        // Título
        fill(242, 25, 25);
        textFont(fonts.get("text-title"));
        textSize(80);
        textAlign(PConstants.CENTER, PConstants.CENTER);
        text("GAME OVER", (float) SCREEN_WIDTH / 2, (float) SCREEN_HEIGHT / 3);
        drawTextScore(SCREEN_WIDTH / 2.f - 75, SCREEN_HEIGHT / 2.f - 20);
        // Dibujar botones
        textFont(fonts.get("text-base"));
        for(Button button : gameOverButtons) {
            button.draw();
        }
    }

    @Override
    public void mousePressed() {
        switch (currentState) {
            case GameState.MENU:
                for(Button button : menuButtons) {
                    if(button.isMouseOver(mouseX, mouseY)) {
                        button.performAction();
                        break;
                    }
                }
                break;
            case GameState.GAME_OVER:
                for (Button btn : gameOverButtons) {
                    if (btn.isMouseOver(mouseX, mouseY)) {
                        btn.performAction();
                        break;
                    }
                }
                break;
        }
    }

    /**
     * Inicia el juego inicializando el estado del juego, la puntuación, la serpiente y la manzana.
     * El bucle de juego se activa después de que la configuración se ha completado.
     * @param state El estado inicial del juego. Debe ser PLAYING_PLAYER o PLAYING_AI.
     * Si el estado no es válido, se lanza una RuntimeException.
     */
    private void startGame(GameState state) {
        if(state != GameState.PLAYING_PLAYER && state != GameState.PLAYING_AI) {
            throw new RuntimeException("Estado de juego no válido: " + state);
        }
        currentState = state;
        score = 0;
        snake = new Snake(this, COLS / 4, ROWS / 2,
                CELL_SIZE, GAME_AREA_PADDING, TOP_BAR_HEIGHT+GAME_AREA_PADDING);
        if(apple == null) {
            apple = new Apple(this, CELL_SIZE, GAME_AREA_PADDING,
                    TOP_BAR_HEIGHT+GAME_AREA_PADDING);
        }
        apple.spawn(snake, ROWS, COLS);
        lastUpdateTime = millis();
    }

    /**
     * Reinicia el juego a su estado inicial.
     * Este método restablece el estado del juego, la puntuación, la serpiente y la manzana.
     * También se asegura de que el juego vuelva al menú principal.
     */
    private void resetGame() {
        directionQueue.clear();
        score = 0;
        snake = null;
        apple = null;
    }

    /**
     * Pausa el juego deteniendo el bucle de renderizado y mostrando una sobreimpresión de pausa.
     * El método muestra una superposición blanca semitransparente que cubre la pantalla y muestra el texto «PAUSA» en el centro de la pantalla.
     * muestra el texto «PAUSA» en el centro de la pantalla. El bucle de renderizado se
     * se detiene mediante la función `noLoop()` para congelar las actualizaciones visuales del juego.
     * Este método se utiliza cuando el estado del juego necesita pasar a un estado de pausa,
     * impidiendo más acciones de juego hasta que se reanude.
     */
    private void pauseGame() {
        fill(255, 255, 255, 175);
        rect(0, 0, width, height); // Cubre la pantalla
        fill(0);
        textFont(fonts.get("text-title"));
        textSize(60);
        textAlign(PConstants.CENTER, PConstants.CENTER);
        text("PAUSA", (float) width /2, (float) height /2);
        noLoop();
    }

    @Override
    public void keyPressed() {
        if(keyCode == ' '){
            switch (currentState) {
                case GameState.PLAYING_PLAYER:
                case GameState.PLAYING_AI:
                    pauseGame();
                    previousState = currentState;
                    currentState = GameState.PAUSED;
                    break;
                case GameState.PAUSED:
                    currentState = previousState;
                    loop();
                    break;
                case GameState.MENU:
                    startGame(GameState.PLAYING_PLAYER);
                    break;
                case GameState.GAME_OVER:
                    resetGame();
                    if(previousState == GameState.PLAYING_PLAYER || previousState == GameState.PLAYING_AI) {
                        startGame(previousState);
                    } else{
                        startGame(GameState.PLAYING_PLAYER);
                    }
                    break;
            }
            return;
        }
        if(currentState == GameState.PLAYING_PLAYER && snake != null) {
            int dx = 0;
            int dy = 0;
            boolean directionKeyPressed = true;
            // Verificar si la tecla presionada es una de las teclas de dirección
            if (keyCode == PConstants.UP) dy = -1;
            else if (keyCode == PConstants.DOWN) dy = 1;
            else if (keyCode == PConstants.LEFT) dx = -1;
            else if (keyCode == PConstants.RIGHT) dx = 1;
            else directionKeyPressed = false;

            if(directionKeyPressed) {
                directionQueue.offer(new PVector(dx, dy));
            }
        }
    }

    /**
     * Punto de entrada de la aplicación. Este método inicializa el framework PApplet
     * lanzando el sketch principal definido en el proyecto.
     * @param args argumentos de línea de comandos pasados a la aplicación
     */
    public static void main(String[] args) {
        PApplet.main("SnakeGame");
    }
}
