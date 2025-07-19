package com.github.sebasman.presenter.states.snake;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.IExpirable;
import com.github.sebasman.contracts.model.effects.IEffect;
import com.github.sebasman.contracts.model.entities.ISnakeAPI;
import com.github.sebasman.contracts.presenter.ISnakeState;
import com.github.sebasman.contracts.view.ISnakeRenderStyle;
import com.github.sebasman.model.effects.GrowthEffect;
import com.github.sebasman.model.effects.SpawnObstaclesEffect;
import com.github.sebasman.model.effects.SpeedEffect;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Defines the state of the snake when it enters madness,
 * this allows it to bypass all possible logic in the game.
 */
public final class InvulnerableSnakeState implements ISnakeState, IExpirable {
    private static final long BLINK_THRESHOLD_MS = 3000; // 3 seconds
    private long timeLeft;

    /**
     * Creates a new instance of the state with a predetermined duration
     * @param durationMillis The duration of this state.
     */
    public InvulnerableSnakeState(long durationMillis){
        this.timeLeft = durationMillis;
    }

    @Override
    public void handleCollision(ISnakeAPI snake, IGameSession session) {
        // In the invulnerable state, collisions are ignored.
    }

    @Override
    public ISnakeRenderStyle getRenderStyle() {
        return this::calculateRainbowColor;
    }

    @Override
    public IEffect processEffect(IEffect effect) {
        if(effect instanceof SpeedEffect || effect instanceof SpawnObstaclesEffect ||
        (effect instanceof GrowthEffect && ((GrowthEffect)effect).amount() < 0)){
            System.out.println("DEBUG: Efecto ignorado por invulnerabilidad");
            return null;
        }
        return effect;
    }

    @Override
    public void update(long elapsedTime) {
        this.timeLeft -= elapsedTime;
    }

    @Override
    public boolean isExpired() {
        return this.timeLeft <= 0;
    }

    /**
     * Returns the current color for rendering, creating a rainbow effect.
     * That flashes when the effect is about to end.
     * @param context The PApplet to access color functions.
     * @return The color (int) to use to draw the snake.
     */
    private int calculateRainbowColor(PApplet context){
        context.colorMode(PConstants.HSB, 360, 100, 100);
        float hue = (System.currentTimeMillis() / 10f) % 360;

        // ---Blink logic ---

        if (timeLeft < BLINK_THRESHOLD_MS) {
            boolean isVisible = (System.currentTimeMillis() / 150) % 2 == 0;
            if (!isVisible) {
                return context.color(hue, 100, 0);
            }
        }

        int color = context.color(hue, 90, 90);
        context.colorMode(PConstants.RGB, 255);
        return color;
    }
}
