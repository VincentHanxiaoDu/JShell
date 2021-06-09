package test;

import static org.junit.Assert.assertEquals;

import command.Mkdir;
import command.builder.CommandBuilder;
import driver.Directory;
import environment.Environment;
import test.mock.MockOutputIO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

/**
 * Test suite for mkdir
 */
public class MkdirTest {

  private Environment env;
  private MockOutputIO standardOut;
  private MockOutputIO errorOut;
  private MockParameters parameters;
  private CommandBuilder builder;

  @Before
  public void setup() {
    env = Environment.createSingleInstance();
    // Initialize the FS
    Directory root = new Directory("");
    env.setCurrentDir(root);
    // Mock objects
    standardOut = new MockOutputIO();
    errorOut = new MockOutputIO();
    parameters = new MockParameters();
    builder = new CommandBuilder();
    parameters.setCommandName("mkdir");
    builder.setStandardOut(standardOut);
    builder.setErrorOut(errorOut);
    builder.setParameters(parameters);
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }
//
  @Test
  public void testNotEnoughArguments() {
    Mkdir mkdir = Mkdir.createWithBuilder(builder);
    mkdir.run();
    assertEquals("ERROR: " +
            "mkdir: Invalid number of arguments.", errorOut.buffer);
  }

  @Test
  public void testIllegalFileName() {
    parameters.setArguments("something.txt");
    Mkdir mkdir = Mkdir.createWithBuilder(builder);

    mkdir.run();
    assertEquals("ERROR: mkdir: invalid directory name, "
        + "cannot contain any of !@#$%^&*(){}~|/<>?/. or whitespaces",
        errorOut.buffer);
  }

  @Test
  public void testAddingDirectory() {
    parameters.setArguments("test");
    Mkdir mkdir = Mkdir.createWithBuilder(builder);
    mkdir.run();
    assertEquals(env.getCurrentDir().getFiles()
            .get(0).getName(), "test");
  }

  @Test
  public void testAddingNestedDirectory() {
    parameters.setArguments("test", "test/test2");
    Mkdir mkdir = Mkdir.createWithBuilder(builder);
    mkdir.run();
    assertEquals(((Directory) env.getCurrentDir().getFiles().get(0))
        .getFiles().get(0).getName(), "test2");
  }

  @Test
  public void testDirectoryAlreadyExists() {
    parameters.setArguments("test", "test");
    Mkdir mkdir = Mkdir.createWithBuilder(builder);
    mkdir.run();
    assertEquals("ERROR: mkdir: directory "
        + "\"test\" already exists at this path.", errorOut.buffer);
  }
}