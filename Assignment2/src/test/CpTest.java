package test;

import static org.junit.Assert.*;

import command.Cp;
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
 * Unit tests for cp
 */
public class CpTest {

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
    Directory test1 = (Directory) root.getNodeByPathString("testdir1");
    File f1 = new File("testfile1");
    f1.setContent("content");
    root.addFile(f1);
    root.addFile(new File("testfile2"));
    test1.addFile(new Directory("testdir3"));
    env.setCurrentDir(root);
    // Initialize mock objects and builder
    errout = new MockOutputIO();
    out = new MockOutputIO();
    parameters = new MockParameters();
    builder = new CommandBuilder();
    builder.setStandardOut(out);
    builder.setErrorOut(errout);
    builder.setParameters(parameters);
    parameters.setCommandName("cp");
  }

  @After
  public void tearDown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testCpDir() {
    parameters.setArguments("testdir1", "testdir2");
    Cp cp = Cp.createWithBuilder(builder);
    cp.run();
    assertEquals("", errout.buffer);
  }

  @Test
  public void testCpFileToFile() {
    parameters.setArguments("testfile1", "testfile2");
    Cp cp = Cp.createWithBuilder(builder);
    cp.run();
    assertEquals("content", ((File)env.getCurrentDir().
        getNodeByPathString("testfile2")).getContent());
  }

  @Test
  public void testCpFileToDir() {
    parameters.setArguments("testfile1", "testdir1");
    Cp cp = Cp.createWithBuilder(builder);
    cp.run();
    assertEquals("content", ((File)env.getCurrentDir().
        getNodeByPathString("testdir1/testfile1")).getContent());
  }

  @Test
  public void testCpWithInvalidPath() {
    parameters.setArguments("testfile1zxc", "testdir1");
    Cp cp = Cp.createWithBuilder(builder);
    cp.run();
    assertEquals("ERROR: cp: testfile1zxc: Invalid path.",
        errout.buffer);
  }


  @Test
  public void testCpDirIntoItself() {
    parameters.setArguments("testdir1", "testdir1");
    Cp cp = Cp.createWithBuilder(builder);
    cp.run();
    assertEquals("ERROR: cp: testdir1: Cannot copy "
            + "directory into itself.",
        errout.buffer);
  }

  @Test
  public void testCpDirIntoItself2() {
    parameters.setArguments("testdir1", "testdir1/testdir3");
    Cp cp = Cp.createWithBuilder(builder);
    cp.run();
    assertEquals("ERROR: cp: testdir1: Cannot copy "
            + "directory into itself.",
        errout.buffer);
  }

}