package com.github.sebasman.model.effects;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.effects.IEffect;

import java.util.Random;

/**
 * An effect that generates a random number of obstacles on the board.
 * @param min Defines the minimum value that can be generated from obstacles
 * @param max Defines the maximum value that can be generated from obstacles
 * @param session The game session in which the event is executed
 */
public record SpawnObstaclesEffect(int min, int max, IGameSession session) implements IEffect {
    @Override
    public void apply(IGameSession session) {
        int amount = new Random().nextInt(max - min + 1) + min;
        session.getBoard().generateRandomObstacles(amount, session);
    }
}
