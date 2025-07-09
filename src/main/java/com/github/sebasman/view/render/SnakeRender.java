package com.github.sebasman.view.render;

import com.github.sebasman.contracts.model.ISnakeAPI;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.view.config.ColorPalette;
import processing.core.PApplet;

/**
 * Snake-specific renderer. Knows how to draw an object
 * that implements ISnakeAPI on the screen.
 */
public final class SnakeRender {
    private static final SnakeRender INSTANCE = new SnakeRender();

    /**
     * Private constructor to prevent instantiation.
     */
    private SnakeRender() {}

    /**
     * Returns the singleton instance of SnakeRender.
     * @return The singleton instance of SnakeRender.
     */
    public static SnakeRender getInstance() {
        return INSTANCE;
    }

    /**
     * Draws the object instance
     * @param p The context in which the drawing is to be made
     * @param interpolation percentage of the interpolation moves the snake
     * @param snake The instance of the object to be drawn
     */
    public void draw(PApplet p, Float interpolation, ISnakeAPI snake) {
        p.pushStyle();
        p.noStroke();
        // Draw each body part independently
        // The loop now only draws the INSIDE body segments.
        for (int i = 1; i < snake.getBodySet().size() - 1; i++) {
            drawSegment(p, i, interpolation, snake);
        }
        if (!snake.getBodySet().isEmpty()) {
            drawEnd(p, 0, interpolation, true, snake); // Draw the head
        }
        if (snake.getBodySet().size() > 1) {
            drawEnd(p, snake.getBodySet().size() - 1, interpolation, false, snake); // Draw the tail
        }

        p.popStyle();
    }

    /**
     * Draws a segment of the snake's body.
     * @param p The PApplet instance used for drawing.
     * @param index The index of the segment to render.
     * @param interpolation The interpolation factor for smooth rendering.
     * @param snake The instance of the object to be drawn
     */
    private void drawSegment(PApplet p, int index, Float interpolation, ISnakeAPI snake) {
        Position currentPos = snake.getBody().get(index);
        Position previousPos = (index < snake.getPreviousBody().size()) ? snake.getPreviousBody().get(index) : currentPos;

        float renderX = PApplet.lerp(previousPos.x(), currentPos.x(), interpolation) * ModelConfig.BOX_SIZE;
        float renderY = PApplet.lerp(previousPos.y(), currentPos.y(), interpolation) * ModelConfig.BOX_SIZE;

        p.fill(ColorPalette.SNAKE_BODY);
        p.rect(renderX, renderY, ModelConfig.BOX_SIZE, ModelConfig.BOX_SIZE);
    }

