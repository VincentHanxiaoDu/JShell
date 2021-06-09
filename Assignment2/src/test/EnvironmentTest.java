package test;

import static org.junit.Assert.assertEquals;

import driver.Directory;
import environment.Environment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.util.EnvironmentUtility;

/**
 * Test for Environment
 * Coverage report:
 * Class  100%
 * Method 100%
 * Line   100%
 */
public class EnvironmentTest {

  Environment env;

  @Before
  public void initializeEnvironment() {
    env = Environment.createSingleInstance();
  }

  @After
  public void destroyEnvironment() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testShouldBeAbleToReturnInitialExitFlag() {
    assertEquals(env.getExitFlag(), true);
  }

  @Test
  public void testSetExitFlagFalseShouldSetExitFlagToFalse() {
    env.setExitFlagFalse();
    assertEquals(env.getExitFlag(), false);
  }

  @Test
  public void testShouldBeAbleToReturnInitialCommandStack() {
    // We know it is stack if getSize is 0
    assertEquals(env.getCommandStack().getSize(), 0);
  }

  @Test
  public void testShouldBeAbleToReturnInitialDirStack() {
    assertEquals(env.getDirStack().getSize(), 0);
  }

  @Test
  public void testShouldBeAbleToReturnInitialDirectory() {
    assertEquals(env.getCurrentDir().getName(), "");
  }

  @Test
  public void testShouldBeAbleToSetNewCurrentDirectory() {
    env.setCurrentDir(new Directory("haha"));
    assertEquals(env.getCurrentDir().getName(), "haha");
  }

}
