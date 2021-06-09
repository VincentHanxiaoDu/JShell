package command;

import command.builder.CommandBuilder;
import driver.Directory;
import driver.File;
import driver.Node;
import driver.exceptions.NodeAlreadyExistsException;

import driver.exceptions.NodeTypeAreNotTheSameException;
import environment.Environment;
import java.util.ArrayList;

import util.IParameters;
import util.Traverse;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Copy the item from OLDPATH and paste it to NEWPATH
 */
public class Cp extends Command {

  /**
   * Construct the command with parameters
   *
   * @param params Parameters
   */
  public Cp(IParameters params) {
    super(params);
  }

  /**
   * Constructor used to inject dependencies
   *
   * @param builder Builder to use
   */
  private Cp(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Factory method to create new command with builder
   *
   * @param builder Builder to use
   * @return New Cp instance
   */
  public static Cp createWithBuilder(CommandBuilder builder) {
    return new Cp(builder);
  }

  /**
   * Copy the item from OLDPATH and paste it to NEWPATH
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
        Node copy = getNodeCopy(oldPath);
        String name = getFileName(params.getArgument(1));
        if (isValidName(name)) {
          copy.setName(name);
          try {
            pasteNodetoDir(copy, ((File)newPath).getProvisionalParent());
          }catch (NodeTypeAreNotTheSameException e){}
        } else {
          printToErr(name + ": Invalid node name.");
        }
      } else {
        if (oldPath.isFile() && newPath.isFile()) {
          ((File) newPath).setContent(((File) oldPath).getContent());
        } else {
          Node copy = getNodeCopy(oldPath);
          try {
            pasteNodetoDir(copy, newPath);
          } catch (NodeTypeAreNotTheSameException e) {
            printToErr("The new path contains a different type of node "
                + "with the same name of old path");
          }
        }
      }
    }
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
   * Check the validity of paths for copying
   *
   * @param oldarg the old path string
   * @param oldPath the old node
   * @param newarg the new path string
   * @param newPath the new node
   * @return true if both paths are valid, false otherwise
   */
  private boolean checkValidity(String oldarg, Node oldPath,
      String newarg, Node newPath) {
    if (!isValidPath(oldPath)) {
      printToErr(oldarg + ": Invalid path.");
    } else if (newPath == null) {
      printToErr(newarg + ": Invalid path.");
    } else if (oldPath.isDirectory() && newPath.isFile() &&
        !((File)newPath).isDangling()) {
      printToErr("Cannot copy a directory to a file.");
    } else if (oldPath == getEnvironment().getCurrentDir()
        .getNodeByPathString("/")) {
      printToErr("Cannot copy the root directory");
    } else if (oldPath.isDirectory() && (oldPath == newPath || isDescentdant(
        oldPath, newPath, this.getEnvironment()))) {
      printToErr(oldarg + ": Cannot copy directory into itself.");
    } else {
      return true;
    }
    return false;
  }

  private static boolean isNotDanglingFile(Node path) {
    if (path != null && path.isFile() && ((File) path).isDangling()) {
      return false;
    }
    return true;
  }

  /**
   * Check if a path is valid
   *
   * @param path checking path
   * @return true if the path is valid, false otherwise
   */
  private static boolean isValidPath(Node path) {
    return path.isDirectory() || (path.isFile() &&
        !((File) path).isDangling());
  }

  /**
   * Paste the copy to Directory
   *
   * @param oldPath the copy
   * @param newPath the target directory
   */
  private static void pasteNodetoDir(Node oldPath, Node newPath) throws
      NodeTypeAreNotTheSameException {
    try {
      ((Directory) newPath).addFile(oldPath);
    } catch (NodeAlreadyExistsException e) {
      Node newNode = ((Directory) newPath).getNodeByPathString(
          oldPath.getName());
      if (oldPath.getType().equals(newNode.getType())) {
        ((Directory) newPath).removeFile(oldPath.getName());
        pasteNodetoDir(oldPath, newPath);
      } else {
        throw new NodeTypeAreNotTheSameException();
      }
    }
  }


  /**
   * Get a copy of a Node
   *
   * @param root Node that is going to be copied
   * @return A copy of the node
   */
  private static Node getNodeCopy(Node root) {
    Node nodeCopy = null;
    if (root.isFile()) {
      nodeCopy = getFileCope((File) root);
    } else if (root.isDirectory()) {
      nodeCopy = getDirCopy((Directory) root);
    }
    return nodeCopy;
  }

  /**
   * get a copy of a Directory
   *
   * @param root Directory that is going to be copied
   * @return A copy of the Directory
   */
  private static Directory getDirCopy(Directory root) {
    ArrayList<Node> content = root.getFiles();
    Directory dirCopy = new Directory(root.getName());
    for (Node i : content) {
      if (i.isFile()) {
        try {
          dirCopy.addFile(getFileCope((File) i));
        } catch (NodeAlreadyExistsException e) {
        }
      } else if (i.isDirectory()) {
        try {
          dirCopy.addFile(getDirCopy((Directory) i));
        } catch (NodeAlreadyExistsException e) {
        }
      }
    }
    return dirCopy;
  }

  /**
   * get a copy of a File
   *
   * @param file File that is going to be copied
   * @return A copy of the File
   */
  private static File getFileCope(File file) {
    File fileCopy = new File(file.getName());
    fileCopy.setContent(file.getContent());
    return fileCopy;
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
   * Get cp's manual.
   *
   * @return Manual for cp command.
   */
  public String getManual() {
    return "cp [OLDPATH] [NEWPATH]\n"
        + "====================================\n"
        + "copy the item from OLDPATH and paste it to NEWPATH";
  }
}
