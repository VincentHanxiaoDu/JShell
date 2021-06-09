package command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import output.IOutputIO;
import output.OutputIO;
import util.IParameters;
import util.Parser;

/**
 * A processor of command.
 */
public class CommandHandler {
  private static IOutputIO errOut = OutputIO.makeError();
  private static Class<?> parserClass = Parser.class;
  private static Class<?> commandSetClass = CommandSet.class;

  /**
   * Find the command and call it with arguments.
   *
   * @param command the string of user input
   */
  public static void callCommand(String command) {
    try {
      Method parse = parserClass.getMethod("parse", String.class);
      IParameters params = (IParameters) parse.invoke(parserClass, command);
      if (!params.getCommandName().equals("")) {
        try {
          Method getCommand = commandSetClass.getMethod("getCommand",
              IParameters.class);
          Command commandInstance = (Command)
              getCommand.invoke(commandSetClass, params);
          commandInstance.run();
        } catch (InvocationTargetException e) {
          if (e.getCause().getMessage().
              equals("Command not found.")) {
            errOut.write("ERROR: " + params.getCommandName() +
                ": Command not found.");
          } else {
            errOut.write("ERROR: " + "Cannot get the command instance!");
          }
        } catch (IllegalAccessException| NoSuchMethodException e) {
          errOut.write("CommandSet error!");
        }
        errOut.flushBuffer();
      }
    } catch (NoSuchMethodException| IllegalAccessException|
        InvocationTargetException e) {
      errOut.write("Parser error!");
      errOut.flushBuffer();
    }
  }

  /**
   * sets a new Parser for command
   * @param newParser new Parser
   */
  public static void setParserClass(Class<?> newParser) {
    parserClass = newParser;
  }

  /**
   * sets a new CommandSetClass for command
   * @param newCommandSet new CommandSet
   */
  public static void setCommandSetClass(Class<?> newCommandSet) {
    commandSetClass = newCommandSet;
  }

  /**
   * sets a new error output stream for command
   * @param newErrOut new error output stream
   */
  public static void setErrOut(IOutputIO newErrOut) {
    errOut = newErrOut;
  }

  /**
   * restore Parser to default
   */
  public static void restoreParserClass() {
    parserClass = Parser.class;
  }

  /**
   * restore error output stream to default
   */
  public static void restoreErrOut() {
    errOut = OutputIO.makeError();
  }

  /**
   * restore CommandSet to default
   */
  public static void restoreCommandSetClass() {
    commandSetClass = CommandSet.class;
  }
}
