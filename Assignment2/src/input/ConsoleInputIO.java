package input;

import environment.Environment;
import java.io.InputStream;
import java.util.Scanner;

/**
 * The input for commands entered by the user into the console
 *
 */
public class ConsoleInputIO {

  /**
   * The input scanner
   */
  private Scanner scanner;

  /**
   * Create a new input IO with stdin stream
   */
  public ConsoleInputIO() {
    this.scanner = new Scanner(System.in);
  }

  /**
   * Create a new input IO with specific stream
   * @param in Input stream to use
   */
  public ConsoleInputIO(InputStream in) {
    this.scanner = new Scanner(in);
  }

  /**
   * Get current environment
   * @return Current environment
   */
  private Environment getEnvironment() {
    return Environment.createSingleInstance();
  }

  /**
   * Get a stdin scanner
   * @return Stdin scanner
   */
  private Scanner getScanner() {
    return this.scanner;
  }

  /**
   * Read one line from stdin and pushes it to command stack
   * @return Line read
   */
  public String read() {
    String in = this.getScanner().nextLine();
    // push command into command stack
    this.getEnvironment().getCommandStack().push(in);
    return in;
  }
}
