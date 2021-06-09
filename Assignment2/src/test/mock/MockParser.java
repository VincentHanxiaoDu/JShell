package test.mock;

import util.IParameters;

/**
 * IParser for testing.
 * Won't document as this is purely for testing
 */
public class MockParser {

  public static MockParameters parse(String command) {
    MockParameters mockParameters = new MockParameters();
    mockParameters.setCommandName(command);
    mockParameters.setArguments("arg1", "args2");
    return mockParameters;
  }


}