package command;

import command.builder.CommandBuilder;
import util.IParameters;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Exit the shell.
 */
public class Exit extends Command {

  /**
   * Construct the exit command with parameters
   *
   * @param params Parameters
   */
  public Exit(IParameters params) {
    super(params);
  }

  /**
   * Constructor used to inject dependencies
   *
   * @param builder Builder to use
   */
  private Exit(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new exit instance with builder
   *
   * @param builder Builder to use
   * @return New exit instance
   */
  public static Exit createWithBuilder(CommandBuilder builder) {
    return new Exit(builder);
  }

  /**
   * Exit the Shell.
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    getParams().assertArgumentLength(0);
    this.getEnvironment().setExitFlagFalse();
  }

  /**
   * Get exit's manual.
   *
   * @return Manual for exit command.
   */
  public String getManual() {
    return "exit\n"
        + "====================================\n"
        + "Exit the shell.";
  }
}
