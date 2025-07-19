package com.github.sebasman.model.effects;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.effects.ITimedEffect;

/**
 * An effect that makes the snake grow by a certain number of segments
 * over a period of time, one segment per tick.
 */
public final class DelayedGrowthEffect implements ITimedEffect {
    private int segmentLeft;
    private final int totalSegments;

    public DelayedGrowthEffect(int segments) {
        this.totalSegments = segments;
        this.segmentLeft = segments;
    }

    @Override
    public void apply(IGameSession session) {

    }

    @Override
    public void update(long elapsedTime) {
        /*
        if(segmentLeft > 0){
            session.getSnake().grow(1);
            segmentLeft--;
        }
         */
    }

    @Override
    public boolean isFinished() {
        return segmentLeft <= 0;
    }

    @Override
    public void onFinish(IGameSession session) {

    }
}
