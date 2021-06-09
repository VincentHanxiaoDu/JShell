package command;

import command.builder.CommandBuilder;
import util.IParameters;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Print a string
 */
public class Echo extends Command {

  /**
   * Construct echo command with parameters
   *
   * @param params Parameters
   */
  public Echo(IParameters params) {
    super(params);
  }

  /**
   * Constructor used to inject all dependencies
   *
   * @param builder Command builder
   */
  private Echo(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Factory method to create echo command with a builder
   *
   * @param builder Builder to use
   * @return New echo instance
   */
  public static Echo createWithBuilder(CommandBuilder builder) {
    return new Echo(builder);
  }

  /**
   * Check if a string argument is valid
   * Wrapped in quotes, and does not have double quote within the quotes
   *
   * @param value String to check
   * @return boolean
   */
  private boolean isValidString(String value) {
    if (value.length() < 2) {
      return false;
    }
    if (value.charAt(0) != '"' || value.charAt(value.length() - 1) != '"') {
      return false;
    }
    for (int i = 1; i < value.length() - 1; i++) {
      if (value.charAt(i) == '"') {
        return false;
      }
    }
    return true;
  }

  /**
   * Get the actual string to print from argument (strip the quotes)
   *
   * @return The actual string to print
   */
  private String getActualStringFromArgument() {
    return getParams().getArguments()[0].substring(1,
            getParams().getArguments()[0].length() - 1);
  }

  /**
   * Run the echo command
   * echo "STRING" [ >> or > ]? [ FILENAME ]?
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    getParams().assertArgumentLength(1);
    if (!isValidString(getParams().getArgument(0))) {
      printToErr("Invalid string to print.");
    } else {
      printToOut(getActualStringFromArgument());
      flushOutput();
    }
  }

  /**
   * Get echo's manual.
   *
   * @return Manual for echo command.
   */
  @Override
  public String getManual() {
    return "echo \"STRING\"\n"
            + "echo \"STRING\" > FILENAME\n"
            + "echo \"STRING\" >> FILENAME\n"
            + "====================================\n"
            + "Print given string on the shell if no file\n"
            + "is provided. Otherwise erase the file or create new\n"
            + "file to store the given string if echo String > file\n"
            + "is used. If echo String >> file is used, it will append\n"
            + "the given string into file instead of overwrites.";
  }
}
