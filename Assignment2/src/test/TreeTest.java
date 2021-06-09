package test;

import command.Tree;
import command.builder.CommandBuilder;
import driver.Directory;
import driver.File;
import environment.Environment;
import org.junit.Test;
import test.mock.MockOutputIO;
import org.junit.After;
import org.junit.Before;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

import static org.junit.Assert.assertEquals;

public class TreeTest {
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
    parameters.setCommandName("tree");
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
  public void testTreeWhenThereIsOnlyRoot(){
    parameters.setCommandName("tree");
    Tree cmd = Tree.createWithBuilder(builder);
    cmd.run();
    assertEquals("\\", standardOut.content);
  }

  @Test
  public void testTreeWithOnlyOneDirectory() throws Exception{
    parameters.setCommandName("tree");
    Directory root = new Directory("");
    root.addFile(new Directory("directory1"));
    env.setCurrentDir(root);
    Tree cmd = Tree.createWithBuilder(builder);
    cmd.run();
    assertEquals("\\\n directory1", standardOut.content);
  }

  @Test
  public void testTreeWhenThereAreMoreThanOneDirectory() throws Exception{
    parameters.setCommandName("tree");
    Directory root = new Directory("");
    root.addFile(new Directory("directory1"));
    root.addFile(new Directory("directory2"));
    root.addFile(new Directory("directory3"));
    env.setCurrentDir(root);
    Tree cmd = Tree.createWithBuilder(builder);
    cmd.run();
    assertEquals("\\\n directory3\n directory2\n directory1",
            standardOut.content);
  }

  @Test
  public void testTreeWhenThereAreDirectoriesInDirectories() throws Exception{
    parameters.setCommandName("tree");
    Directory root = new Directory("");
    Directory directoryOne = new Directory("directory1");
    Directory directoryThree = new Directory("directory3");
    root.addFile(directoryOne);
    directoryOne.addFile(new Directory("directory11"));
    directoryOne.addFile(new Directory("directory12"));
    root.addFile(new File("directory2"));
    root.addFile(directoryThree);
    directoryThree.addFile(new Directory("directory31"));
    env.setCurrentDir(root);
    Tree cmd = Tree.createWithBuilder(builder);
    cmd.run();
    assertEquals("\\\n directory3\n  directory31\n directory2\n" +
                    " directory1\n  directory12\n  directory11",
            standardOut.content);
  }
}
