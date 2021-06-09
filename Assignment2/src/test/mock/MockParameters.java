package test.mock;

import util.IParameters;
import util.exceptions.InvalidNumberOfArgumentsException;

public class MockParameters implements IParameters {

  private String[] redirectionParameters = new String[]{};
  private String commandName = "";
  private String[] arguments = new String[]{};

  @Override
  public void extractRedirectionParameters() {
    // Do nothing
  }

  @Override
  public String[] getRedirectionParameters() {
    return redirectionParameters;
  }

  @Override
  public String getCommandName() {
    return commandName;
  }

  @Override
  public String[] getArguments() {
    return arguments;
  }

  @Override
  public String getArgument(int index) {
    return arguments[index];
  }

  @Override
  public void assertArgumentLength(int... lengths)
    throws InvalidNumberOfArgumentsException {
    for (int length : lengths) {
      if (getArguments().length == length) {
        return;
      }
    }
    throw new InvalidNumberOfArgumentsException();
  }

  @Override
  public void assertArgumentNotEmpty()
      throws InvalidNumberOfArgumentsException {
    if(getArguments().length == 0) {
      throw new InvalidNumberOfArgumentsException();
    }
  }

  /**
   * Set command name field
   * @param commandName The command name field
   */
  public void setCommandName(String commandName) {
    this.commandName = commandName;
  }

  /**
   * Set the arguments field
   * @param arguments The arguments
   */
  public void setArguments(String... arguments) {
    this.arguments = arguments;
  }

  /**
   * Set redirection parameters field
   * @param redirectionParameters The parameters
   */
  public void setRedirectionParameters(String... redirectionParameters) {
    this.redirectionParameters = redirectionParameters;
  }
}
