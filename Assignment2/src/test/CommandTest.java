package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import command.Command;
import org.junit.Test;
import test.mock.MockParameters;

/**
 * Test for Command
 * Coverage report:
 * Class  100%
 * Method 100%
 * Line   100%
 */
public class CommandTest {

  @Test
  public void testCommandGetManualShouldReturnNullByDefault() {
    MockParameters params = new MockParameters();
    Command cmd = new Command(params);
    assertNull(cmd.getManual());
  }

  @Test
  public void testCommandGetParamsShouldGetCorrectObject() {
    MockParameters params = new MockParameters();
    Command cmd = new Command(params);
    assertTrue(cmd.getParams().equals(params));
  }

  @Test
  public void testCommandGetEnvironmentShouldGetInstanceOfEnvironment() {
    MockParameters params = new MockParameters();
    Command cmd = new Command(params);
    assertFalse(cmd.getEnvironment() == null);
  }

}
