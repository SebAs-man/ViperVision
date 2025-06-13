package com.github.sebasman.ui;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.UiRenderAPI;
import com.github.sebasman.entities.Board;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.utils.ColorPalette;
import com.github.sebasman.utils.GameConfig;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * A singleton that manages the rendering of the main user interface.
 * Uses a PGraphics buffer to pre-render static elements and optimize performance.
 */
public final class GameUiImpl implements UiRenderAPI {
    // Singleton instance of GameUI
    private static final GameUiImpl INSTANCE = new GameUiImpl();
    // PGraphics buffer for pre-rendering UI elements
    private PGraphics buffer;
    // Flag to check if the UI has been initialized
    private boolean isInitialized = false;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameUiImpl() {}

    /**
     * Returns the singleton instance of GameUI.
     * @return The singleton instance of GameUI.
     */
    public static GameUiImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(Game game){
        if(isInitialized) {
            System.err.println("GameUI is already initialized, skipping...");
            return; // UI is already initialized, skip initialization
        }
        // Set the flag to true to prevent re-initialization
        isInitialized = true;
        // Initialize the PGraphics buffer with the size of the game window
        this.buffer = game.createGraphics(game.width, game.height);
        // Load the color palette for UI elements
        preRenderElements(game);
    }

    /**
     * Pre-renders the static elements of the UI into the buffer.
     * @param game The game instance used to access the window size and other configurations.
     */
    private void preRenderElements(Game game) {
        buffer.beginDraw();
        // Set the background color for the UI buffer
        buffer.background(ColorPalette.UI_BACKGROUND);
        // Draw the frame for the game area
        buffer.noStroke();
        buffer.fill(ColorPalette.UI_FRAME);
        // Draw the top bar
        buffer.rect(GameConfig.GAME_AREA_PADDING/2f, GameConfig.GAME_AREA_PADDING,
                game.width - GameConfig.SIDE_PANEL_WIDTH - GameConfig.GAME_AREA_PADDING*3f, GameConfig.TOP_BAR_HEIGHT, 16);
        // Draw the side panel
        buffer.rect(game.width - GameConfig.GAME_AREA_PADDING - GameConfig.SIDE_PANEL_WIDTH, GameConfig.GAME_AREA_PADDING,
                GameConfig.SIDE_PANEL_WIDTH,game.height - (GameConfig.GAME_AREA_PADDING*2), 16);
        buffer.endDraw();
    }

    @Override
    public void render(Game game, Float interpolation) {
        if (!isInitialized) {
            System.err.println("GameUI is not initialized, skipping render...");
            return; // UI is not initialized, skip drawing
        }
        // Draw the pre-rendered buffer onto the main game canvas
        game.image(buffer, 0, 0);
        // Draw dynamic elements
        game.pushStyle();
        // Draw the score and high score
        float iconSize = GameConfig.TOP_BAR_HEIGHT*0.75f;
        float iconX = iconSize + GameConfig.GAME_AREA_PADDING/2f;
        float icon_X = game.width - GameConfig.GAME_AREA_PADDING*2 - GameConfig.SIDE_PANEL_WIDTH - iconX;
        float iconY = GameConfig.GAME_AREA_PADDING + GameConfig.TOP_BAR_HEIGHT/2f;
        game.textFont(Assets.textFont);
        game.textSize(iconSize*0.85f);
        game.fill(ColorPalette.TEXT_PRIMARY);
        game.imageMode(PConstants.CENTER);
        // Score display
        game.textAlign(PConstants.LEFT, PConstants.CENTER);
        game.image(Assets.appleImage, iconX, iconY, iconSize, iconSize);
        game.text(game.getScore(), iconX + iconSize, iconY);
        // High score display
        game.textAlign(PConstants.RIGHT, PConstants.CENTER);
        game.image(Assets.trophyImage, icon_X, iconY, iconSize, iconSize);
        game.text(game.getHighScore(), icon_X - iconSize, iconY);
        // Draw the game title
        game.textFont(Assets.titleFont);
        game.textAlign(PConstants.CENTER, PConstants.CENTER);
        game.textSize(GameConfig.TOP_BAR_HEIGHT*0.35f);
        game.text("VIPER VISION", (GameConfig.GAME_AREA_PADDING/2f) + ((GameConfig.BOX_SIZE*GameConfig.GRID_WIDTH)/2f),
                GameConfig.GAME_AREA_PADDING + GameConfig.TOP_BAR_HEIGHT / 2f);
        // Draw the game board, snake, and food
        game.imageMode(PConstants.CORNER);
        game.pushMatrix();
        game.translate(GameConfig.GAME_AREA_PADDING, GameConfig.GAME_AREA_PADDING*2 + GameConfig.TOP_BAR_HEIGHT);
        Board.getInstance().draw(game, null);
        if(game.getSnake() != null){
            game.getSnake().draw(game, interpolation);
        }
        if(game.getFood() != null){
            game.getFood().draw(game, null);
        }
        game.popMatrix();
        game.popStyle();
    }
}
