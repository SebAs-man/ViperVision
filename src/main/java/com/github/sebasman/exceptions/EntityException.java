package com.github.sebasman.exceptions;

/**
 * Represents a custom exception thrown when an entity's state
 * or properties do not meet certain expected conditions. This exception
 * is designed to be used in scenarios where specific entity-related errors
 * occur, and it provides several constructors for flexibility in creating
 * exception instances with different levels of detail.
 * It is common to use this exception to enforce rules or constraints
 * on entities, such as preventing invalid state changes or ensuring
 * required properties are not null.
 */
public class EntityException extends RuntimeException {
  /**
   * Constructs a new EntityException with no detailed message or cause.
   * This constructor creates an exception instance that represents a general
   * error related to an entity without providing additional context.
   */
  public EntityException() { super(); }

  /**
   * Constructs a new EntityException with the specified detailed message.
   * The message provides additional information about the exception,
   * which can be useful for debugging or logging purposes.
   * @param message the detailed message explaining the cause of the exception
   */
  public EntityException(String message) { super(message); }

  /**
   * Constructs a new EntityException with the specified detailed message and cause.
   * The message provides additional context for the exception, and the cause represents
   * the original exception that triggered this exception, allowing exception chaining.
   * @param message the detailed message explaining the reason for the exception
   * @param cause the underlying cause of the exception, which can provide additional context
   */
  public EntityException(String message, Throwable cause) { super(message, cause); }

  /**
   * Constructs a new EntityException with the specified cause.
   * This constructor allows for exception chaining, where the cause represents
   * the underlying exception that led to this exception being thrown.
   * @param cause the underlying cause of the exception (can be null)
   */
  public EntityException(Throwable cause) { super(cause); }
}
