package util;

import util.exceptions.InvalidNumberOfArgumentsException;

import java.util.ArrayList;

/**
 * Wrapper for parser arguments
 * Used to extract the array into sub parts
 */
public class Parameters implements IParameters {

  /**
   * list of arguments
   */
  private ArrayList<String> rawArgs;
  /**
   * string array of file redirection parameters
   */
  private String[] redirectionParameters;

  /**
   * Default constructor
   */
  public Parameters() {
  }

  /**
   * Construct the parameters object
   *
   * @param rawArgs Parsed arguments
   */
  public Parameters(ArrayList<String> rawArgs) {
    this.rawArgs = rawArgs;
  }

  /**
   * Return true if the command ends with >> ? or > ?
   *
   * @return If has redirection parameters
   */
  private boolean hasRedirectionParameters() {
    return (rawArgs.size() >= 3 &&
            (rawArgs.get(rawArgs.size() - 2).equals(">>") ||
                    rawArgs.get(rawArgs.size() - 2).equals(">")));
  }

  /**
   * Run extraction for all redirection parameters
   */
  public void extractRedirectionParameters() {
    if (hasRedirectionParameters()) {
      int size = this.rawArgs.size();
      ArrayList<String> args;
      // No need to check size, since hasRedirectionParameters already asserted
      args = new ArrayList<>(this.rawArgs.subList(size - 2, size));
      // new String[0] is the array that filled with values in args
      redirectionParameters = args.toArray(new String[0]);
    }
  }

  /**
   * Get redirection parameters in array, for example ['>>', 'test.txt']
   * null if no redirection exist
   *
   * @return Redirection parameters
   */
  public String[] getRedirectionParameters() {
    return redirectionParameters;
  }

  /**
   * Get the name of the command as a string
   *
   * @return name of the command or null if no command name exist
   */
  public String getCommandName() {
    if (this.rawArgs.size() > 0) {
      return this.rawArgs.get(0);
    } else {
      return null;
    }
  }

  /**
   * Get the arguments of the command as an array of strings
   *
   * @return the arguments of the command
   */
  public String[] getArguments() {
    int size = this.rawArgs.size();
    ArrayList<String> args;
    if (size >= 2) {
      if (getRedirectionParameters() != null) {
        args = new ArrayList<>(this.rawArgs.subList(1, size - 2));
      } else {
        args = new ArrayList<>(this.rawArgs.subList(1, size));
      }

    } else {
      args = new ArrayList<>();
    }
    // new String[0] is the array that filled with values in args
    return args.toArray(new String[0]);
  }

  /**
   * Get one argument at index
   *
   * @param index The index
   * @return An string argument, if out of bound, will return null
   */
  public String getArgument(int index) {
    String[] args = getArguments();
    if (index > args.length - 1) {
      return null;
    }
    return args[index];
  }

  /**
   * Check if argument len matches
   *
   * @param lengths Array of length to check
   * @throws InvalidNumberOfArgumentsException Thrown when assert fails
   */
  public void assertArgumentLength(int... lengths)
          throws InvalidNumberOfArgumentsException {
    for (int length : lengths) {
      if (getArguments().length == length) {
        return;
      }
    }
    throw new InvalidNumberOfArgumentsException();
  }

  /**
   * Assert argument list is not empty
   *
   * @throws InvalidNumberOfArgumentsException Thrown if empty
   */
  public void assertArgumentNotEmpty()
          throws InvalidNumberOfArgumentsException {
    if (getArguments().length != 0) {
      return;
    }
    throw new InvalidNumberOfArgumentsException();
  }
}
