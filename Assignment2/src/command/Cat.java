package command;

import command.builder.CommandBuilder;
import driver.Directory;
import driver.Node;
import driver.File;
import util.IParameters;
import util.StringUtil;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Retrieve content of file(s)
 */
public class Cat extends Command {

  /**
   * Construct the cat command
   *
   * @param params Parameters
   */
  public Cat(IParameters params) {
    super(params);
  }

  /**
   * Constructor used for dependency injection
   *
   * @param builder Builder to use
   */
  private Cat(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create new instance with command builder
   *
   * @param builder Builder to use
   * @return New Cat instance
   */
  public static Cat createWithBuilder(CommandBuilder builder) {
    return new Cat(builder);
  }

  /**
   * Print the contents of file(s) in the shell.
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    String[] arguments = this.getParams().getArguments();
    Directory currentDir = this.getEnvironment().getCurrentDir();
    String result = "";
    getParams().assertArgumentNotEmpty();
    for (String arg : arguments) {
      Node node = currentDir.getNodeByPathString(arg);
      if (node != null && node.isFile() && !((File) node).isDangling()) {
        File file = (File) node;
        result += file.getContent() + "\n\n\n";
      } else {
        printToErr("The given path " + arg + " is not a file.");
      }
    }
    result = StringUtil.removeLastNChars(result, 3);
    printToOut(result);
    flushOutput();
  }

  /**
   * Get cat's manual.
   *
   * @return Manual for cat command.
   */
  public String getManual() {
    return "cat FILE1 [FILE2 ...]\n"
        + "====================================\n"
        + "Display the contents of given files.";
  }
}
