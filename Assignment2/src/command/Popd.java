package command;

import command.builder.CommandBuilder;
import driver.Directory;
import environment.Environment;
import util.IParameters;
import driver.Node;

/**
 * Remove the top entry from the directory stack, and cd into it.
 */
public class Popd extends Command {

  /**
   * Default constructor
   *
   * @param params Parameters to use
   */
  public Popd(IParameters params) {
    super(params);
  }

  /**
   * Constructor used to inject dependencies
   *
   * @param builder Builder to use
   */
  private Popd(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new popd instance with builder
   *
   * @param builder Builder to use
   * @return The new popd instance
   */
  public static Popd createWithBuilder(CommandBuilder builder) {
    return new Popd(builder);
  }

  /**
   * Get popd's manual.
   *
   * @return Manual for popd command.
   */
  public String getManual() {
    return "popd\n"
        + "====================================\n"
        + "Remove the top entry from the directory stack, and cd into it";
  }

  /**
   * Remove the top entry from the directory stack, and cd into it.
   */
  @Override
  protected void execute() {
    Environment env = this.getEnvironment();
    // pop the directory on the top of the dir Stack
    String targetpath = env.getDirStack().pop();
    // cd into the target dir
    if (targetpath != null) {
      Node node = env.getCurrentDir().getNodeByPathString(targetpath);
      this.getEnvironment().setCurrentDir((Directory) node);
    } else {
      printToErr("The directory stack is empty.");
    }
  }
}
