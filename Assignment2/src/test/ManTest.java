package test;

import command.Man;
import command.builder.CommandBuilder;
import environment.Environment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockOutputIO;
import test.mock.MockParameters;
import test.util.EnvironmentUtility;

import static org.junit.Assert.assertEquals;

public class ManTest {
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
    parameters.setCommandName("man");
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
  public void testManWithCat(){
    parameters.setCommandName("man");
    parameters.setArguments("cat");
    Man cmd = Man.createWithBuilder(builder);
    cmd.run();
    assertEquals("cat FILE1 [FILE2 ...]\n"
            + "====================================\n"
            + "Display the contents of given files.", standardOut.content);
  }

  @Test
  public void testManWithCd(){
    parameters.setCommandName("man");
    parameters.setArguments("cd");
    Man cmd = Man.createWithBuilder(builder);
    cmd.run();
    assertEquals("cd DIR\n"
            + "====================================\n"
            + "Change directory to given directory, which may\n"
            + "be relative to current directory or full path.",
            standardOut.content);
  }

  @Test
  public void testManWithCp(){
    parameters.setCommandName("man");
    parameters.setArguments("cp");
    Man cmd = Man.createWithBuilder(builder);
    cmd.run();
    assertEquals("cp [OLDPATH] [NEWPATH]\n"
            + "====================================\n"
            + "copy the item from OLDPATH and paste it to NEWPATH",
            standardOut.content);
  }

  @Test
  public void testManWithEcho(){
    parameters.setCommandName("man");
    parameters.setArguments("echo");
    Man cmd = Man.createWithBuilder(builder);
    cmd.run();
    assertEquals("echo \"STRING\"\n"
                    + "echo \"STRING\" > FILENAME\n"
                    + "echo \"STRING\" >> FILENAME\n"
                    + "====================================\n"
                    + "Print given string on the shell if no file\n"
                    + "is provided. Otherwise erase the file or create new\n"
                    + "file to store the given string if echo String > file\n"
                    + "is used. If echo String >> file is used, it will append"
                    + "\nthe given string into file instead of overwrites.",
            standardOut.content);
  }

  @Test
  public void testManWithTree(){
    parameters.setCommandName("man");
    parameters.setArguments("tree");
    Man cmd = Man.createWithBuilder(builder);
    cmd.run();
    assertEquals("tree\n"
                    + "====================================\n"
                    + "Display the entire file system as a tree",
            standardOut.content);
  }

  @Test
  public void testManWithPwd(){
    parameters.setCommandName("man");
    parameters.setArguments("pwd");
    Man cmd = Man.createWithBuilder(builder);
    cmd.run();
    assertEquals("pwd\n" + "====================================\n"
                    + "Print current working directory (including the " +
                    "whole path).", standardOut.content);
  }
}
