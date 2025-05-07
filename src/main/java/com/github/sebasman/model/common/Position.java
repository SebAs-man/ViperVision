package com.github.sebasman.model.common;

public record Position(int x, int y) {
    public Position {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates must be non-negative");
        }
    }

    public Position add(Position other) {
        if (other == null) {
            throw new IllegalArgumentException("Position to add cannot be null");
        }
        return new Position(this.x + other.x, this.y + other.y);
    }
}
