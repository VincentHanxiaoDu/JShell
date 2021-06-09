package environment;

import driver.Directory;

import java.io.Serializable;

/**
 * Class representing the environment
 */
public class Environment implements Serializable {

  /**
   * The singleton instance of environment
   */
  private static Environment instance;

  /**
   * Directory stack
   */
  private Stack<String> dirStack;

  /**
   * Command stack
   */
  private Stack<String> commandStack;

  /**
   * Current working directory ref
   */
  private Directory currentDir;

  /**
   * Exit flag, if false the program should exit
   */
  private boolean exitFlag;

  /**
   * Constructor
   */
  private Environment() {
    this.currentDir = new Directory("");
    this.dirStack = new Stack<>();
    this.commandStack = new Stack<>();
    this.exitFlag = true;
  }

  /**
   * Create an instance of Environment if none exists However if one has 
   * already been created, then this method will return existing one
   *
   * @return The environment instance
   */
  public static Environment createSingleInstance() {
    if (Environment.instance == null) {
      Environment.instance = new Environment();
    }
    return Environment.instance;
  }

  public static void setSingleInstance(Environment env) {
    Environment.instance = env;
  }

  /**
   * Get command stack
   *
   * @return The command stack
   */
  public Stack<String> getCommandStack() {
    return this.commandStack;
  }

  /**
   * Set current directory
   *
   * @param dir New directory ref
   */
  public void setCurrentDir(Directory dir) {
    this.currentDir = dir;
  }

  /**
   * Get current dir
   *
   * @return Current dir ref
   */
  public Directory getCurrentDir() {
    return this.currentDir;
  }

  /**
   * Get directory stack
   *
   * @return The directory stack
   */
  public Stack<String> getDirStack() {
    return this.dirStack;
  }

  /**
   * Get the exit flag for the shell
   *
   * @return The exit flag
   */
  public boolean getExitFlag() {
    return this.exitFlag;
  }

  /**
   * Set the exit flag for the shell to be false
   */
  public void setExitFlagFalse() {
    this.exitFlag = false;
  }
}
