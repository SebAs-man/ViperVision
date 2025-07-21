package com.github.sebasman.contracts.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Unit tests for the Position class.
 * This class verifies the core functionality of Position, such as
 * equality checks and basic property access.
 */
class PositionTest {
    @Test
    @DisplayName("Two positions with the same coordinates should be equal")
    void equalPositions() {
        // Arrange
        Position a = new Position(5, 1);
        Position b = new Position(5, 1);
        // Assert
        assertEquals(a, b, "Positions with identical x and y should be considered equal.");
    }

    @Test
    @DisplayName("Two positions with different coordinates should not be equal")
    void notEqualPositions() {
        Position a = new Position(5, 1);
        Position b = new Position(5, 2);
        // Asserts
        assertNotEquals(a, b, "Positions with different coordinates should not be equal.");
    }
}