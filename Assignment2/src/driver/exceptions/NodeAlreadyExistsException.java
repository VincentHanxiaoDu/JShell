package driver.exceptions;

/**
 * Error thrown when a given Node already exists
 *
 */
public class NodeAlreadyExistsException extends Exception{
  /**
   * name of the node
   */
  private String nodeName;

  /**
   * Constructs the error with a message
   * @param nodeName String containing name of node
   */
  public NodeAlreadyExistsException(String nodeName) {
    super(nodeName + " already exists in the directory.");
    this.nodeName = nodeName;
  }
  /**
   * Return the name of the node trying to be created
   * @return nodeName String the invalid name of the node
   */
  public String getNodeName() {
    return this.nodeName;
  }
}
