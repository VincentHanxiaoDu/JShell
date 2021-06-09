package test;

import static org.junit.Assert.assertEquals;

import command.Ls;
import command.builder.CommandBuilder;
import driver.Directory;
import driver.File;
import environment.Environment;
import test.mock.MockOutputIO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

/**
 * Ls test suite
 */
public class LsTest {

  private Environment env;
  private MockOutputIO standardOut;
  private MockOutputIO errorOut;
  private MockParameters parameters;
  private CommandBuilder builder;

  @Before
  public void setup() throws Exception {
    // Initialize file system
    env = Environment.createSingleInstance();
    Directory root = new Directory("");
    root.addFile(new Directory("test"));
    ((Directory) root.getNodeByPathString("test"))
        .addFile(new Directory("test3"));
    root.addFile(new File("sample"));
    root.addFile(new File("test2"));
    env.setCurrentDir(root);

    standardOut = new MockOutputIO();
    errorOut = new MockOutputIO();
    parameters = new MockParameters();
    parameters.setCommandName("ls");

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
  public void testPrintCurrentDirectory() {
    Ls ls = Ls.createWithBuilder(builder);
    ls.run();
    assertEquals("test\nsample\ntest2", standardOut.content);
  }

  @Test
  public void testPrintRootDirectory() {
    parameters.setArguments("/");
    Ls ls = Ls.createWithBuilder(builder);
    ls.run();
    assertEquals("/:\ntest\nsample\ntest2\n\n",
        standardOut.content);
  }

  @Test
  public void testPrintMultipleDirectories() {
    parameters.setArguments("/", "test");
    Ls ls = Ls.createWithBuilder(builder);
    ls.run();
    assertEquals("/:\ntest\nsample\ntest2\n\ntest:\ntest3\n\n",
        standardOut.content);
  }

  @Test
  public void testPrintRecursiveDirectories() {
    parameters.setArguments("-R");
    Ls ls = Ls.createWithBuilder(builder);
    ls.run();
    assertEquals(".:\ntest\nsample\ntest2\n\n./test/:\ntest3\n\n"
        + "./test/test3/:\n\n", standardOut.content);
  }

  @Test
  public void testPrintFile() {
    parameters.setArguments("sample");
    Ls ls = Ls.createWithBuilder(builder);
    ls.run();
    assertEquals(standardOut.content, "sample");
  }

  @Test
  public void testFileDoesNotExist() {
    parameters.setArguments("asdf");
    Ls ls = Ls.createWithBuilder(builder);
    ls.run();
    assertEquals(errorOut.buffer, "ERROR: ls: \"asdf\" does not exist");
  }
}