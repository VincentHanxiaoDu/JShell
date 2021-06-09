package command;

import command.builder.CommandBuilder;
import util.IParameters;
import util.Parameters;

import java.util.ArrayList;

import util.Traverse;

import driver.*;
import util.StringUtil;

/**
 * Print the files and directories in a given directory
 */
public class Ls extends Command {

  /**
   * The field for the -R flag, by default it is set to false
   * If Rflag == true that means -R is present
   */
  boolean rFlag = false;

  /**
   * construct Ls command with parameters
   *
   * @param params Parameters
   */
  public Ls(IParameters params) {
    super(params);
  }

  /**
   * Constructor to inject dependencies
   * @param builder Builder to use
   */
  private Ls(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new ls instance
   * @param builder Builder to use
   */
  public static Ls createWithBuilder(CommandBuilder builder) {
    return new Ls(builder);
  }

  /**
   * Run the ls command
   * ls [path1] ...
   */
  @Override
  protected void execute() {
    if (getParams().getArguments().length == 0) {
      printCurrentDirectory();
      flushOutput();
    } else if (getParams().getArguments().length == 1 
        && getParams().getArgument(0).equals("-R")) {
      // call recursive print dir with current dir
      printRecursiveDirectory(super.getEnvironment().getCurrentDir(), ".");
      flushOutputNoNewline(); 
    } else {
      multipleArgs();
      flushOutputNoNewline();
    }
  }
  
  /**
   * Deals with multiple paths given to ls
   */
  private void multipleArgs() {
    String[] paths = getParams().getArguments();
    for (String path : paths) {
      // check if the path is a provisional node
      if (path.equals("-R")) {
        rFlag = true;
        continue;
      }
      Node curr = getEnvironment().getCurrentDir()
              .getNodeByPathString(path);

      // check if node is a file or directory
      if (curr == null) {
        printToErr("file or directory\"" + path + "\" does not exist");
        continue;
      }
      if (curr.isFile() && ((File) curr).isDangling()) {
        printToErr("\"" + path + "\" does not exist");
        continue;
      }

      if (curr.isFile()) {
        //printToOut(curr.getName());
        printToOut(path);
      } else {
        // otherwise curr is a directory
        if (rFlag)
          printRecursiveDirectory((Directory) curr, path);
        else 
          printDirectory((Directory) curr, path);
      }
    }
  }

  /**
   * Prints the contents of a given directory
   *
   * @param dir Directory to get files of
   * @param path String the path to dir
   */
  private void printDirectory(Directory dir, String path) {
    ArrayList<Node> files = dir.getFiles();
    // if the name is the empty string we are at the root
    /*
    if (dir.getName().equals("")) {
      printToOut("/:\n");
    } else {
      printToOut(dir.getName() + ":\n");
    }*/
    if (path.length() > 1 && path.charAt(0) == '/' && path.charAt(1) == '/') {
      path = path.substring(1);
    }
    printToOut(path + ":\n");
    for (Node node : files) {
      printToOut(node.getName() + "\n");
    }
    printToOut("\n");
  }

  /**
   * Prints the contents of the current directory in environment
   */
  private void printCurrentDirectory() {
    String output = "";
    ArrayList<Node> files = super.getEnvironment().getCurrentDir().getFiles();
    for (Node node : files) {
      output += node.getName() + "\n";
      //printToOut(node.getName());
    }
    // take away extra newline
    output = StringUtil.removeLastNChars(output, 1);
    if (!output.equals(""))
      printToOut(output);
  }

  /**
   * Print out the contents of a given directory as well as every sub
   * directory within that directory
   * 
   * @param dir Directory to start at
   * @param dirPath String the path to dir
   */
  private void printRecursiveDirectory(Directory dir, String dirPath) {
    // first print the current directory
    printDirectory(dir, dirPath);

    Traverse<Node> traverse = new Traverse<Node>(this.getEnvironment());
    traverse.setStartPath(this.getEnvironment(), dir.getPath());
    ArrayList<Directory> directories = new ArrayList<Directory>();
    for (Node i : traverse) {
      if (i.isDirectory()) {
        directories.add((Directory) i);
      }
    }
    String current = "./";
    for (Directory d : directories) {
      if (d.equals(dir)) {
        continue;
      }
      String path = d.getPath();
      int start = path.indexOf(dir.getName());
      int end = path.indexOf(d.getName());
      // get the substring after wherever dir.getname appears + 1 for /
      String currentPath;
      if (!(start == end)) {
        path = path.substring(start + dir.getName().length() + 1);
        currentPath = dirPath + "/" + path;
      } else {
        currentPath = dirPath;
      }
      //String currentPath = current + path;
      // find where dir.getName is on path
      //printToOut(currentPath);
      printDirectory(d, currentPath);
    }
  }

  /**
   * Get ls's manual.
   *
   * @return Manual for ls command.
   */
  public String getManual() {
    return "ls [-R] [PATH ...]\n"
            + "====================================\n"
            + "Print the contents(file or directory) of current\n"
            + "directory, if no paths are given. Otherwise, print\n"
            + "the file if file is given. The contents of a directory\n"
            + "if a path is given. Otherwise an error message will\n"
            + "be printed. If -R is present it will recursively print\n"
            + "every subdirectory within every given path as well.";
  }
}
