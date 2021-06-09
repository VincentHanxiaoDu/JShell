package command;

import command.builder.CommandBuilder;
import driver.Directory;
import driver.File;
import driver.Node;
import driver.exceptions.NodeAlreadyExistsException;
import driver.exceptions.NodeTypeAreNotTheSameException;
import environment.Environment;
import util.IParameters;
import util.Traverse;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Move the item from OLDPATH to NEWPATH
 */
public class Mv extends Command {

  /**
   * Construct the command with parameters
   *
   * @param params Parameters
   */
  public Mv(IParameters params) {
    super(params);
  }

  /**
   * Constructor used to inject dependencies
   *
   * @param builder Builder to use
   */
  private Mv(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new instance with builder
   *
   * @param builder Builder to use
   * @return New mv instance
   */
  public static Mv createWithBuilder(CommandBuilder builder) {
    return new Mv(builder);
  }

  /**
   * Move the item from OLDPATH to NEWPATH
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    IParameters params = this.getParams();
    params.assertArgumentLength(2);
    Node oldPath = getEnvironment().getCurrentDir().
        getNodeByPathString(params.getArgument(0));
    Node newPath = getEnvironment().getCurrentDir().
        getNodeByPathString(params.getArgument(1));
    if (checkValidity(params.getArgument(0), oldPath,
        params.getArgument(1), newPath)) {
      if (!isNotDanglingFile(newPath)) {

        String name = getFileName(params.getArgument(1));
        if (isValidName(name)) {
          oldPath.setName(name);
          try {
            moveNodetoDir(oldPath, ((File)newPath).getProvisionalParent());
          } catch (NodeTypeAreNotTheSameException e){}
        } else {
          printToErr(name + ": Invalid node name.");
        }
      } else {
        if (oldPath.isFile() && newPath.isFile()) {
          moveFiletoFile(oldPath, newPath);
        } else {
          try {
            moveNodetoDir(oldPath, newPath);
          } catch (NodeTypeAreNotTheSameException e) {
            printToErr("The new path contains a different type of node "
                + "with the same name of old path");
          }

        }
      }
    }
  }


  private static boolean isNotDanglingFile(Node path) {
    if (path != null && path.isFile() && ((File) path).isDangling()) {
      return false;
    }
    return true;
  }

  /**
   * get the file name
   *
   * @param path the file path
   * @return the name of the file
   */
  private static String getFileName(String path) {
    return path.substring(path.lastIndexOf("/") + 1);
  }

  /**
   * checks if a file contains any illegal characters in its name
   *
   * @return boolean
   */
  private boolean isValidName(String name) {
    if (name.matches(".*[\\s/.!@#$%^&*(){}~|<>\\?].*")) {
      return false;
    }
    return true;
  }

  /**
   * Check the validity of oldPath and newPath
   * @param oldarg the string of oldPath
   * @param oldPath the oldPath Node
   * @param newarg the string of newPath
   * @param newPath the newPath Node
   * @return true if they are valid, false otherwise
   */
  private boolean checkValidity(String oldarg, Node oldPath,
      String newarg, Node newPath) {
    if (!isValidPath(oldPath)) {
      printToErr(oldarg + ": Invalid path.");
    } else if (newPath == null) {
      printToErr(newarg + ": Invalid path.");
    } else if (oldPath.isDirectory() && newPath.isFile() &&
        !((File)newPath).isDangling()) {
      printToErr("Cannot move a directory to a file.");
    } else if (oldPath == getEnvironment().getCurrentDir()
        .getNodeByPathString("/")) {
      printToErr("Cannot move the root directory");
    } else if (oldPath.isDirectory() && (oldPath == newPath || isDescentdant(
        oldPath, newPath, this.getEnvironment()))) {
      printToErr(oldarg + ": Cannot move directory into itself.");
    } else {
      return true;
    }
    return false;
  }

  private static boolean isValidPath(Node path) {
    return path.isDirectory() || (path.isFile() && !((File) path).isDangling());
  }

  /**
   * Move file to a file
   *
   * @param oldPath the moving file
   * @param newPath the target file
   */
  private static void moveFiletoFile(Node oldPath, Node newPath) {
    if (oldPath != newPath) {
      ((Directory) oldPath.getParent()).removeFile(oldPath);
      ((File) newPath).setContent(((File) oldPath).getContent());
    }
  }

  /**
   * Move Node to Directory
   *
   * @param oldPath the moving node
   * @param newPath the target directory
   */
  private static void moveNodetoDir(Node oldPath, Node newPath) throws
      NodeTypeAreNotTheSameException{
    Directory oldparent = (Directory) oldPath.getParent();
    try {
      if (oldparent != newPath) {
        ((Directory) newPath).addFile(oldPath);
        oldparent.removeFile(oldPath);
      }
    } catch (NodeAlreadyExistsException e) {
      Node newNode = ((Directory) newPath).getNodeByPathString(
          oldPath.getName());
      if (oldPath.getType().equals(newNode.getType())){
        ((Directory) newPath).removeFile(oldPath.getName());
        moveNodetoDir(oldPath, newPath);
      } else {
        throw new NodeTypeAreNotTheSameException();
      }
    }
  }

  /**
   * Return if newPath is a descentdant of oldPath
   *
   * @param oldPath the old path
   * @param newPath the new path
   * @param env the environment
   * @return boolean indicates if newPath is a descentdant of oldPath
   */
  private static boolean isDescentdant(Node oldPath,
      Node newPath, Environment env) {
    if (newPath.isFile() && ((File)newPath).isDangling()) {
      newPath = ((File)newPath).getProvisionalParent();
    }
    Traverse<Node> traverse = new Traverse<>(env);
    traverse.setStartPath(env, oldPath.getPath());
    for (Node i : traverse) {
      if (i == newPath) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get mv's manual.
   *
   * @return Manual for mv command.
   */
  public String getManual() {
    return "mv [OLDPATH] [NEWPATH]\n"
        + "====================================\n"
        + "move the item from OLDPATH to NEWPATH";
  }
}