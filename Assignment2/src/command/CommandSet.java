package command;

import driver.exceptions.CommandNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import util.IParameters;

/**
 * Register all commands installed and provide the instance of specific command
 * class.
 */
public class CommandSet {

  /**
   * Returns a new instance of the command found, or null if there is no such a
   * command found.
   *
   * @return a new instance of corresponding command, or null if there is no
   * such a command found
   */
  private static Hashtable<String, String> getCommands() {
    Hashtable<String, String> commands = new Hashtable<>();
    commands.put("exit", "Exit");
    commands.put("cd", "Cd");
    commands.put("cat", "Cat");
    commands.put("echo", "Echo");
    commands.put("history", "History");
    commands.put("ls", "Ls");
    commands.put("man", "Man");
    commands.put("mkdir", "Mkdir");
    commands.put("popd", "Popd");
    commands.put("pushd", "Pushd");
    commands.put("pwd", "Pwd");
    commands.put("mv", "Mv");
    commands.put("get", "Get");
    commands.put("tree", "Tree");
    commands.put("save", "Save");
    commands.put("load", "Load");
    commands.put("cp", "Cp");
    commands.put("find", "Find");
    return commands;
  }

  /**
   * Get a command instance
   *
   * @param params the parameters of the command
   * @return the command instance
   */
  public static Command getCommand(IParameters params) throws
      CommandNotFoundException {
    Hashtable<String, String> commands = getCommands();
    String classname = commands.get(params.getCommandName());
    if (classname == null) {
      throw new CommandNotFoundException();
    } else {
      try {
        Class<?> cmd = Class.forName("command." + classname);
        Object cmdInstance = cmd.getConstructor(IParameters.class)
            .newInstance(params);
        return (Command) cmdInstance;
      } catch (NoSuchMethodException |
          IllegalAccessException |
          InvocationTargetException |
          InstantiationException|
          ClassNotFoundException e) {
        throw new CommandNotFoundException();
      }
    }
  }
}
