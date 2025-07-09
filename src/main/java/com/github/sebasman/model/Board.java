package com.github.sebasman.model;

import com.github.sebasman.contracts.model.IBoardAPI;
import com.github.sebasman.contracts.vo.Position;

import java.util.HashSet;
import java.util.Set;

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
    public boolean addObstacle(Position position) {
        return this.obstacles.add(position);
    }

    @Override
    public boolean removeObstacle(Position position) {
        return this.obstacles.remove(position);
    }

    @Override
    public Set<Position> getObstacles() {
        return new HashSet<>(this.obstacles);
    }
}
