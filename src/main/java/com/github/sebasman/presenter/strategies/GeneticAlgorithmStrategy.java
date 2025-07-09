package com.github.sebasman.presenter.strategies;

import com.github.sebasman.contracts.configuration.IConfigParameter;
import com.github.sebasman.contracts.model.ISnakeAPI;
import com.github.sebasman.contracts.presenter.IControlStrategy;
import com.github.sebasman.contracts.presenter.IUiProvider;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.model.config.ModelConfig;

import java.util.List;

public final class GeneticAlgorithmStrategy implements IControlStrategy, IUiProvider {
    public GeneticAlgorithmStrategy(){
        System.out.println("Estrategia!");
    }

    @Override
    public void subscribeToEvents() {

    }

    @Override
    public void unsubscribeFromEvents() {

    }

    @Override
    public void update(IGameContext game, ISnakeAPI snake) {

    }

    @Override
    public void keyPressed(IGameContext game, ISnakeAPI snake, int keyCode) {
        // The AI doesn't respond to the keyboard, so this method is empty.
    }

    @Override
    public boolean isGameStartAction(int keyCode) {
        // The AI does not start the game with a key, but with a UI button.
        return false;
    }

    @Override
    public float getDesiredSpeed() {
        return ModelConfig.STARTING_FRAME_RATE;
    }

    @Override
    public List<IConfigParameter> getConfigurationParameters() {
        return List.of();
    }
}
