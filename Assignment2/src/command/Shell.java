package command;

import input.ConsoleInputIO;
import util.IParameters;
import util.Parser;

/**
 * continuously prompts user for input and runs command entered
 */
public class Shell extends Command {

  /**
   * handles user input
   */
  private ConsoleInputIO consoleInputIO;

  /**
   * The default constructor with env and args
   *
   * @param params Command arguments
   */
  public Shell(IParameters params) {
    super(params);
    this.initializeConsoleInputIO();
  }

  /**
   * Overload a empty constructor to create a default shell
   */
  private Shell() {
    super(Parser.parse("shell"));
    this.initializeConsoleInputIO();
  }

  /**
   * Create a new shell with new Environment and a root directory
   *
   * @return New shell
   */
  public static Shell createShellWithNewEnvironment() {
    return new Shell();
  }

  /**
   * Initialize a default console input IO stream
   */
  private void initializeConsoleInputIO() {
    this.consoleInputIO = new ConsoleInputIO();
  }

  /**
   * Get current input IO stream
   *
   * @return Console input IO
   */
  private ConsoleInputIO getConsoleInputIO() {
    return this.consoleInputIO;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  /**
   * Run the shell program
   */
  protected void execute() {
    
    // Keep prompting for input
    while (this.getEnvironment().getExitFlag()) {
      // print the path of current directory
      String path = this.getEnvironment().getCurrentDir().getPath();
      if (!path.equals("/")) {
        path = path.substring(0, path.length() - 1);
      }
      System.out.print(path + "$ ");
      String in = this.getConsoleInputIO().read();
      CommandHandler.callCommand(in);
    }
  }
}
