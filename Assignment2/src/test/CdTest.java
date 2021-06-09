package test;

import static org.junit.Assert.*;

import command.Cd;
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
 * Test for Cd
 */
public class CdTest {

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
    builder = new CommandBuilder();
    builder.setParameters(parameters);
    builder.setErrorOut(errorOut);
    builder.setStandardOut(standardOut);
    parameters.setCommandName("cd");
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testCdToRoot() throws Exception {
    Directory dir = new Directory("test");
    env.getCurrentDir().addFile(dir);
    env.setCurrentDir(dir);
    parameters.setArguments("..");
    Cd cmd = Cd.createWithBuilder(builder);
    cmd.run();
    assertEquals("/", env.getCurrentDir().getPath());
  }

  @Test
  public void testCdToRootParent() throws Exception {
    Directory dir = new Directory("test");
    env.getCurrentDir().addFile(dir);
    env.setCurrentDir(dir);
    parameters.setArguments("../../");
    Cd cmd = Cd.createWithBuilder(builder);
    cmd.run();
    assertEquals("ERROR: cd: ../../: No such directory",
        errorOut.buffer);
  }

  @Test
  public void testCdByAbsolutePath() throws Exception {
    Directory dir = new Directory("test5");
    env.getCurrentDir().addFile(dir);
    env.setCurrentDir(dir);
    parameters.setArguments("/");
    Cd cmd = Cd.createWithBuilder(builder);
    cmd.run();
    assertEquals("/", env.getCurrentDir().getPath());
  }


  @Test
  public void testCdByRelativePath() throws Exception {
    Directory dir = new Directory("test1");
    env.getCurrentDir().addFile(dir);
    parameters.setArguments("test1");
    Cd cmd = Cd.createWithBuilder(builder);
    cmd.run();
    assertEquals("/test1/", env.getCurrentDir().getPath());
  }

  @Test
  public void testCdByDot() throws Exception {
    Directory dir = new Directory("test1");
    env.getCurrentDir().addFile(dir);
    env.setCurrentDir(dir);
    parameters.setArguments("..");
    Cd cmd = Cd.createWithBuilder(builder);
    cmd.run();
    assertEquals("/", env.getCurrentDir().getPath());
  }

  @Test
  public void testCdToNotExistingAbsolutePath() throws Exception {
    Directory dir = new Directory("test1");
    env.getCurrentDir().addFile(dir);
    env.setCurrentDir(dir);
    parameters.setArguments("/test1/test2");
    Cd cmd = Cd.createWithBuilder(builder);
    cmd.run();
    assertEquals("ERROR: cd: /test1/test2: No such directory",
        errorOut.buffer);
  }

  @Test
  public void testCdToNotExistingRelativePath() throws Exception {
    Directory dir = new Directory("test3");
    env.getCurrentDir().addFile(dir);
    env.setCurrentDir(dir);
    parameters.setArguments("test3/test2");
    Cd cmd = Cd.createWithBuilder(builder);
    cmd.run();
    assertEquals("ERROR: cd: test3/test2: No such directory",
        errorOut.buffer);
  }

}