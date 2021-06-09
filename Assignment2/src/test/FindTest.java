package test;

import static org.junit.Assert.*;

import command.Find;
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
 * Unit tests for find
 */
public class FindTest {

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
    test1.addFile(new File("testfile4"));
    File f1 = new File("testfile1");
    f1.setContent("content");
    root.addFile(f1);
    root.addFile(new File("testfile2"));
    test1.addFile(new Directory("testdir3"));
    env.setCurrentDir(root);
    errout = new MockOutputIO();
    out = new MockOutputIO();
    parameters = new MockParameters();
    parameters.setCommandName("find");
    builder = new CommandBuilder();
    builder.setParameters(parameters);
    builder.setStandardOut(out);
    builder.setErrorOut(errout);
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
    errout = null;
    out = null;
  }

  @Test
  public void testFindDir() {
    parameters.setArguments("/", "-type", "d", "-name", "\"testdir1\"");
    Find find = Find.createWithBuilder(builder);
    find.run();
    assertEquals("Search directories in /:\n"
        + "1. /testdir1\n", out.buffer);
  }

  @Test
  public void testFindFile() {
    parameters.setArguments("/", "-type", "f", "-name", "\"testfile1\"");
    Find find = Find.createWithBuilder(builder);
    find.run();
    assertEquals("Search files in /:\n"
        + "1. /testfile1\n", out.buffer);
  }

  @Test
  public void testFindwithInvalidFlags() {
    parameters.setArguments("/", "-type", "q", "-name", "\"testfile1\"");
    Find find = Find.createWithBuilder(builder);
    find.run();
    assertEquals("ERROR: find: Invalid flag: -type q", errout.buffer);
  }

  @Test
  public void testFindWithInvalidFlags2() {
    parameters.setArguments("/", "-type", "d", "-name", "testfile1");
    Find find = Find.createWithBuilder(builder);
    find.run();
    assertEquals("ERROR: find: Invalid flag: -name testfile1",
        errout.buffer);
  }

  @Test
  public void testFindwithMultipleDir() {
    parameters.setArguments("/", "/testdir1", "-type", "f", "-name",
        "\"testfile4\"");
    Find find = Find.createWithBuilder(builder);
    find.run();
    assertEquals("Search files in /:\n"
            + "1. /testdir1/testfile4\n"
            + "Search files in /testdir1:\n"
            + "1. /testdir1/testfile4\n",
        out.buffer);
  }

  @Test
  public void testFindwithMultipleDir2() {
    parameters.setArguments("/", "/testdir1", "-type", "d", "-name",
        "\"testdir1\"");
    Find find = Find.createWithBuilder(builder);
    find.run();
    assertEquals("Search directories in /:\n"
            + "1. /testdir1\n"
            + "Search directories in /testdir1:\n",
        out.buffer);
  }

}