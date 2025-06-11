package com.github.sebasman.core.vo;

/**
 * Represents the four cardinal directions: UP, DOWN, LEFT, and RIGHT.
 * Each direction is associated with a change in x and y coordinates, which
 * can be used to compute relative movement on a 2D grid.
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    // Fields to store the change in x and y coordinates for each direction
    private final int dx;
    private final int dy;

    /**
     * Constructs a Direction with the specified changes in the x and y coordinates.
     * @param dx the change in the x-coordinate associated with this direction
     * @param dy the change in the y-coordinate associated with this direction
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // --- Getters ---

    /**
     * Returns the change in the x-coordinate associated with this direction.
     * This value determines the horizontal movement on a 2D grid.
     * @return the change in the x-coordinates as an integer
     */
    public int getDx() { return dx; }

    /**
     * Returns the change in the y-coordinate associated with this direction.
     * This value determines the vertical movement on a 2D grid.
     * @return the change in the y-coordinates as an integer
     */
    public int getDy() { return dy; }

    // --- Utility Methods ---

    /**
     * Returns the opposite direction to this direction.
     * For example, the opposite of UP is DOWN, and the opposite of LEFT is RIGHT.
     * @return The opposite direction to this direction
     */
    public Direction opposite() {
        return switch (this){
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
