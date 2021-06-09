package command;

import command.builder.CommandBuilder;
import util.IParameters;
import driver.Directory;
import driver.Node;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Saves the current working directory by pushing onto directory stack and then
 * changes the new current working directory to the given directory.
 */
public class Pushd extends Command {

  /**
   * Default constructor
   *
   * @param params Parameters to use
   */
  public Pushd(IParameters params) {
    super(params);
  }

  /**
   * Constructor used to inject dependencies
   *
   * @param builder Builder to use
   */
  private Pushd(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new instance with builder
   *
   * @param builder Builder to use
   * @return New pushd instance
   */
  public static Pushd createWithBuilder(CommandBuilder builder) {
    return new Pushd(builder);
  }

  /**
   * Get pushd's manual.
   *
   * @return Manual for pushd command.
   */
  public String getManual() {
    return "pushd DIR\n"
        + "====================================\n"
        + "Saves the current working directory by pushing\n"
        + "onto directory stack and then changes the new\n"
        + "current working directory to the given directory.";
  }

  /**
   * Saves the current working directory by pushing onto directory stack and
   * then changes the new current working directory to the given directory.
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    getParams().assertArgumentLength(1);
    // get the node looking for
    Node node = getEnvironment().getCurrentDir().getNodeByPathString(
        getParams().getArgument(0));
    // check if the target path is valid and the if type of the node
    // is Directory
    if ((node != null) && (node.isDirectory())) {
      getEnvironment().getDirStack().push(
          getEnvironment().getCurrentDir().getPath());
      this.getEnvironment().setCurrentDir((Directory) node);
    } else {
      printToErr(getParams().getArgument(0) +
          ": No such file or directory");
    }
  }
}
