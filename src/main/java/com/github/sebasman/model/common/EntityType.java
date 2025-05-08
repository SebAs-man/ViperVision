package com.github.sebasman.model.common;

/**
 * Defines the various types of entities that can exist in the game.
 * This enumeration is used to classify entities, determining their
 * behavior and interaction within the game world.
 * The available entity types include:
 * - PLAYER: Represents the player-controlled entity.
 * - ENEMY: Represents hostile entities that interact with the player.
 * - WALL: Represents static barriers or obstacles in the game.
 * - DOOR: Represents locations that can be used for transitions or entry.
 * - TRAP: Represents entities that harm or hinder the player or other entities.
 * - SNAKE: Represents a snake entity, commonly associated with a snake-like mechanic.
 * - CONSUMABLE: Represents items that can be consumed or collected for rewards or benefits.
 */
public enum EntityType {
    PLAYER,
    ENEMY,
    WALL,
    DOOR,
    TRAP,
    SNAKE,
    CONSUMABLE
}
