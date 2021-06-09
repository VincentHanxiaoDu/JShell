package test;

import static org.junit.Assert.*;

import command.Mv;
import command.builder.CommandBuilder;
import driver.Directory;
import driver.File;
import environment.Environment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockOutputIO;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

/**
 * Unit tests for mv
 */
public class MvTest {

  private Environment env;
  private MockOutputIO out;
  private MockOutputIO errout;
  private MockParameters parameters;
  private CommandBuilder builder;

  @Before
  public void setup() throws Exception {
    env = Environment.createSingleInstance();
    Directory root = new Directory("");
    root.addFile(new Directory("testdir1"));
    root.addFile(new Directory("testdir2"));
    root.addFile(new File("testfile1"));
    Directory test1 = (Directory) root.getNodeByPathString("testdir1");
    test1.addFile(new Directory("testdir3"));
    env.setCurrentDir(root);
    errout = new MockOutputIO();
    out = new MockOutputIO();
    builder = new CommandBuilder();
    builder.setErrorOut(errout);
    builder.setStandardOut(out);
    parameters = new MockParameters();
    parameters.setCommandName("mv");
    builder.setParameters(parameters);
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testMvDir() {
    parameters.setArguments("testdir1", "testdir2");
    Mv mv = Mv.createWithBuilder(builder);
    mv.run();
    assertEquals("", errout.buffer);
  }

  @Test
  public void testMvFile() {
    parameters.setArguments("testfile1", "testdir2");
    Mv mv = Mv.createWithBuilder(builder);
    mv.run();
    assertEquals("", errout.buffer);
  }

  @Test
  public void testMvInvalidPath() {
    parameters.setArguments("testf", "testdir2");
    Mv mv = Mv.createWithBuilder(builder);
    mv.run();
    assertEquals("ERROR: mv: testf: Invalid path.", errout.buffer);
  }

  @Test
  public void testMvDirIntoItself() {
    parameters.setArguments("testdir1", "testdir1");
    Mv mv = Mv.createWithBuilder(builder);
    mv.run();
    assertEquals("ERROR: mv: testdir1: "
        + "Cannot move directory into itself.", errout.buffer);
  }

  @Test
  public void testMvDirIntoItself2() {
    parameters.setArguments("testdir1", "testdir1/testdir3");
    Mv mv = Mv.createWithBuilder(builder);
    mv.run();
    assertEquals("ERROR: mv: testdir1: "
        + "Cannot move directory into itself.", errout.buffer);
  }

  @Test
  public void testMvFiletoItself() {
    parameters.setArguments("testfile1", "testfile1");
    Mv mv = Mv.createWithBuilder(builder);
    mv.run();
    assertEquals("", errout.buffer);
  }

  @Test
  public void testMvDirtoFile() {
    parameters.setArguments("testdir1", "testfile1");
    Mv mv = Mv.createWithBuilder(builder);
    mv.run();
    assertEquals("ERROR: mv: Cannot move a "
        + "directory to a file.", errout.buffer);
  }
}