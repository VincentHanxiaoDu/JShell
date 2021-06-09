package util;

import java.util.ArrayList;

/**
 * Parses a given command into an arraylist for the commands to read
 *
 */
public class Parser {
  /**
   * Parses the command
   * @param command String which is the entire command given by the user
   * @return a new Parameters class which contains parameters for commands to
   * use
   */
  public static Parameters parse(String command) {
    ArrayList<String> cmdParsed= new ArrayList<String>();
    // trim leading/trailing spaces
    String toParse = command.trim();
    // find first location of space
    int trimLocation = toParse.indexOf(' ');
    String cmd = toParse;
    if (trimLocation != -1) {
      cmd = toParse.substring(0, trimLocation);
    }
    cmdParsed.add(cmd);
    if (trimLocation != -1) {
      toParse = toParse.substring(trimLocation);
    }
    // otherwise cmd is the only field
    else {
      toParse = "";
    }
    // create arraylist
    createArrayList(cmdParsed, toParse);
    return new Parameters(cmdParsed);
  }
  
  /**
   * creates the arraylist of parameters with the given string
   * @param cmdParsed ArrayList which contains the parsed command
   * @param toParse String which needs to be parsed
   */
  private static void createArrayList(ArrayList<String> cmdParsed, 
      String toParse) {
    int trimLocation;
    while (toParse.length() > 0) {
      toParse = toParse.trim();
      String toAdd;
      if (toParse.indexOf(' ') > toParse.indexOf("\"") && 
          toParse.indexOf("\"") != -1) {
        toParse = addQuote(cmdParsed, toParse);
      }
      // otherwise the first space is before the first quotation
      else {
        trimLocation = toParse.indexOf(' ');
        // no space means this is the last thing to add
        if (trimLocation == -1) {
          cmdParsed.add(toParse);
          toParse = "";
        }
        else {
          toAdd = toParse.substring(0, trimLocation);
          cmdParsed.add(toAdd);
          toParse = toParse.substring(trimLocation);
        }
      }
    }
  }
  
  /**
   * Adds the next parameter surrounded in quotes to the arraylist of commands
   * and returns the rest of the string back
   * @param cmdParsed ArrayList to store the parameter in
   * @param toParse String which contains the parameters
   * @return String which contains the rest of the commands minus what was
   * added
   */
  private static String addQuote(ArrayList<String> cmdParsed, String toParse) {
    int trimLocation;
    String toAdd;
    
    trimLocation = toParse.indexOf("\"");
    // find the other "
    int secQuote = toParse.indexOf("\"", trimLocation + 1);
    if (secQuote == -1) {
      toAdd = toParse;
    }
    else {
      toAdd = toParse.substring(trimLocation, secQuote + 1);
    }
    cmdParsed.add(toAdd);
    // trim string to past the second quote point
    toParse = toParse.substring(secQuote + 1);
    if (secQuote == -1) {
      toParse = "";
    }
    return toParse;
  }
}
