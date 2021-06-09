package command;

import command.builder.SaveCommandBuilder;
import serialization.BinaryObjectSerializer;
import serialization.IObjectSerializer;
import util.IParameters;
import util.exceptions.InvalidNumberOfArgumentsException;

import java.io.IOException;

/**
 * Save command, used to save current shell state to a file
 */
public class Save extends Command {

  private IObjectSerializer serializer;

  /**
   * Construct the save command with parameters
   *
   * @param params Parameters
   */
  public Save(IParameters params) {
    super(params);
    this.serializer = new BinaryObjectSerializer();
  }

  /**
   * Constructor used to inject all dependencies
   *
   * @param builder Command builder
   */
  private Save(SaveCommandBuilder builder) {
    super(builder);
    this.serializer = builder.getSerializer();
  }

  /**
   * Factory method to create save command with a builder
   *
   * @param builder Builder to use
   * @return New command instance
   */
  public static Save createWithBuilder(SaveCommandBuilder builder) {
    return new Save(builder);
  }

  /**
   * Run the save command
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    getParams().assertArgumentLength(1);
    String fileName = getParams().getArgument(0);
    try {
      serializer.serializeToFile(getEnvironment(), fileName);
    } catch (IOException e) {
      printToErr("Cannot write " + fileName);
    }
  }

  /**
   * Get manual for pwd command.
   *
   * @return Manual for pwd.
   */
  @Override
  public String getManual() {
    return "save FILENAME\n"
            + "====================================\n"
            + "Save current shell state to a file.";
  }
}
