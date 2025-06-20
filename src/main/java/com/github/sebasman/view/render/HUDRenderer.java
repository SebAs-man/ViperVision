package com.github.sebasman.view.render;

import com.github.sebasman.GameConfig;
import com.github.sebasman.presenter.HUDController;
import com.github.sebasman.view.GameView;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.assets.ColorPalette;
import processing.core.PConstants;

/**
 * Singleton Renderer responsible for drawing the Heads-Up Display (HUD),
 * which includes the score, high score and other overlay UI elements.
 */
public final class HUDRenderer {
    // Singleton instance of HUDRenderer
    private static final HUDRenderer INSTANCE = new HUDRenderer();

    /**
     * Private constructor to prevent instantiation.
     */
    private HUDRenderer() {}

    /**
     * Returns the singleton instance of HUDRenderer.
     * @return The singleton instance of HUDRenderer.
     */
    public static HUDRenderer getInstance() {
        return INSTANCE;
    }

    /**
     * Draws the HUD using the formatted data from HUDController.
     * @param game The PApplet context to draw.
     * @param controller The HUD controller containing the text to display.
     */
    public void render(GameView game, HUDController controller){
        if (controller == null) return;

        game.pushStyle();

        float iconSize = GameConfig.TOP_BAR_HEIGHT*0.75f;
        float iconX = iconSize + GameConfig.GAME_AREA_PADDING/2f;
        float icon_X = game.width - GameConfig.GAME_AREA_PADDING*2 - GameConfig.SIDE_PANEL_WIDTH - iconX;
        float iconY = GameConfig.GAME_AREA_PADDING + GameConfig.TOP_BAR_HEIGHT/2f;
        // Draw the score
        game.textFont(Assets.textFont);
        game.textSize(iconSize*0.85f);
        game.fill(ColorPalette.TEXT_PRIMARY);
        game.imageMode(PConstants.CENTER);
        game.textAlign(PConstants.LEFT, PConstants.CENTER);
        game.image(Assets.appleImage, iconX, iconY, iconSize, iconSize);
        game.text(controller.getScoreText(), iconX + iconSize, iconY);
        // Draw the High score
        game.textAlign(PConstants.RIGHT, PConstants.CENTER);
        game.image(Assets.trophyImage, icon_X, iconY, iconSize, iconSize);
        game.text(controller.getHighScoreText(), icon_X - iconSize, iconY);
        // Draw the game title
        game.textFont(Assets.titleFont);
        game.textAlign(PConstants.CENTER, PConstants.CENTER);
        game.textSize(GameConfig.TOP_BAR_HEIGHT*0.35f);
        game.text("VIPER VISION", (GameConfig.GAME_AREA_PADDING/2f) + ((GameConfig.BOX_SIZE*GameConfig.GRID_WIDTH)/2f),
                GameConfig.GAME_AREA_PADDING + GameConfig.TOP_BAR_HEIGHT / 2f);

        game.popStyle();
    }
}
