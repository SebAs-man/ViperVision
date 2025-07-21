package com.github.sebasman.view.render;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.AiPathUpdatedEvent;
import com.github.sebasman.contracts.events.types.GameSessionEndedEvent;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;
import processing.core.PApplet;

import java.util.Collections;
import java.util.List;

/**
 * A dedicated renderer to draw the path calculated by the AI for debugging purposes.
 * Subscribes to the AiPathUpdatedEvent event to receive the path to be drawn.
 */
public final class PathRenderer {
    // The singleton instance
    private static final PathRenderer INSTANCE = new PathRenderer();

    private List<Position> currentPath;

    /**
     * Private builder who subscribes to the necessary events.
     */
    private PathRenderer() {
        this.currentPath = Collections.emptyList();
        this.suscribeToEvents();
    }

    /**
     * Subscribes to events important to the class
     */
    private void suscribeToEvents(){
        EventManager eventManager = EventManager.getInstance();
        eventManager.subscribe(AiPathUpdatedEvent.class, this::onPathUpdate);
        eventManager.subscribe(GameSessionEndedEvent.class, event -> this.clearPath());
    }

    /**
     * Clears the current path so that it is not drawn in the next game.
     */
    private void clearPath(){
        this.currentPath = Collections.emptyList();
    }

    /**
     * The method that handles the event and updates the internal path.
     * @param event The event containing the new path.
     */
    private void onPathUpdate(AiPathUpdatedEvent event) {
        this.currentPath = event.path();
    }

    /**
     * Returns the single instance of the PathRenderer.
     * @return Singleton instance.
     */
    public static PathRenderer getInstance() {
        return INSTANCE;
    }

    /**
     * Draw the path stored in the game canvas.
     * @param context The PApplet context for drawing operations.
     */
    public void draw(PApplet context){
        if(this.currentPath == null || this.currentPath.isEmpty()) return;

        context.pushStyle();
        context.noFill();
        context.stroke(255, 255, 0, 150);
        context.strokeWeight(5);

        context.beginShape();
        for(Position pos: currentPath){
            float x = pos.x() * ModelConfig.BOX_SIZE + ModelConfig.BOX_SIZE / 2f;
            float y = pos.y() * ModelConfig.BOX_SIZE + ModelConfig.BOX_SIZE / 2f;
            context.vertex(x, y);
        }
        context.endShape();
        context.popStyle();
    }
}
