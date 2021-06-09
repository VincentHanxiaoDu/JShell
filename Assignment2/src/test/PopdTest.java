package test;

import static org.junit.Assert.*;

import command.Popd;
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
 * Test for Popd
 */
public class PopdTest {

  private Environment env;
  private MockOutputIO out;
  private MockOutputIO errout;
  private MockParameters parameters;
  private CommandBuilder builder;

  @Before
  public void setup() throws Exception {
    env = Environment.createSingleInstance();
    Directory root = new Directory("");
    root.addFile(new Directory("test1"));
    root.addFile(new Directory("test2"));
    Directory test1 = (Directory) root.getNodeByPathString("test1");
    test1.addFile(new Directory("test3"));
    env.setCurrentDir(root);

    out = new MockOutputIO();
    errout = new MockOutputIO();
    parameters = new MockParameters();
    parameters.setCommandName("popd");
    builder = new CommandBuilder();
    builder.setParameters(parameters);
    builder.setStandardOut(out);
    builder.setErrorOut(errout);
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testPopdWhenNoDirInDirStack() {
    Popd popd = Popd.createWithBuilder(builder);
    popd.run();
    assertEquals("ERROR: popd: " +
            "The directory stack is empty.", errout.buffer);
  }

  @Test
  public void testPopdInDirStack() {
    Popd popd = Popd.createWithBuilder(builder);
    env.getDirStack().push("/test1");
    popd.run();
    assertEquals("/test1/", env.getCurrentDir().getPath());
  }

  @Test
  public void testPopdInDirStack2() {
    Popd popd = Popd.createWithBuilder(builder);
    env.getDirStack().push("/test1/test3");
    popd.run();
    assertEquals("/test1/test3/", env.getCurrentDir().getPath());
  }

}