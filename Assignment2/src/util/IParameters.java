package util;

import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Interface for parameters
 *
 */
public interface IParameters {
  void extractRedirectionParameters();
  String[] getRedirectionParameters();
  String getCommandName();
  String[] getArguments();
  String getArgument(int index);
  void assertArgumentLength(int... lengths)
          throws InvalidNumberOfArgumentsException;
  void assertArgumentNotEmpty()
          throws InvalidNumberOfArgumentsException;
}
