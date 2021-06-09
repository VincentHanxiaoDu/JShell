package test;

import static org.junit.Assert.assertEquals;

import environment.Environment;
import input.ConsoleInputIO;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.util.EnvironmentUtility;

/**
 * Test for ConsoleInputIO
 * Coverage report:
 * Class  100%
 * Method 100%
 * Line   100%
 */
public class ConsoleInputIOTest {

  private InputStream in;
  private ConsoleInputIO inputIO;

  @Before
  public void construct() {
    in = new ByteArrayInputStream("Awesome command\n".getBytes());
    inputIO = new ConsoleInputIO(in);
  }

  @After
  public void destruct() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
  }

  @Test
  public void testReadShouldReadNextLine() {
    String s = inputIO.read();
    assertEquals(s, "Awesome command");
  }

}
