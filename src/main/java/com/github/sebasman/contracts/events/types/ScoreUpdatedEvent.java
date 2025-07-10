package com.github.sebasman.contracts.events.types;

import com.github.sebasman.contracts.events.IGameEvent;

/**
 * Published whenever the score within the current GameSession is updated.
 * @param score The new score of the current game session.
 */
public record ScoreUpdatedEvent(int score) implements IGameEvent {}
