package test;

import command.History;
import command.builder.CommandBuilder;
import environment.Environment;
import test.mock.MockOutputIO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for history command
 */
public class HistoryTest {

  Environment env;
  private MockOutputIO errorOut;
  private MockOutputIO standardOut;
  private CommandBuilder builder;
  private MockParameters parameters;

  @Before
  public void setup() {
    // We can't help, as Command requires Environment
    // unable to mock this object.
    env = Environment.createSingleInstance();
    errorOut = new MockOutputIO();
    standardOut = new MockOutputIO();
    builder = new CommandBuilder();
    parameters = new MockParameters();
    builder.setErrorOut(errorOut);
    builder.setStandardOut(standardOut);
    builder.setParameters(parameters);
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testRegularHistory() {
    parameters.setCommandName("history");
    env.getCommandStack().push("testcommand > 1");
    env.getCommandStack().push("somethingelse 123");
    History cmd = History.createWithBuilder(builder);
    cmd.run();
    // There won't be 3. history as it is pushed on user input
    assertEquals("1. testcommand > 1\n2. somethingelse 123",
        standardOut.content);
  }

  @Test
  public void testHistoryWithValidNumber() {
    parameters.setCommandName("history");
    parameters.setArguments("2");
    env.getCommandStack().push("command 1");
    env.getCommandStack().push("command 2");
    env.getCommandStack().push("command 3");
    History cmd = History.createWithBuilder(builder);
    cmd.run();
    assertEquals("2. command 2\n3. command 3",
        standardOut.content);
  }

  @Test
  public void testHistoryWithLargeNumber() {
    parameters.setCommandName("history");
    parameters.setArguments("10000");
    env.getCommandStack().push("command a");
    env.getCommandStack().push("command b");
    env.getCommandStack().push("command c");
    History cmd = History.createWithBuilder(builder);
    cmd.run();
    assertEquals("1. command a\n2. command b\n3. command c",
        standardOut.content);
  }

  @Test
  public void testHistoryWithNegativeNumber() {
    parameters.setCommandName("history");
    parameters.setArguments("-100");
    env.getCommandStack().push("command a");
    History cmd = History.createWithBuilder(builder);
    cmd.run();
    // Expect empty std out
    assertEquals("", standardOut.content);
    // Expect error out
    assertEquals("ERROR: history: Please enter a valid number. i.e., "
        + "history number.", errorOut.buffer);
  }

  @Test
  public void testHistoryWithInvalidInput() {
    parameters.setCommandName("history");
    parameters.setArguments("notvalidnumber");
    History cmd = History.createWithBuilder(builder);
    cmd.run();
    // Expect empty std out
    assertEquals("", standardOut.content);
    // Expect error out
    assertEquals("ERROR: history: Please enter a valid number. i.e., "
        + "history number.", errorOut.buffer);
  }
}
