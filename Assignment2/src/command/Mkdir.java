package command;

import command.builder.CommandBuilder;
import driver.exceptions.NodeAlreadyExistsException;

import java.util.ArrayList;

import driver.Directory;
import driver.Node;
import driver.File;
import util.IParameters;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Creates Directorie(s)
 */
public class Mkdir extends Command {

  /**
   * construct Mkdir command with parameters
   *
   * @param params Parameters
   */
  public Mkdir(IParameters params) {
    super(params);
  }

  /**
   * Constructor used to inject dependencies
   *
   * @param builder Builder to use
   */
  private Mkdir(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new instance with builder
   *
   * @param builder Builder to use
   * @return New mkdir instance
   */
  public static Mkdir createWithBuilder(CommandBuilder builder) {
    return new Mkdir(builder);
  }

  /**
   * creates all of the Directories which the command is called with Throws
   * appropriate errors if the file names are invalid or if their path doesn't
   * exist.
   */
  private void createDirs() {
    String[] paths = getParams().getArguments();
    Directory currDir = super.getEnvironment().getCurrentDir();
    for (String path : paths) {
      if (path.equals("/")) {
        printToErr("cannot create directory \"" + path +
            "\" directory already exists");
        continue;
      }
      // if path name starts with . or / its a relative or abs path
      if (path.charAt(0) == '.' || path.charAt(0) == '/') {
        createDirAtPath(currDir, path);
      } else {
        int indexOfSlash = path.indexOf('/');
        if (indexOfSlash != -1) {
          // check if the name prior to / is a directory in currdir
          String outerDir = path.substring(0, indexOfSlash);
          if (!alreadyExists(currDir, outerDir)) {
            printToErr("cannot create directory \"" + path + "\"");
            continue;
          }
          String newPath = "./" + path;
          createDirAtPath(currDir, newPath);
        }
        // create folder here
        else {
          createDirHere(currDir, path);
        }
      }
    }
  }

  /**
   * Creates a new directory at the current directory
   *
   * @param currDir current Directory where the environment is
   * @param name String name of the current directory to be created
   */
  private void createDirHere(Directory currDir, String name) {
    if (!isValidName(name)) {
      printToErr("invalid directory name, cannot contain any of "
          + "!@#$%^&*(){}~|/<>?/. or whitespaces");
      return;
    }
    Node newDir = new Directory(name);
    try {
      currDir.addFile(newDir);
    } catch (NodeAlreadyExistsException e) {
      printToErr("directory \"" + name +
          "\" already exists at this path.");
    }
  }

  /**
   * creates a directory at a given path (absolute or relative)
   *
   * @param currDir current Directory that the environment is in
   * @param path String where the new directory will be created
   */
  private void createDirAtPath(Directory currDir, String path) {
    int lastIndexofSlash = path.lastIndexOf('/');
    String dirName = path.substring(lastIndexofSlash + 1);
    if (!isValidName(dirName)) {
      printToErr("invalid directory name, cannot contain any of "
          + "!@#$%^&*(){}~|/<>?/. or whitespaces");
      return;
    }
    String parentDir = path.substring(0, lastIndexofSlash);
    if (lastIndexofSlash == 0) {
      // if the only / is at the beginning then we want the root directory
      parentDir = "/.";
    }/*
    String dirName = path.substring(lastIndexofSlash + 1);
    if (!isValidName(dirName)) {
      printToErr("invalid directory name, cannot contain any of "
              + "!@#$%^&*(){}~|/<>?/. or whitespaces");
      return;
    }*/
    if (!parentDirExist(currDir, parentDir)) {
      printToErr("cannot create directory \"" + path + "\"");
      return;
    }
    Directory toAdd = (Directory) currDir.getNodeByPathString(parentDir);
    Node newDir = new Directory(dirName);
    try {
      toAdd.addFile(newDir);
    } catch (NodeAlreadyExistsException e) {
      printToErr("directory \"" + dirName +
          "\" already exists at this path.");
    }
  }

  /**
   * create directories within paths specified with the names specified Run the
   * Mkdir command
   * <p>
   * mkdir [path] ...
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    getParams().assertArgumentNotEmpty();
    this.createDirs();
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
   * Checks if a directory exists in a given parent directory
   *
   * @return boolean on if the directory already exists
   */
  private boolean alreadyExists(Directory dir, String name) {
    ArrayList<Node> files = dir.getFiles();
    for (Node node : files) {
      if (node.getName().equals(name)) {
        return true;
      }
    }
    return false;

  }

  /**
   * Check if parent directory actually exists
   *
   * @return If parent directory exists
   */
  private boolean parentDirExist(Directory dir, String parentDir) {
    Node curr = dir.getNodeByPathString(parentDir);
    // if the parent dir is a dangling file then it doesn't exist
    if (curr != null && curr.isFile() && ((File) curr).isDangling()) {
      return false;
    }
    return true;
  }

  /**
   * Get mkdir's manual.
   *
   * @return Manual for mkdir command.
   */
  public String getManual() {
    return "mkdir DIR [DIR ...]\n"
        + "====================================\n"
        + "Create new directories, which may be relative to current\n"
        + "directory or a full path.";
  }
}
