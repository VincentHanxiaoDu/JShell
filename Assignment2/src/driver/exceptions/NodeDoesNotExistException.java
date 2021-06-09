package driver.exceptions;

/**
 * Thrown when a node that is trying to be accessed does not exist
 */
public class NodeDoesNotExistException extends Exception {

  /**
   * Constructor with msg
   * @param message Error message
   */
  public NodeDoesNotExistException(String message) {
    super(message);
  }
}
