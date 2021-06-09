package test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import command.Command;
import command.CommandSet;
import driver.exceptions.CommandNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.mock.MockParameters;

public class CommandSetTest {
  MockParameters parameters;
  @Before
  public void setup() {
    parameters = new MockParameters();
  }


  @After
  public void tearDown() {
    parameters = null;
  }

  @Test
  public void testCommandSetWithGet() {
    parameters.setCommandName("get");
    parameters.setArguments("http://test/testfile.html");
    try{
      Command cmd = CommandSet.getCommand(parameters);
      assertEquals(cmd.getClass().getName(), "command.Get");
    }catch (CommandNotFoundException e) {
      fail("CommandNotFoundException");
    }
  }

  @Test
  public void testCommandSetWithCd() {
    parameters.setCommandName("cd");
    parameters.setArguments("/testpath");
    try{
      Command cmd = CommandSet.getCommand(parameters);
      assertEquals(cmd.getClass().getName(), "command.Cd");
    }catch (CommandNotFoundException e) {
      fail("CommandNotFoundException");
    }
  }

  @Test
  public void testCommandSetWithInvalidCommand() {
    parameters.setCommandName("InvalidCommand");
    parameters.setArguments("testargs");
    try{
      Command cmd = CommandSet.getCommand(parameters);
      fail("Still got Command instance!");
    }catch (CommandNotFoundException e) {
      assertEquals(e.getMessage(), "Command not found.");
    }
  }

}