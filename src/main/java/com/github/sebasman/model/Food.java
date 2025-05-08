package com.github.sebasman.model;

import com.github.sebasman.exceptions.EntityException;
import com.github.sebasman.model.common.EntityType;
import com.github.sebasman.model.common.Position;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a consumable food entity in the game. Food objects are used
 * to provide value or benefits to players or other entities when consumed.
 * They are placed at specific positions in the game world and can be activated
 * or deactivated depending on their current state.
 * This class extends the GameEntity class and inherits properties and
 * behavior common to all game entities, such as activation state
 * and type classification.
 */
public class Food extends GameEntity {
    // Fields
    private Position position;
    private final int value;

    /**
     * Constructs a new Food object that represents a consumable entity in the game.
     * Each food object is assigned a position and a value that determines its benefit
     * when consumed. The value must be greater than zero; otherwise, it defaults to 1.
     * @param position the position of the food in the game world; must not be null
     * @param value the value of the food; must be greater than 0. If a non-positive value is provided, it defaults to 1
     */
    public Food(Position position, int value) {
        super(EntityType.CONSUMABLE);
        setPosition(position);
        this.value = value > 0 ? value : 1; // Ensure Value is at least 1
    }

    // --- Getters ---

    /**
     * Retrieves the current position of the food entity in the game world.
     * The position represents where the entity is located on the game's 2D coordinate system.
     * @return the current position of the food entity
     */
    public Position getPosition() { return position; }

    /**
     * Retrieves the value of the food entity, which determines the benefit
     * provided when consumed in the game.
     * @return the value of the food entity
     */
    private int getValue() { return value; }

    // --- Setters ---

    /**
     * Updates the position of the entity to the specified value. The position represents
     * where the entity is located within the game world. This method ensures that the
     * position is not null before setting it.
     * @param position the new position to set for the entity; must not be null
     * @throws NullPointerException if the provided position is null
     */
    public void setPosition(Position position) {
        this.position = Objects.requireNonNull(position, "Position cannot be null");;
    }

    // --- Override Methods ---

    @Override
    public List<Position> getPositions() {
        return Collections.singletonList(position);
    }

    @Override
    public void setActive(boolean active) {
        if(active && position == null){
            throw new EntityException("Food cannot be activated without a valid position (internal state error).");
        }
        super.active = active;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Food food = (Food) obj;
        return active == food.active && Objects.equals(position, food.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, active);
    }

    @Override
    public String toString() {
        return "Food{" +
                "position=" + position +
                ", active=" + active +
                '}';
    }
}
