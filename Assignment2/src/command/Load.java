package command;

import command.builder.LoadCommandBuilder;
import environment.Environment;
import serialization.BinaryObjectSerializer;
import serialization.IObjectSerializer;
import util.IParameters;
import util.exceptions.InvalidNumberOfArgumentsException;

import java.io.IOException;

/**
 * Load command, used to load a save file from disk
 */
public class Load extends Command {

  private IObjectSerializer serializer;

  /**
   * Construct the load command with parameters
   *
   * @param params Parameters
   */
  public Load(IParameters params) {
    super(params);
    this.serializer = new BinaryObjectSerializer();
  }

  /**
   * Constructor used to inject all dependencies
   *
   * @param builder Command builder
   */
  private Load(LoadCommandBuilder builder) {
    super(builder);
    this.serializer = builder.getSerializer();
  }

  /**
   * Factory method to create load command with a builder
   *
   * @param builder Builder to use
   * @return New command instance
   */
  public static Load createWithBuilder(LoadCommandBuilder builder) {
    return new Load(builder);
  }

  /**
   * Run the load command
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    getParams().assertArgumentLength(1);
    if (!historyIsEmpty()) {
      printToErr("You can't load once a command has executed.");
    } else {
      try {
        Object obj = serializer.deserializeFromFile(getParams().getArgument(0));
        if (obj instanceof Environment) {
          printToOut("Loaded environment.\n");
          Environment env = (Environment) obj;
          Environment.setSingleInstance(env);
          flushOutput();
        } else {
          printToErr("Fatal error, invalid save file.");
        }
      } catch (IOException e) {
        printToErr(e.toString());
        printToErr("File not readable."
                + getParams().getArgument(0));
      } catch (ClassNotFoundException e) {
        printToErr("Fatal error, class not found.");
      }
    }
  }

  /**
   * Check if command history is empty
   *
   * @return True if history stack is empty
   */
  private boolean historyIsEmpty() {
    return getEnvironment().getCommandStack().getSize() == 1;
  }

  /**
   * Get manual for pwd command.
   *
   * @return Manual for pwd.
   */
  @Override
  public String getManual() {
    return "load FILENAME\n"
            + "====================================\n"
            + "Load shell state from a file.";
  }
}
