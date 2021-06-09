package driver.exceptions;

/**
 * Thrown when the type of nodes are not the same.
 */
public class NodeTypeAreNotTheSameException extends Exception{

  /**
   * Constructor with msg
   */
  public NodeTypeAreNotTheSameException() {
    super("The type of nodes are the same.");
  }
}
