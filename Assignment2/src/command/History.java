package command;

import command.builder.CommandBuilder;
import util.IParameters;
import util.Parameters;
import util.StringUtil;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * List command history
 */
public class History extends Command {

  /**
   * Construct the history command with parameters
   *
   * @param params Parameters
   */
  public History(IParameters params) {
    super(params);
  }

  /**
   * Construct with a builder to inject dependencies
   * @param builder The builder to use
   */
  private History(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new history instance with builder
   * @param builder The command builder
   * @return New history instance
   */
  public static History createWithBuilder(CommandBuilder builder) {
    return new History(builder);
  }

  /**
   * Get the first argument parsed to int.
   * Return negative number if invalid.
   *
   * @return Number of commands to print (first argument)
   */
  private int getNumberOfCommandsToPrint() {
    try {
      return Integer.parseInt(getParams().getArgument(0));
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  /**
   * Given total number of commands we wish to print, and number we have,
   * return the number we can actually print
   *
   * @param toPrint Number with to print
   * @param actual  Actual command count we have
   * @return Number we can print
   */
  private static int getNumberOfCommandsToPrintByActualNumber
  (int toPrint, int actual) {
    if (toPrint < actual) {
      return toPrint;
    }
    return actual;
  }

  /**
   * Print the actual history
   *
   * @param toPrint    Number of commands to print
   * @param startIndex Start index of the command to print.
   */
  private void printHistory(int toPrint, int startIndex) {
    Object[] commandArray = getEnvironment().getCommandStack().toArray();
    if (startIndex != commandArray.length) {
      String result = "";
      int currentNumber = 1;
      if (startIndex != -1) {
        currentNumber = startIndex + 1;
      }
      for (int i = toPrint - 1; i >= 0; i--) {
        result += currentNumber + ". " + commandArray[i] + "\n";
        currentNumber++;
      }
      result = StringUtil.removeLastNChars(result, 1);
      printToOut(result);
      flushOutput();
    }
  }

  /**
   * Print a list of recently used commands to the screen.
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    Object[] commandArray = this.getEnvironment().getCommandStack().toArray();
    this.getParams().assertArgumentLength(0, 1);
    if (this.getParams().getArguments().length == 1) {
      int wishToPrint = getNumberOfCommandsToPrint();
      if (wishToPrint < 0) {
        printToErr("Please enter a valid number. i.e., history number.");
      } else {
        int totalNumberOfCommands = commandArray.length;
        int totalToPrint = History.getNumberOfCommandsToPrintByActualNumber
                (wishToPrint, totalNumberOfCommands);
        if (totalToPrint == totalNumberOfCommands) {
          printHistory(totalToPrint, -1);
        } else {
          printHistory(totalToPrint,
                  totalNumberOfCommands - totalToPrint);
        }
      }
    } else {
      printHistory(commandArray.length, -1);
    }
  }

  /**
   * Get history's manual.
   *
   * @return Manual for history command.
   */
  public String getManual() {
    return "history [number]\n"
            + "====================================\n"
            + "Print out the recently used commands, one command per line.";
  }
}
