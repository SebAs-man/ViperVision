package com.github.sebasman.model.entities;

import com.github.sebasman.contracts.vo.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Board class.
 * This suite verifies the board's properties and its collision detection logic.
 */
@DisplayName("A Game Board")
class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        // Arrange: Create a new board with fixed dimensions for all tests.
        board = new Board();
    }

    @Nested
    @DisplayName("for position validation")
    class PositionValidation {
        @ParameterizedTest(name = "{2}") // Use the 3rd parameter (description) as the test name
        @CsvSource({
                "-1, 10,  'Collision on the left wall'",
                "30, 10,  'Collision on the right wall'",
                "15, -1,  'Collision on the top wall'",
                "15, 20,  'Collision on the bottom wall'"
        })
        @DisplayName("should detect collision when a position is outside the boundaries")
        void shouldDetectCollisionOnWalls(int x, int y, String description) {
            // Act & Assert
            assertTrue(board.isWallCollision(new Position(x, y)), description);
        }
    }

    @Nested
    @DisplayName("for wall collision detection")
    class ObstacleCollision {
        @ParameterizedTest(name = "{2}") // Use the 3rd parameter (description) as the test name
        @CsvSource({
                "5, 10,  'Collision on the obstacle (5,10)'",
                "4, 10,  'Collision on the obstacle (4,10)'",
                "15, 8,  'Collision on the obstacle (15, 8)'",
                "4, 3,  'Collision on the obstacle (4, 3)'"
        })
        @DisplayName("should detect collision when a position is the same as that of an obstacle")
        void shouldDetectCollisionOnObstacles(int x, int y, String description) {
            board.addObstacle(new Position(x, y));
            assertAll("Obstacles created",
                    () -> assertTrue(board.isObstacle(new Position(x, y)), description),
                    () -> assertFalse(board.isObstacle(new Position(y, x)), description));
        }
    }
}