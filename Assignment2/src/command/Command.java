package command;

import command.builder.CommandBuilder;
import driver.File;
import driver.Node;
import environment.Environment;
import output.BufferBehaviour;
import output.IOutputIO;
import output.OutputIO;
import util.IParameters;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Super class for all commands
 * Takes care of IO redirection
 */
public class Command {

  private IOutputIO errOut;
  private IOutputIO out;
  private IParameters params;

  /**
   * Construct an empty command with parameters
   * @param params Parsed parameters
   */
  public Command(IParameters params) {
    this.params = params;
    this.errOut = OutputIO.makeError();
    this.errOut.setBufferBehaviour(BufferBehaviour.AUTO_FLUSH);
    extractAndSetupRedirection();
  }

  /**
   * A special constructor to construct the command with a builder
   * @param builder Builder to use
   */
  protected Command(CommandBuilder builder) {
    this.params = builder.getParameters();
    this.errOut = builder.getErrorOut();
    this.out = builder.getStandardOut();
    getParams().extractRedirectionParameters();
  }

  /**
   * Setup redirection for the command
   * @return True if setup successful
   */
  protected boolean extractAndSetupRedirection() {
    getParams().extractRedirectionParameters();
    String[] redirectionParams = getParams().getRedirectionParameters();
    if(redirectionParams != null && redirectionParams.length == 2) {
      // In this case, we need to get a file, it could be provisional
      Node node = getEnvironment().getCurrentDir()
          .getNodeByPathString(redirectionParams[1]);
      if(node == null || !node.isFile() || !isValidName(node.getName())) {
        this.out = null;
        return false;
      } else {
        this.out = OutputIO.make(redirectionParams[0], (File) node);
      }
    } else {
      this.out = OutputIO.make();
    }
    return true;
  }

  /**
   * Run the command
   */
  public void run() {
    if(out != null) {
      try {
        execute();
      } catch (InvalidNumberOfArgumentsException e) {
        printToErr("Invalid number of arguments.");
      }
    } else {
      printToErr("Invalid output stream, setup failure.");
    }
  }

  /**
   * An abstract method for running the command
   */
  protected void execute() throws InvalidNumberOfArgumentsException {

  }

  /**
   * Print a string to error output
   * @param value The value to print
   */
  protected void printToErr(String value) {
    String commandName = getParams().getCommandName();
    if(commandName == null) {
      commandName = "unknown";
    }
    this.errOut.write("ERROR: " + commandName + ": " + value);
  }

  /**
   * Print a string to the output destination
   * @param value The value to print
   */
  protected void printToOut(String value) {
    if(this.out != null) {
      this.out.write(value);
    }
  }

  /**
   * Flush the output
   */
  protected void flushOutput() {
    if(this.out != null) {
      this.out.flushBuffer();
    }
  }
  
  /**
   * Flush the output with no newline character at the end of the output
   */
  protected void flushOutputNoNewline() {
    if (this.out != null) {
      this.out.flushBufferNoNewline();
    }
  }

  /**
   * Get the parameter instance
   *
   * @return Arguments for current command
   */
  public IParameters getParams() {
    return this.params;
  }

  /**
   * Get this.env
   *
   * @return Current environment
   */
  public Environment getEnvironment() {
    return Environment.createSingleInstance();
  }

  /**
   * checks if a file contains any illegal characters in its name
   *
   * @param name
   * @return boolean
   */
  private boolean isValidName(String name) {
    if (name.matches(".*[\\s/.!@#$%^&*(){}~|<>\\?].*")) {
      return false;
    }
    return true;
  }

  /**
   * Get command's manual, this must be overridden
   * @return Command's manual, this case null
   */
  public String getManual() {
    return null;
  }
}
