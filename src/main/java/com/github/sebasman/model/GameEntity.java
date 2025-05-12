package com.github.sebasman.model;

import com.github.sebasman.exceptions.EntityException;
import com.github.sebasman.model.common.EntityType;
import com.github.sebasman.model.common.Position;

import java.util.List;
import java.util.Objects;

/**
 * Represents an abstract base class for all entities in the game. Game entities are distinct
 * objects within the game world, each with specific behavior and characteristics defined by
 * subclasses. This class provides a common structure for all game entities, including a type
 * identifier and an active status.
 * Subclasses must implement the abstract methods to define specific behavior and attributes
 * for their respective entity types.
 */
public abstract class GameEntity {
    // Fields common to all game entities
    protected boolean active;
    protected final EntityType type;

    /**
     * Constructs a new GameEntity with a specified entity type. The type is used to
     * classify the entity and determine its behavior and interaction within the game world.
     * This constructor is protected and is intended to be used by subclasses to initialize
     * the common properties of all game entities.
     * @param type the type of the entity, specified as an instance of {@code EntityType};
     *             must not be null
     * @throws NullPointerException if the provided entity type is null
     */
    protected GameEntity(EntityType type) {
        this.type = Objects.requireNonNull(type, "Entity type cannot be null");
        this.active = true;
    }

    // --- Getters ---

    /**
     * Determines whether the entity is active in the game.
     * Active entities are those currently participating in the game world,
     * whereas inactive entities are not.
     * @return {@code true} if the entity is active, otherwise {@code false}
     */
    public boolean isActive() { return active; }

    /**
     * Retrieves the type of the entity, which classifies its role or behavior
     * in the game world. The entity type is determined during construction and
     * remains constant throughout the entity's lifecycle.
     * @return the type of the entity as an {@code EntityType} enumeration constant
     */
    public EntityType getType() { return type; }

    // --- Abstract Methods ---

    /**
     * Sets the active state of the entity. The active state determines
     * whether the entity is currently participating in the game.
     * Subclasses may impose additional constraints or logic based on
     * their specific behavior when activating or deactivating the entity.
     * @param active a boolean indicating the desired active state of the entity;
     *               {@code true} to activate the entity, {@code false} to deactivate it
     * @throws EntityException if activation is invalid based on the specific
     *                         requirements or internal state of the subclass
     */
    public abstract void setActive(boolean active);

    /**
     * Retrieves a list of positions associated with the current game entity.
     * The positions represent the locations in the game world relevant to the entity.
     * For example, a snake entity might return the positions of its body segments,
     * while a food entity might return a single position corresponding to its location.
     * @return a list of {@code Position} objects representing the positions of the entity
     */
    public abstract List<Position> getPositions();

    // --- Override Methods ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEntity that = (GameEntity) o;
        return active == that.active && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(active, type);
    }
}
