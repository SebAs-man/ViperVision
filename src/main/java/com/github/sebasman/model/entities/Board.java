package com.github.sebasman.model.entities;

import com.github.sebasman.contracts.model.IGameSession;
import com.github.sebasman.contracts.model.entities.IBoardAPI;
import com.github.sebasman.contracts.model.entities.IFoodAPI;
import com.github.sebasman.contracts.vo.Position;
import com.github.sebasman.model.config.ModelConfig;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * It represents the game board and the state of its squares.
 * This Model class does not know how to draw itself; it only manages the data
 * of the entities it contains, such as obstacles.
 */
public final class Board implements IBoardAPI {
    private final Set<Position> obstacles;

    /**
     * Builds a board with empty obstacles
     */
    public Board() {
        this.obstacles = new HashSet<>();
    }

    @Override
    public boolean isObstacle(Position position) {
        return this.obstacles.contains(position);
    }

    @Override
    public void addObstacle(Position position) {
        this.obstacles.add(position);
    }

    @Override
    public void removeObstacle(Position position) {
        this.obstacles.remove(position);
    }

    @Override
    public void generateRandomObstacles(int amount, IGameSession session) {
        Set<Position> occupiedCells = new HashSet<>(session.getSnake().getBodySet());
        occupiedCells.addAll(session.getFoods().stream().map(IFoodAPI::getPosition).collect(Collectors.toSet()));
        occupiedCells.addAll(this.obstacles);

        int gridWidth = ModelConfig.GRID_WIDTH;
        int gridHeight = ModelConfig.GRID_HEIGHT;
        int totalSpots = gridWidth * gridHeight;
        Random random = new Random();

        for(int i = 0; i < amount; i++) {
            int availableSpots = totalSpots - occupiedCells.size();
            if(availableSpots <= 0) {
                System.out.println("No hay suficientes espacios disponibles para generar " + amount + " obstáculos. Se generaron " + i + " obstáculos.");
                return;
            }
            int targetEmptySpot = random.nextInt(availableSpots);
            int emptySpotCount = 0;
            Position newObstaclePosition = null;
            for (int y = 0; y < gridHeight; y++) {
                for (int x = 0; x < gridWidth; x++) {
                    Position currentPos = new Position(x, y);
                    if (!occupiedCells.contains(currentPos)) {
                        if (emptySpotCount == targetEmptySpot) {
                            newObstaclePosition = currentPos;
                            break;
                        }
                        emptySpotCount++;
                    }
                }
                if(newObstaclePosition != null) {
                    break;
                }
            }
            if(newObstaclePosition != null) {
                this.addObstacle(newObstaclePosition);
                occupiedCells.add(newObstaclePosition);
            }
        }
    }

    @Override
    public Set<Position> getObstacles() {
        return new HashSet<>(this.obstacles);
    }
}
