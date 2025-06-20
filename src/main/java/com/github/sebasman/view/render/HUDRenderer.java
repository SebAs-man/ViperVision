package com.github.sebasman.view.render;

import com.github.sebasman.contracts.presenter.IHUDController;
import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.config.ColorPalette;
import com.github.sebasman.view.config.ViewConfig;
import processing.core.PApplet;
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
     * @param context The PApplet context to draw.
     * @param controller The HUD controller containing the text to display.
     */
    public void render(PApplet context, IHUDController controller){
        if (controller == null) return;

        context.pushStyle();

        float iconSize = ViewConfig.TOP_BAR_HEIGHT*0.75f;
        float iconX = iconSize + ViewConfig.GAME_AREA_PADDING/2f;
        float icon_X = context.width - ViewConfig.GAME_AREA_PADDING*2 - ViewConfig.SIDE_PANEL_WIDTH - iconX;
        float iconY = ViewConfig.GAME_AREA_PADDING + ViewConfig.TOP_BAR_HEIGHT/2f;
        // Draw the score
        context.textFont(Assets.textFont);
        context.textSize(iconSize*0.85f);
        context.fill(ColorPalette.TEXT_PRIMARY);
        context.imageMode(PConstants.CENTER);
        context.textAlign(PConstants.LEFT, PConstants.CENTER);
        context.image(Assets.appleImage, iconX, iconY, iconSize, iconSize);
        context.text(controller.getScoreText(), iconX + iconSize, iconY);
        // Draw the High score
        context.textAlign(PConstants.RIGHT, PConstants.CENTER);
        context.image(Assets.trophyImage, icon_X, iconY, iconSize, iconSize);
        context.text(controller.getHighScoreText(), icon_X - iconSize, iconY);
        // Draw the game title
        context.textFont(Assets.titleFont);
        context.textAlign(PConstants.CENTER, PConstants.CENTER);
        context.textSize(ViewConfig.TOP_BAR_HEIGHT*0.35f);
        context.text("VIPER VISION", (ViewConfig.GAME_AREA_PADDING/2f) + ((ModelConfig.BOX_SIZE * ModelConfig.GRID_WIDTH)/2f),
                ViewConfig.GAME_AREA_PADDING + ViewConfig.TOP_BAR_HEIGHT / 2f);

        context.popStyle();
    }
}
