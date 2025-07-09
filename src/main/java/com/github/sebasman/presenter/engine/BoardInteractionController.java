package com.github.sebasman.presenter.engine;

import com.github.sebasman.contracts.events.EventManager;
import com.github.sebasman.contracts.events.types.NotificationRequestedEvent;
import com.github.sebasman.contracts.model.IBoardAPI;
import com.github.sebasman.contracts.model.IFoodAPI;
import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.ISnakeAPI;
import com.github.sebasman.contracts.view.IGameContext;
import com.github.sebasman.contracts.vo.NotificationType;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;
import com.github.sebasman.view.config.ViewConfig;

/**
 * Manages the user's interaction logic with the game board,
 * such as adding or removing obstacles. This class centralizes the logic so
 * that it can be reused by different states.
 */
public final class BoardInteractionController {
    private static final BoardInteractionController instance = new BoardInteractionController();

    /**
     * Private constructor to prevent instantiation.
     */
    private BoardInteractionController() {}

    /**
     * Returns the singleton instance of the BoardInteractionController.
     * @return the instance of BoardInteractionController.
     */
    public static BoardInteractionController getInstance() {
        return instance;
    }

    /**
     * Processes a right click on the board to add or remove an obstacle.
     * Contains all necessary validations.
     * @param game The game context to access the model and settings.
     * The X-coordinate of the mouse click.
     * @param mouseY The Y coordinate of the mouse click.
     */
    public void handleRightClick(IGameContext game, int mouseX, int mouseY){
        IGameSession session = game.getSession();
        if(session == null) return;

        //  Convert mouse pixel coordinates to grid coordinates
        int gridX = (mouseX - ViewConfig.GAME_AREA_PADDING) / ModelConfig.BOX_SIZE;
        int gridY = (mouseY - (ViewConfig.GAME_AREA_PADDING*2 + ViewConfig.TOP_BAR_HEIGHT)) / ModelConfig.BOX_SIZE;
        Position clickPos = new Position(gridX, gridY);
        // Check if the position is within the board limits
        if(gridX < 0 || gridX >= ModelConfig.GRID_WIDTH || gridY < 0 || gridY >= ModelConfig.GRID_HEIGHT) {
            EventManager.getInstance().notify(new NotificationRequestedEvent(
                    "It is not possible to add obstacles at that location.", NotificationType.WARNING, 6000));
            return;
        }
        // Check that no obstacle can be placed over the snake or food
        ISnakeAPI snake = session.getSnake();
        IFoodAPI food = session.getFood();
        if(snake.getBodySet().contains(clickPos) || food.getPosition().equals(clickPos)) {
            EventManager.getInstance().notify(new NotificationRequestedEvent(
                    "It is not possible to add obstacles on entities.", NotificationType.ERROR, 6000));
            return;
        }
        // Add or remove the obstacle
        IBoardAPI board = session.getBoard();
        if(board.isObstacle(clickPos)) {
            board.removeObstacle(clickPos);
        } else{
            board.addObstacle(clickPos);
        }
    }
}
