package com.github.sebasman.model.common;


/**
 * Represents a position in a 2D coordinate system with non-negative x and y values.
 * This class is immutable and provides utility methods for working with positional data.
 */
public record Position(int x, int y) {
    /**
     * Constructs a Position object with the specified x and y coordinates.
     * Both coordinates must be non-negative; otherwise, an exception will be thrown.
     * @param x the x-coordinate of the position must be non-negative
     * @param y the y-coordinate of the position must be non-negative
     * @throws IllegalArgumentException if either x or y is negative
     */
    public Position {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates must be non-negative");
        }
    }

    /**
     * Adds the specified position to this position and returns the resulting position.
     * The addition is performed by summing the x and y coordinates of both positions.
     * @param other the position to be added; must not be null
     * @return a new Position representing the sum of this position and the specified position
     * @throws IllegalArgumentException if the specified position is null
     */
    public Position add(Position other) {
        if (other == null) {
            throw new IllegalArgumentException("Position to add cannot be null");
        }
        return new Position(this.x + other.x, this.y + other.y);
    }
}
