package command;

import command.builder.CommandBuilder;
import driver.Directory;
import driver.Node;
import util.IParameters;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Change working directory
 */
public class Cd extends Command {

  /**
   * Construct the command with parameters
   *
   * @param params Parameters
   */
  public Cd(IParameters params) {
    super(params);
  }

  /**
   * Constructor used for dependency injection
   *
   * @param builder Builder to use
   */
  private Cd(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new instance with builder
   *
   * @param builder Builder to use
   * @return New cd instance
   */
  public static Cd createWithBuilder(CommandBuilder builder) {
    return new Cd(builder);
  }

  /**
   * Change working directory
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    getParams().assertArgumentLength(1);
    // get the node looking for
    Node node = getEnvironment().getCurrentDir().
        getNodeByPathString(getParams().getArgument(0));
    // check if the target path is valid and the if type of
    // the node is Directory
    if ((node != null) && (node.isDirectory())) {
      this.getEnvironment().setCurrentDir((Directory) node);
    } else {
      printToErr(getParams().getArgument(0) +
          ": No such directory");
    }
  }

  /**
   * Get cd's manual.
   *
   * @return Manual for cd command.
   */
  public String getManual() {
    return "cd DIR\n"
        + "====================================\n"
        + "Change directory to given directory, which may\n"
        + "be relative to current directory or full path.";
  }
}
