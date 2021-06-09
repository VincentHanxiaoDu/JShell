package test;

import command.Echo;
import command.builder.CommandBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockOutputIO;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for echo command
 */
public class EchoTest {

  private CommandBuilder builder;
  private MockOutputIO errorOut;
  private MockOutputIO standardOut;
  private MockParameters parameters;

  @Before
  public void setup() {
    // Construct mock objects
    errorOut = new MockOutputIO();
    standardOut = new MockOutputIO();
    parameters = new MockParameters();
    // Construct the builder
    builder = new CommandBuilder();
    builder.setErrorOut(errorOut);
    builder.setStandardOut(standardOut);
    builder.setParameters(parameters);
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testShouldReturnErrorIfNotWrappedWithQuotes() {
    parameters.setCommandName("echo");
    parameters.setArguments("\"this is just a sample string");
    Echo cmd = Echo.createWithBuilder(builder);
    cmd.run();
    assertEquals("ERROR: echo: Invalid string to print.",
            errorOut.buffer);
  }
  @Test
  public void testShouldPrintToOutputIfCorrect() {
    parameters.setCommandName("echo");
    parameters.setArguments("\"test\"");
    Echo cmd = Echo.createWithBuilder(builder);
    cmd.run();
    assertEquals("test", standardOut.content);
  }

}
