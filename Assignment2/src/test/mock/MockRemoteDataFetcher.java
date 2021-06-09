package test.mock;

import driver.exceptions.InvalidFileNameException;
import fetching.IDataFetcher;
import java.io.IOException;
import java.net.MalformedURLException;

public class MockRemoteDataFetcher implements IDataFetcher {

  public String getDataFromURL(String UrlStr) throws IOException {
    if (UrlStr.equals("Invalid URL")) {
      throw new MalformedURLException();
    } else if (UrlStr.equals("Failed to get input stream")) {
      throw new IOException();
    }
    return "\n"
        + "<!DOCTYPE html>\n"
        + "<html>\n"
        + "<head>\n"
        + "<title>Page Title</title>\n"
        + "</head>\n"
        + "<body>\n"
        + "\n"
        + "<h1>Data</h1>\n"
        + "\n"
        + "</body>\n"
        + "</html>";
  }

  public String getFileName(String url) throws InvalidFileNameException{
    if (url.equals("Invalid file name")) {
      throw new InvalidFileNameException();
    }
    return "filename";
  }
}
