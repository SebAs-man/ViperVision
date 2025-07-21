package com.github.sebasman.model.entities;

import com.github.sebasman.contracts.model.entities.ISnakeAPI;
import com.github.sebasman.contracts.vo.Direction;
import com.github.sebasman.contracts.vo.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Snake class.
 * This suite verifies the snake's internal logic, including its initial state,
 * movement, growth, and self-collision detection, independent of the game board or rules.
 */
@DisplayName("A Snake")
class SnakeTest {
    private ISnakeAPI snake;

    @BeforeEach
    void setUp() {
        // Arrange: Create a new snake instance for each test to ensure isolation.
        snake = new Snake(new Position(10, 10), 3);
    }

    @Nested
    @DisplayName("When newly create")
    class WhenNew{
        @Test
        @DisplayName("Should have the correct initial position")
        void correctInitialPosition(){
            assertAll("Body segments is new",
                    () -> assertEquals(new Position(10, 10), snake.getHead(), "The head should be at the initial position."),
                    () -> assertEquals(new Position(8, 10), snake.getTail(), "The tail should be at the initial position."));
        }

        @Test
        @DisplayName("Should have the correct initial length")
        void correctInitialLength(){
            assertAll("Body segments is new",
                    () -> assertEquals(3, snake.getBody().size(), "The length should match the initial size."),
                    () -> assertEquals(3, snake.getPreviousBody().size(), "The length should match the initial size."),
                    () -> assertEquals(3, snake.getBodySet().size(), "The length should match the initial size."));
        }

        @Test
        @DisplayName("Should be facing right by default")
        void faceRightByDefault(){
            assertEquals(Direction.RIGHT, snake.getDirection(), "The initial direction should be RIGHT.");
        }
    }

    @Nested
    @DisplayName("When moving")
    class WhenMoving{
        @Test
        @DisplayName("Should move its head to the correct new position")
        void shouldMoveHeadToNewPosition(){
            // Act: Move the snake one step.
            snake.update();
            // Assert: The new head position should be one unit to the right.
            assertAll("Body segments after move",
                    () -> assertEquals(new Position(11, 10), snake.getHead(),  "Head should move one step in the current direction."),
                    () -> assertNotEquals(new Position(8, 10), snake.getTail(), "Tail should move one step in the current direction."),
                    () -> assertEquals(new Position(9, 10), snake.getTail(), "Tail should move one step in the current direction."));
        }

        @Test
        @DisplayName("should not change length while moving")
        void shouldNotChangeLengthWhenMoving() {
            // Act: Move the snake.
            snake.update();
            // Assert: The length should remain unchanged after a normal move.
            assertAll("Body segments after move",
                    () -> assertEquals(3, snake.getBody().size(), "The length should match the initial size."),
                    () -> assertEquals(3, snake.getPreviousBody().size(), "The length should match the initial size."),
                    () -> assertEquals(3, snake.getBodySet().size(), "The length should match the initial size."));
        }

        @Test
        @DisplayName("should correctly update all its body segments")
        void shouldUpdateAllBodySegments() {
            // Act: Move the snake.
            snake.update();
            // Assert: The new body should be shifted.
            // The new head is (11, 10).
            // The old head (10, 10) is now the first body segment.
            // The old first body segment (9, 10) is now the second.
            // The old tail segment is removed.
            List<Position> newBody = snake.getBody();
            List<Position> oldBody = snake.getPreviousBody();
            assertAll("Body Segments After Move",
                    () -> assertEquals(new Position(11, 10), newBody.getFirst(), "New head should be at (11,10)"),
                    () -> assertEquals(oldBody.getFirst(), newBody.get(1), "First body segment should be the old head"),
                    () -> assertEquals(oldBody.get(1), newBody.get(2), "Second body segment should be the old first segment"),
                    () -> assertNotEquals(oldBody.getLast(), newBody.getLast(), "The old tail segment should have been removed")
            );
        }
    }

    @Nested
    @DisplayName("when growing")
    class WhenGrowing {
        @Test
        @DisplayName("should increase its length by one")
        void shouldIncreaseLength() {
            // Act
            snake.grow(1);
            snake.update();
            // Assert
            assertAll("Body segments after growing",
                    () -> assertEquals(4, snake.getBody().size(), "Length should increase by 1 after growing."),
                    () -> assertEquals(3, snake.getPreviousBody().size(), "Length should increase by 1 after growing."),
                    () -> assertEquals(4, snake.getBodySet().size(), "Length should increase by 1 after growing."));
        }

        @Test
        @DisplayName("should keep the same tail position after growing and then moving")
        void shouldKeepTailWhenGrowing() {
            // Arrange
            Position tailBeforeGrow = snake.getTail();
            // Act: Grow and then move. The growth effect happens on the next move.
            snake.grow(1);
            snake.update();
            // Assert
            Position tailAfterMove = snake.getTail();
            assertAll("After grow and move",
                    () -> assertEquals(tailBeforeGrow, tailAfterMove, "The last segment (tail) should remain in place during a growth move."),
                    () -> assertEquals(4, snake.getBody().size(), "Length should be 4 after growing and moving."));
        }
    }

    @Nested
    @DisplayName("for self-collision")
    class SelfCollision {
        @Test
        @DisplayName("should detect collision when its head hits its body")
        void shouldDetectCollisionWhenHeadHitsBody() {
            // Arrange: Manually create a longer snake and turn it back on itself.
            snake.grow(1); // length 4: (10,10), (9,10), (8,10), (7,10)
            snake.grow(1); // length 5: (10,10), (9,10), (8,10), (7,10), (6,10)
            snake.update(); // head at (11,10)
            snake.bufferDirection(Direction.UP);
            snake.update(); // head at (11,11)
            snake.bufferDirection(Direction.LEFT);
            snake.update(); // head at (10,11)
            snake.bufferDirection(Direction.DOWN);
            // Act: The next move will cause a collision. Head will move to (10,10)
            snake.update();
            // Assert
            assertTrue(snake.isSelfColliding(), "Should detect collision when head occupies a body segment's position.");
        }
    }
}