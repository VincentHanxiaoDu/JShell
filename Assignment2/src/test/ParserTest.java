package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import util.Parameters;
import util.Parser;

/**
 * Test for Parser
 * Coverage report:
 * Class  100%
 * Method 100%
 * Line   100%
 */
public class ParserTest {

  @Test
  public void testShouldBeAbleToParseNormalCommand() {
    Parameters params = Parser.parse("man 123 456");
    assertEquals(params.getCommandName(), "man");
    assertEquals(params.getArgument(0), "123");
    assertEquals(params.getArgument(1), "456");
  }

  @Test
  public void testShouldBeAbleToParseCommandsWithQuotes() {
    Parameters params = Parser.parse("echo \"awesome\" haha");
    assertEquals(params.getCommandName(), "echo");
    assertEquals(params.getArgument(0), "\"awesome\"");
    assertEquals(params.getArgument(1), "haha");
  }

  @Test
  public void testShouldBeAbleToParseEmptyCommand() {
    Parameters params = Parser.parse("");
    assertEquals(params.getCommandName(), "");
    assertEquals(params.getArguments().length, 0);
  }

  @Test
  public void testShouldBeAbleToParseCommandWithoutClosingQuote() {
    Parameters params = Parser.parse("echo \"haha 233");
    assertEquals(params.getCommandName(), "echo");
    assertEquals(params.getArgument(0), "\"haha 233");
  }

  @Test
  public void testShouldBeAbleToParseCommandWithoutClosingQuote2() {
    Parameters params = Parser.parse("echo \"haha 233\" \"haha");
    assertEquals(params.getCommandName(), "echo");
    assertEquals(params.getArgument(0), "\"haha 233\"");
    assertEquals(params.getArgument(1), "\"haha");
  }

  @Test
  public void testShouldBeAbleToParseWithMoreSpaces() {
    Parameters params = Parser.parse("man      123      456");
    assertEquals(params.getCommandName(), "man");
    assertEquals(params.getArgument(0), "123");
    assertEquals(params.getArgument(1), "456");
  }

  @Test
  public void testShouldBeAbleToParseWithMoreSpaces2() {
    Parameters params = Parser.parse("man      123      " +
            "456             ");
    assertEquals(params.getCommandName(), "man");
    assertEquals(params.getArgument(0), "123");
    assertEquals(params.getArgument(1), "456");
  }

  @Test
  public void testShouldBeAbleToParseWithMoreSpaces3() {
    Parameters params = Parser.parse("       man      123      " +
            "456             ");
    assertEquals(params.getCommandName(), "man");
    assertEquals(params.getArgument(0), "123");
    assertEquals(params.getArgument(1), "456");
  }

  @Test
  public void testShouldBeAbleToParseWithMoreSpaces4() {
    Parameters params = Parser.parse("       man      123   " +
            "\"'  123  d'\"   456    ");
    assertEquals(params.getCommandName(), "man");
    assertEquals(params.getArgument(0), "123");
    assertEquals(params.getArgument(1), "\"'  123  d'\"");
    assertEquals(params.getArgument(2), "456");
  }

  @Test
  public void testShouldBeAbleToParseQuotesWithinQuotes() {
    Parameters params = Parser.parse("test \"\"\"\"\"\"\"\"");
    assertEquals(params.getCommandName(), "test");
    assertEquals(params.getArgument(0), "\"\"\"\"\"\"\"\"");
  }

  @Test
  public void testShouldBeAbleToParseJustSpaces() {
    Parameters params = Parser.parse("                        ");
    assertEquals(params.getCommandName(), "");
    assertEquals(params.getArguments().length, 0);
  }

}
