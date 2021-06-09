package test;

import command.Exit;
import command.builder.CommandBuilder;
import environment.Environment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockOutputIO;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for exit command
 */
public class ExitTest {

  private MockOutputIO standardOut;
  private MockOutputIO errorOut;
  private MockParameters parameters;
  private CommandBuilder builder;

  @Before
  public void setup() {
    standardOut = new MockOutputIO();
    errorOut = new MockOutputIO();
    parameters = new MockParameters();
    builder = new CommandBuilder();
    builder.setStandardOut(standardOut);
    builder.setErrorOut(errorOut);
    builder.setParameters(parameters);
    parameters.setCommandName("exit");
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testExitCommandShouldSetFlagToFalse() {
    Exit exit = Exit.createWithBuilder(builder);
    exit.run();
    assertEquals(false,
            Environment.createSingleInstance().getExitFlag());
  }

  @Test
  public void testExitCommandShouldNotCrashEvenIfInvalidArgumentsProvided() {
    Exit exit = Exit.createWithBuilder(builder);
    exit.run();
  }

}
