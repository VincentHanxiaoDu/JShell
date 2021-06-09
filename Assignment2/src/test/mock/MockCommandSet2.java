package test.mock;

import command.Command;
import driver.exceptions.CommandNotFoundException;
import util.IParameters;

public class MockCommandSet2 {
  public static Command getCommand(IParameters params) throws
      CommandNotFoundException {
    throw new CommandNotFoundException();
  }

}
