package test;

import static org.junit.Assert.assertEquals;

import command.Cat;
import command.builder.CommandBuilder;
import driver.File;
import environment.Environment;
import test.mock.MockOutputIO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

/**
 * Unit tests for cat
 */
public class CatTest {

  private Environment env;
  private MockOutputIO standardOut;
  private MockOutputIO errorOut;
  private MockParameters parameters;
  private CommandBuilder builder;

  @Before
  public void setup() {
    env = Environment.createSingleInstance();
    standardOut = new MockOutputIO();
    errorOut = new MockOutputIO();
    parameters = new MockParameters();
    parameters.setCommandName("cat");
    builder = new CommandBuilder();
    builder.setStandardOut(standardOut);
    builder.setErrorOut(errorOut);
    builder.setParameters(parameters);
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testCatShouldReturnOneFileContent() throws Exception {
    parameters.setArguments("sample");
    File file = new File("sample");
    file.setContent("amazing content.");
    env.getCurrentDir().addFile(file);
    Cat cmd = Cat.createWithBuilder(builder);
    cmd.run();
    assertEquals("amazing content.", standardOut.content);
  }

  @Test
  public void testCatShouldGiveErrorIfNoArguments() {
    Cat cmd = Cat.createWithBuilder(builder);
    cmd.run();
    assertEquals("ERROR: cat: Invalid number of arguments.",
        errorOut.buffer);
  }


  @Test
  public void testCatShouldStillPrintFileContentsIfSomeIsInvalid()
      throws Exception {

    File file = new File("sample");
    file.setContent("amazing content.");
    env.getCurrentDir().addFile(file);
    File anotherFile = new File("sample3");
    anotherFile.setContent("sample3 content");
    env.getCurrentDir().addFile(anotherFile);

    parameters.setArguments("sample", "sample2", "sample1", "sample3");

    Cat cmd = Cat.createWithBuilder(builder);
    cmd.run();
    // Should have correct standard output
    assertEquals("amazing content.\n\n\nsample3 content",
        standardOut.content);
    // Should have correct error output
    assertEquals("ERROR: cat: The given path sample2 is not a file."
            + "ERROR: cat: The given path sample1 is not a file.",
        errorOut.buffer);
  }

  @Test
  public void testCatShouldBeAbleToCatSameFileMultipleTimes()
      throws Exception {
    parameters.setArguments("file", "file", "file");
    File file = new File("file");
    file.setContent("1");
    env.getCurrentDir().addFile(file);
    Cat cmd = Cat.createWithBuilder(builder);
    cmd.run();
    assertEquals("1\n\n\n1\n\n\n1", standardOut.content);
  }


}
