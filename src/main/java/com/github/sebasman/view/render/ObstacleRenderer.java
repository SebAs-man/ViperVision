package com.github.sebasman.view.render;

import com.github.sebasman.contracts.model.entities.IBoardAPI;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.view.config.ColorPalette;
import com.github.sebasman.view.config.ViewConfig;
import processing.core.PApplet;

import java.util.Set;

/**
 * Singleton Renderer specific for obstacles.
 * Knows how to draw the set of obstacles found in the board model.
 */
public final class ObstacleRenderer {
    private static final ObstacleRenderer INSTANCE = new ObstacleRenderer();

    /**
     * Private constructor to prevent instantiation.
     */
    private ObstacleRenderer() {}

    /**
     * Returns the singleton instance of ObstacleRenderer.
     * @return The singleton instance of ObstacleRenderer.
     */
    public static ObstacleRenderer getInstance() {
        return INSTANCE;
    }

    /**
     * Draw all obstacles on the board.
     * @param context The PApplet context for drawing operations.
     * @param board The model of the board containing the obstacles.
     */
    public void draw(PApplet context, IBoardAPI board){
        if(board == null) return;

        Set<Position> obstacles = board.getObstacles();
        if(obstacles.isEmpty()) return;

        context.pushMatrix();
        context.translate(ViewConfig.GAME_AREA_PADDING, ViewConfig.GAME_AREA_PADDING*2 + ViewConfig.TOP_BAR_HEIGHT);

        context.pushStyle();
        context.fill(ColorPalette.OBSTACLE_COLOR);
        context.noStroke();
        for(Position obstacle : obstacles){
            float x = obstacle.x() * ModelConfig.BOX_SIZE;
            float y = obstacle.y() * ModelConfig.BOX_SIZE;
            context.rect(x, y, ModelConfig.BOX_SIZE, ModelConfig.BOX_SIZE);
        }
        context.popStyle();

        context.popMatrix();
    }
}
