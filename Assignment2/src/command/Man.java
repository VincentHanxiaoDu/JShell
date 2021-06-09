package command;

import command.builder.CommandBuilder;
import driver.exceptions.CommandNotFoundException;
import util.IParameters;
import util.Parameters;
import util.Parser;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Man command Once ran, will show the documentation for given command
 */
public class Man extends Command {

  /**
   * Construct the man command
   *
   * @param params Command parameters
   */
  public Man(IParameters params) {
    super(params);
  }

  /**
   * Used to inject dependencies
   *
   * @param builder Builder to use
   */
  private Man(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new instance with builder
   *
   * @param builder Builder to use
   * @return New man instance
   */
  public static Man createWithBuilder(CommandBuilder builder) {
    return new Man(builder);
  }

  /**
   * Get man's manual.
   *
   * @return Manual for man command.
   */
  public String getManual() {
    return "man CMD\n"
        + "====================================\n"
        + "Print the documentation for given command.";
  }

  /**
   * Print the manual of the given command on the screen.
   */
  protected void execute() throws InvalidNumberOfArgumentsException {
    // Basic argument assertions
    getParams().assertArgumentLength(1);
    // Construct a new command instance
    try {
      Command command = CommandSet.getCommand(
          Parser.parse(getParams().getArgument(0)));
      printToOut(command.getManual());
      flushOutput();
    } catch (CommandNotFoundException e) {
      printToErr(getParams().getArgument(0)
          + " is not a valid command.");
    }
  }
}
