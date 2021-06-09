package command.builder;

import output.IOutputIO;
import util.IParameters;

/**
 * Builder class for commands
 */
public class CommandBuilder {

  private IParameters parameters;
  private IOutputIO errorOut;
  private IOutputIO standardOut;

  /**
   * Set command parameters
   * @param parameters Command parameters
   */
  public void setParameters(IParameters parameters) {
    this.parameters = parameters;
  }

  /**
   * Get command parameters
   * @return Command parameters
   */
  public IParameters getParameters() {
    return this.parameters;
  }

  /**
   * Set error output stream
   * @param errorOut Error output
   */
  public void setErrorOut(IOutputIO errorOut) {
    this.errorOut = errorOut;
  }

  /**
   * Get error output stream
   * @return Error output
   */
  public IOutputIO getErrorOut() {
    return this.errorOut;
  }

  /**
   * Set standard output stream
   * @param standardOut Standard output
   */
  public void setStandardOut(IOutputIO standardOut) {
    this.standardOut = standardOut;
  }

  /**
   * Get standard output stream
   * @return Standard output
   */
  public IOutputIO getStandardOut() {
    return this.standardOut;
  }

}
