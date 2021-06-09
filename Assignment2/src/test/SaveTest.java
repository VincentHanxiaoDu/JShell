package test;

import static org.junit.Assert.assertEquals;

import command.Save;
import command.builder.SaveCommandBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockObjectSerializer;
import test.mock.MockOutputIO;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

/**
 * Unit tests for save
 */
public class SaveTest {

  private MockObjectSerializer serializer;
  private SaveCommandBuilder builder;
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
    builder = new SaveCommandBuilder();
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
    parameters.setCommandName("save");
    Save cmd = Save.createWithBuilder(builder);
    cmd.run();
    assertEquals(
        "ERROR: save: Invalid number of arguments.",
        errorOut.buffer);
  }

  @Test
  public void testShouldHaveNoOutputIfNoParameter() {
    parameters.setCommandName("save");
    Save cmd = Save.createWithBuilder(builder);
    cmd.run();
    assertEquals("", standardOut.content);
  }

  @Test
  public void testShouldHaveNoErrorIfParameterExist() {
    parameters.setCommandName("save");
    parameters.setArguments("somefile");
    Save cmd = Save.createWithBuilder(builder);
    cmd.run();
    assertEquals("", errorOut.buffer);
  }

  @Test
  public void testShouldReturnErrorIfExtraParameter() {
    parameters.setCommandName("save");
    parameters.setArguments("somefile", "extraarg");
    Save cmd = Save.createWithBuilder(builder);
    cmd.run();
    assertEquals(
        "ERROR: save: Invalid number of arguments.",
        errorOut.buffer);
  }

  @Test
  public void testShouldHaveNoOutputIfExtraParameter() {
    parameters.setCommandName("save");
    parameters.setArguments("somefile", "extraarg");
    Save cmd = Save.createWithBuilder(builder);
    cmd.run();
    assertEquals("", standardOut.content);
  }

  @Test
  public void testShouldSaveAndSerializeTheObjectToFile() {
    parameters.setCommandName("save");
    parameters.setArguments("somefile");
    Save cmd = Save.createWithBuilder(builder);
    cmd.run();
    assertEquals(true,
        serializer.getObjStore().containsKey("somefile"));
  }

}
