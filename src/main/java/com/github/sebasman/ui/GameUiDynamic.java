package com.github.sebasman.ui;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.ui.UiRenderAPI;
import com.github.sebasman.entities.Board;
import com.github.sebasman.utils.Assets;
import com.github.sebasman.utils.ColorPalette;
import com.github.sebasman.utils.GameConfig;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * GameUiDynamic is a singleton class that implements the UiRenderAPI interface.
 * It is responsible for rendering the dynamic UI elements of the game, such as the score,
 * high score, game title, and the game board with the snake and food.
 */
public class GameUiDynamic implements UiRenderAPI {
    // Singleton instance for the GameUiDynamic class
    private static final GameUiDynamic INSTANCE = new GameUiDynamic();

    /**
     * Private constructor to prevent instantiation.
     */
    private GameUiDynamic() {}

    /**
     * Returns the singleton instance of GameUiDynamic.
     * @return The singleton instance of GameUiDynamic.
     */
    public static GameUiDynamic getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(PApplet context) {
        //
    }

    @Override
    public void render(Game game, Float interpolation) {
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
