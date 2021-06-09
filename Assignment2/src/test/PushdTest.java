package test;

import static org.junit.Assert.*;

import command.Pushd;
import command.builder.CommandBuilder;
import driver.Directory;
import environment.Environment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockOutputIO;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

/**
 * Test for Pushd
 */
public class PushdTest {

  Environment env;
  MockOutputIO out;
  MockOutputIO errout;
  MockParameters parameters;
  CommandBuilder builder;

  @Before
  public void setup() throws Exception {
    env = Environment.createSingleInstance();
    Directory root = new Directory("");
    root.addFile(new Directory("test1"));
    Directory test1 = (Directory) root.getNodeByPathString("test1");
    test1.addFile(new Directory("test2"));
    Directory test2 = (Directory) test1.getNodeByPathString("test2");
    test2.addFile(new Directory("test3"));
    env.setCurrentDir(root);
    out = new MockOutputIO();
    errout = new MockOutputIO();
    parameters = new MockParameters();
    builder = new CommandBuilder();
    builder.setErrorOut(errout);
    builder.setStandardOut(out);
    builder.setParameters(parameters);
    parameters.setCommandName("pushd");
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testPushdWithInvalidPath() {
    parameters.setArguments("/DoesNotExist");
    Pushd pushd = Pushd.createWithBuilder(builder);
    pushd.run();
    assertEquals("ERROR: pushd: " +
            "/DoesNotExist: No such file or directory", errout.buffer);
  }

  @Test
  public void testPushdWithValidPath() {
    parameters.setArguments("/test1");
    Pushd pushd = Pushd.createWithBuilder(builder);
    pushd.run();
    String[] dirstack = {"/"};
    assertEquals(dirstack[0], env.getDirStack().toArray()[0]);
  }
}