    /**
     * Draws the head or tail of the snake with rounded corners based on its direction.
     * @param p The PApplet instance used for drawing.
     * @param index The index of the segment to render (0 for head, last for tail).
     * @param interpolation The interpolation factor for smooth rendering.
     * @param isHead True if drawing the head, false if drawing the tail.
     * @param snake The instance of the object to be drawn
     */
    private void drawEnd(PApplet p, int index, Float interpolation, boolean isHead, ISnakeAPI snake) {
        Position currentPos = snake.getBody().get(index);
        Position previousPos = (index < snake.getPreviousBody().size()) ? snake.getPreviousBody().get(index) : currentPos;

        float renderX = PApplet.lerp(previousPos.x(), currentPos.x(), interpolation) * ModelConfig.BOX_SIZE;
        float renderY = PApplet.lerp(previousPos.y(), currentPos.y(), interpolation) * ModelConfig.BOX_SIZE;

        // Determine the position of the adjacent segment to orient the corners correctly
        Position adjacentPos;
        if (isHead) {
            // The head is oriented with respect to the neck (the second segment).
            adjacentPos = (snake.getBody().size() > 1) ? snake.getBody().get(1) : currentPos;
        } else {
            // The tail is oriented with respect to the penultimate segment
            adjacentPos = snake.getBody().get(index - 1);
        }

        float tl = 0, tr = 0, br = 0, bl = 0;
        int cornerRadius = 24;

        // Determine which corners to round based on the direction of movement
        if (currentPos.x() > adjacentPos.x()) { // Moving right
            tr = cornerRadius; br = cornerRadius;
        } else if (currentPos.x() < adjacentPos.x()) { // Moving left
            tl = cornerRadius; bl = cornerRadius;
        } else if (currentPos.y() > adjacentPos.y()) { // Moving down
            bl = cornerRadius; br = cornerRadius;
        } else if (currentPos.y() < adjacentPos.y()) { // Moving up
            tl = cornerRadius; tr = cornerRadius;
        }

        p.fill(ColorPalette.SNAKE_BODY);
        p.rect(renderX, renderY, ModelConfig.BOX_SIZE, ModelConfig.BOX_SIZE, tl, tr, br, bl);

        // Draw eyes only if it's the head
        if (isHead) {
            float eyeSize = ModelConfig.BOX_SIZE*0.29f;
            float pupilSize = ModelConfig.BOX_SIZE*0.13f;
            float eyeOffsetX1 = 0, eyeOffsetY1 = 0; // eye 1
            float eyeOffsetX2 = 0, eyeOffsetY2 = 0; // eye 2
            float pupilOffsetX = 0, pupilOffsetY = 0;

            switch (snake.getDirection()) {
                case UP:
                    eyeOffsetX1 = ModelConfig.BOX_SIZE * 0.25f; eyeOffsetY1 = ModelConfig.BOX_SIZE * 0.35f;
                    eyeOffsetX2 = ModelConfig.BOX_SIZE * 0.75f; eyeOffsetY2 = ModelConfig.BOX_SIZE * 0.35f;
                    pupilOffsetY = -2; // Move pupils up
                    break;
                case DOWN:
                    eyeOffsetX1 = ModelConfig.BOX_SIZE * 0.25f; eyeOffsetY1 = ModelConfig.BOX_SIZE * 0.65f;
                    eyeOffsetX2 = ModelConfig.BOX_SIZE * 0.75f; eyeOffsetY2 = ModelConfig.BOX_SIZE * 0.65f;
                    pupilOffsetY = 2;  // Move pupils down
                    break;
                case LEFT:
                    eyeOffsetX1 = ModelConfig.BOX_SIZE * 0.35f; eyeOffsetY1 = ModelConfig.BOX_SIZE * 0.25f;
                    eyeOffsetX2 = ModelConfig.BOX_SIZE * 0.35f; eyeOffsetY2 = ModelConfig.BOX_SIZE * 0.75f;
                    pupilOffsetX = -2; // Move pupils left
                    break;
                case RIGHT:
                    eyeOffsetX1 = ModelConfig.BOX_SIZE * 0.65f; eyeOffsetY1 = ModelConfig.BOX_SIZE * 0.25f;
                    eyeOffsetX2 = ModelConfig.BOX_SIZE * 0.65f; eyeOffsetY2 = ModelConfig.BOX_SIZE * 0.75f;
                    pupilOffsetX = 2; // Move pupils right
                    break;
            }

            // Draw eyes (the white part)
            p.fill(ColorPalette.SNAKE_EYES);
            p.ellipse(renderX + eyeOffsetX1, renderY + eyeOffsetY1, eyeSize, eyeSize);
            p.ellipse(renderX + eyeOffsetX2, renderY + eyeOffsetY2, eyeSize, eyeSize);

            // Draw pupils (the black part)
            p.fill(0);
            p.ellipse(renderX + eyeOffsetX1 + pupilOffsetX, renderY + eyeOffsetY1 + pupilOffsetY, pupilSize, pupilSize);
            p.ellipse(renderX + eyeOffsetX2 + pupilOffsetX, renderY + eyeOffsetY2 + pupilOffsetY, pupilSize, pupilSize);
        }
    }
}
