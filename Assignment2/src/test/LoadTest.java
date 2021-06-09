package test;

import command.Load;
import command.builder.LoadCommandBuilder;
import environment.Environment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockObjectSerializer;
import test.mock.MockOutputIO;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for load
 */
public class LoadTest {

  private MockObjectSerializer serializer;
  private LoadCommandBuilder builder;
  private MockOutputIO errorOut;
  private MockOutputIO standardOut;
  private MockParameters parameters;

  @Before
  public void setup() {
    // Construct mock objects
    serializer = new MockObjectSerializer();
    errorOut = new MockOutputIO();
    standardOut = new MockOutputIO();
    parameters = new MockParameters();
    // Construct the builder
    builder = new LoadCommandBuilder();
    builder.setErrorOut(errorOut);
    builder.setStandardOut(standardOut);
    builder.setSerializer(serializer);
    builder.setParameters(parameters);
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testShouldReturnErrorIfNoParameter() {
    parameters.setCommandName("load");
    Load cmd = Load.createWithBuilder(builder);
    cmd.run();
    assertEquals(
            "ERROR: load: Invalid number of arguments.",
            errorOut.buffer);
  }

  @Test
  public void testShouldHaveNoOutputIfNoParameter() {
    parameters.setCommandName("load");
    Load cmd = Load.createWithBuilder(builder);
    cmd.run();
    assertEquals("", standardOut.content);
  }

  @Test
  public void testShouldHaveErrorIfObjectNotSerialized() {
    parameters.setCommandName("load");
    Load cmd = Load.createWithBuilder(builder);
    cmd.run();
    assertEquals("", standardOut.content);
  }

  @Test
  public void testShouldSetEnvironmentSingletonIfSaved() throws Exception {
    parameters.setCommandName("load");
    // Save an object into store
    Environment env = Environment.createSingleInstance();
    serializer.serializeToFile(env, "mysave");
    EnvironmentUtility.destroySingletonInstance();
    Environment.createSingleInstance().getCommandStack().push("load");
    parameters.setArguments("mysave");
    Load cmd = Load.createWithBuilder(builder);
    cmd.run();
    assertEquals(env, Environment.createSingleInstance());
  }
}
