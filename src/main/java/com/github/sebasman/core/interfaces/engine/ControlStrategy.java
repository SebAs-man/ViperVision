package com.github.sebasman.core.interfaces.engine;

import com.github.sebasman.core.Game;
import com.github.sebasman.core.interfaces.ui.UiProvider;
import com.github.sebasman.core.interfaces.ui.UiComponent;
import com.github.sebasman.core.interfaces.model.SnakeAPI;

import java.util.List;

/**
 * Interface for the Strategy pattern. Defines a contract for all snake control strategies (human, AI, etc.).
 */
public interface ControlStrategy extends UiProvider {
    /**
     * Called on every frame of the game loop.
     * Ideal for AIs that need to recalculate their movement constantly.
     * @param game The game instance to access global data.
     * @param snake The snake that this strategy should control.
     */
    void update(Game game, SnakeAPI snake);

    /**
     * Called each time a key is pressed.
     * Ideal for human control, which is event driven.
     * @param game The game instance.
     * @param snake The snake to control.
     * @param keyCode The code of the key pressed.
     */
    void keyPressed(Game game, SnakeAPI snake, int keyCode);

    /**
     * Returns the components to be rendered in the side panel.
     * @return A list of UI components to be rendered in the side panel.
     */
    List<UiComponent> getSidePanelComponents();

    /**
     * Determines whether a keystroke is to be interpreted as an action.
     * To start the game from a ready state.
     * @param keyCode The key code.
     * @return true if the key should start the game, false otherwise.
     */
    boolean isGameStartAction(int keyCode);
}
