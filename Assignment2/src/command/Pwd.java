package command;

import command.builder.CommandBuilder;
import util.IParameters;
import util.Parameters;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Show current working directory
 */
public class Pwd extends Command {

  /**
   * Construct Pwd command with parameters
   *
   * @param params Parameters
   */
  public Pwd(IParameters params) {
    super(params);
  }

  /**
   * Constructor used to inject dependencies
   *
   * @param builder Builder to use
   */
  private Pwd(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new instance with builder
   *
   * @param builder Builder to use
   * @return New pwd instance
   */
  public static Pwd createWithBuilder(CommandBuilder builder) {
    return new Pwd(builder);
  }

  /**
   * Run the pwd command
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    getParams().assertArgumentLength(0);
    String path = this.getEnvironment().getCurrentDir().getPath();
    if (path.equals("/")) {
      printToOut(path);
    } else {
      printToOut(path.substring(0, path.length() - 1));
    }
    flushOutput();
  }

  /**
   * Get manual for pwd command.
   *
   * @return Manual for pwd.
   */
  @Override
  public String getManual() {
    return "pwd\n"
        + "====================================\n"
        + "Print current working directory (including the whole path).";
  }
}
