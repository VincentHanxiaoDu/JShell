package command;

import command.builder.GetCommandBuilder;
import driver.File;
import driver.exceptions.*;
import fetching.RemoteDataFetcher;
import java.io.IOException;
import java.net.MalformedURLException;
import fetching.IDataFetcher;
import util.IParameters;
import util.exceptions.InvalidNumberOfArgumentsException;

/**
 * Retrieve the file at the URL and add it to the current working directory.
 */
public class Get extends Command {
  private IDataFetcher IDataFetcher;

  /**
   * Construct the get command with parameters
   *
   * @param params Parameters
   */
  public Get(IParameters params) {
    super(params);
    this.IDataFetcher = new RemoteDataFetcher();
  }

  /**
   * Constructor used to inject all dependencies
   *
   * @param builder Command builder
   */
  private Get(GetCommandBuilder builder) {
    super(builder);
    this.IDataFetcher = builder.getIDataFetcher();
  }

  /**
   * Factory method to create get command with a builder
   * @param builder Builder to use
   * @return New command instance
   */
  public static Get createWithBuilder(GetCommandBuilder builder) {
    return new Get(builder);
  }

  /**
   * Retrieve the file at the URL and add it to the current working directory.
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    IParameters params = this.getParams();
    params.assertArgumentLength(1);
    String urlStr = params.getArgument(0);
    try {
      String data = IDataFetcher.getDataFromURL(urlStr);
      File file = new File(IDataFetcher.getFileName(urlStr));
      this.getEnvironment().getCurrentDir().addFile(file);
      file.setContent(data);
    } catch (MalformedURLException e) {
      printToErr("Invalid URL: " + urlStr);
    } catch (IOException e) {
      printToErr("Failed to get input stream.");
    } catch (NodeAlreadyExistsException e) {
      printToErr(e.getMessage());
    } catch (InvalidFileNameException e) {
      printToErr(urlStr + ": The type of the file is not supported.");
    }
  }




  /**
   * Get get's manual.
   *
   * @return Manual for get command.
   */
  public String getManual() {
    return "get [URL]\n"
        + "====================================\n"
        + "Retrieve the file at the URL and add"
        + " it to the current working directory.";
  }
}
