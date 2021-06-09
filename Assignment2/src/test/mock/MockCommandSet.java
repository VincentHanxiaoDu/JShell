package test.mock;

import command.Command;
import driver.exceptions.CommandNotFoundException;
import util.IParameters;

public class MockCommandSet {
  public static Command getCommand(IParameters params) throws
      CommandNotFoundException {
    return new Command(params);
  }
}

