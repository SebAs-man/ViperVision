package com.github.sebasman.view;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.FrameUpdatedEvent;
import com.github.sebasman.contracts.events.types.GameSessionEndedEvent;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.IUserProfile;
import com.github.sebasman.contracts.presenter.IState;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.model.GameSession;
import com.github.sebasman.model.UserProfile;
import com.github.sebasman.view.assets.Assets;
import com.github.sebasman.view.audio.SoundManager;
import com.github.sebasman.view.config.ColorPalette;
import com.github.sebasman.view.config.ViewConfig;
import com.github.sebasman.view.render.BoardRender;
import com.github.sebasman.view.render.GameUiStatic;
import com.github.sebasman.view.render.NotificationRenderer;
import processing.core.PApplet;

import java.util.Objects;
import java.util.Stack;

/**
 * The root class of the application. It acts as the main CONTEXT and the root VIEW.
 * It owns the global state of the application (profile, current session) and the state manager (Presenter),
 * but delegates all logic to the current active state.
 */
public class GameView extends PApplet implements IGameContext {
    // Current game profile
    private final IUserProfile profile;
    // Current game session
    private IGameSession session;
    // The stack of game states, allowing for state management
    private final Stack<IState> states;

    /**
     * Constructor for the Game class.
     * @param initialState The initial state of the game to start with.
     */
    public GameView(IState initialState) {
        this.profile = new UserProfile();
        this.states = new Stack<>();
        this.pushState(Objects.requireNonNull(initialState, "Initial state cannot be null."));
    }

    @Override
    public void settings() {
        // Set the size of the window based on the grid dimensions and box size
        super.size(ViewConfig.WINDOW_WIDTH, ViewConfig.WINDOW_HEIGHT);
    }

    @Override
    public void setup() {
        // Set the frame rate
        super.frameRate(60);
        // Set the alignment for the game
        super.textAlign(CENTER, CENTER);
        // Load assets such as images and fonts
        Assets.load(this);
        SoundManager.load(this);
        ColorPalette.load(this);
        GameUiStatic.getInstance().initialize(this);
        // Initialize the static board
        BoardRender.getInstance().initialize(this);
    }

    @Override
    public void draw() {
        IState currentState = this.peekState();
        if(currentState == null) {
            throw new IllegalStateException(
                    "The current state of the game cannot be null and void."
            );
        }
        // In each frame, we first update all the logic.
        EventManager.getInstance().notify(new FrameUpdatedEvent());
        currentState.update(this);
        // Then, we draw the result.
        currentState.draw(this);
        NotificationRenderer.getInstance().draw(this);
    }

    @Override
    public void keyPressed() {
        IState currentState = this.peekState();
        if(currentState == null) {
            throw new IllegalStateException(
                    "The current state of the game cannot be null and void."
            );
        }
        currentState.keyPressed(this, keyCode);
    }

    @Override
    public void mousePressed() {
        IState currentState = this.peekState();
        if(currentState == null) {
            throw new IllegalStateException(
                    "The current state of the game cannot be null and void."
            );
        }
        currentState.mousePressed(this, this.mouseX, this.mouseY);
    }

    @Override
    public IState peekState() {
        if (this.states.isEmpty()) {
            return null;
        }
        return this.states.peek();
    }

    @Override
    public void pushState(IState state) {
        Objects.requireNonNull(state, "State cannot be null");
        this.states.push(state);
        state.onEnter(this);
    }

    @Override
    public void changeState(IState newState) {
        this.popState(); // Remove the current state
        this.pushState(newState);
    }

    @Override
    public void popState() {
        if (!states.isEmpty()) {
            this.peekState().onExit(this);
            states.pop();
        }
    }

    @Override
    public void startNewSession(){
        this.session = new GameSession();
    }

    @Override
    public void endCurrentSession() {
        this.session = null;
        // All systems are notified that the game has ended.
        EventManager.getInstance().notify(new GameSessionEndedEvent());
    }

    // --- Getters ---

    @Override
    public IUserProfile getProfile() {
        return profile;
    }

    @Override
    public IGameSession getSession() {
        return session;
    }

    @Override
    public PApplet getRenderer() {
        // Returns a reference to itself, since it is the instance of PApplet.
        return this;
    }
}
