package driver.exceptions;

/**
 * Thrown when a given command is not found
 *
 */
public class CommandNotFoundException extends Exception {
  /**
   * Constructs command with a message
   */
  public CommandNotFoundException() {
    super("Command not found.");
  }

}
