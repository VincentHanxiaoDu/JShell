package test;

import static org.junit.Assert.*;

import command.CommandHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockCommandSet;
import test.mock.MockCommandSet2;
import test.mock.MockOutputIO;
import test.mock.MockParser;


/**
 * Test for CommandHandler
 */

public class CommandHandlerTest {
  private MockOutputIO errorOut;

  @Before
  public void setup() {
    errorOut = new MockOutputIO();
    CommandHandler.setErrOut(errorOut);
    CommandHandler.setParserClass(MockParser.class);
  }


  @After
  public void tearDown() {
    CommandHandler.restoreCommandSetClass();
    CommandHandler.restoreParserClass();
    CommandHandler.restoreErrOut();
    errorOut = null;
  }

  @Test
  public void testCommandHandlerWithCommand() {
    CommandHandler.setCommandSetClass(MockCommandSet.class);
    CommandHandler.callCommand("command");
    assertEquals(errorOut.buffer, "");
  }

  @Test
  public void testCommandHandlerWithInvalidCommand() {
    CommandHandler.setCommandSetClass(MockCommandSet2.class);
    CommandHandler.callCommand("invalid Command");
    assertEquals(errorOut.buffer,
        "ERROR: invalid Command: Command not found.");
  }

}