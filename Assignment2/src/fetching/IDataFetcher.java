package fetching;

import driver.exceptions.InvalidFileNameException;
import java.io.IOException;

/**
 * Interface for datafetcher
 *
 */
public interface IDataFetcher {
  String getDataFromURL(String urlStr) throws IOException;
  String getFileName(String url) throws InvalidFileNameException;
}
