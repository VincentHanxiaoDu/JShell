package driver.exceptions;

/**
 * Thrown when a file which is to be created has an invalid character in it
 *
 */
public class InvalidFileNameException extends Exception{
  /**
   * Constructs the error with message
   */
  public InvalidFileNameException() {
    super("The file name of the url is invalid.");
  }
}
