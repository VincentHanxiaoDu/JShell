package fetching;

import driver.exceptions.InvalidFileNameException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * RemoteDataFetcher gets file data from a given URL
 */
public class RemoteDataFetcher implements IDataFetcher {

  /**
   * get Data from url
   * @param urlStr the string of the url
   * @return the content of the url
   * @throws IOException thrown when failed to fetch data from remote
   */
  public String getDataFromURL(String urlStr) throws IOException {
    URL url = new URL(urlStr);
    URLConnection connection = url.openConnection();
    InputStream stream = connection.getInputStream();
    byte[] bytesOut = readStream(stream);
    String contentStr = new String(bytesOut);
    stream.close();
    return contentStr;
  }

  /**
   * Read data from stream and return a byte array contains data in it.
   *
   * @param stream input data stream
   * @return byte array of input stream
   * @throws IOException thrown when failed to read data
   */
  private static byte[] readStream(InputStream stream) throws
      IOException {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    byte[] bytesData = new byte[1024];
    int bytesRecorded;
    while ((bytesRecorded = stream.read(bytesData)) != -1) {
      byteOut.write(bytesData, 0, bytesRecorded);
    }
    return byteOut.toByteArray();
  }

  /**
   * Get the name of the file in URL
   *
   * @param url URL string
   * @return the name of the file
   * @throws InvalidFileNameException thrown when file name invalid
   */
  public String getFileName(String url) throws
      InvalidFileNameException {
    String[] path = url.split("/");
    int len = path.length;
    if (len > 0 && isValidFileType(path[len - 1])) {
      String newName = "";
      String name = path[len - 1];
      String invalid = "!@#$%^&*(){}~|/<>?/. ";
      char[] invalidArray = invalid.toCharArray();
      char[] nameArray = name.toCharArray();
      boolean valid = true;
      for (char nameChar : nameArray) {
        for (char invalidChar : invalidArray) {
          if (nameChar == invalidChar) {
            valid = false;
            break;
          }
        }
        if (valid) {
          newName += nameChar;
        } else {
          break;
        }
      }
      return newName;
    } else {
      throw new InvalidFileNameException();
    }
  }

  /**
   * Determine if the type of the file is acceptable.
   *
   * @param fileName The name of the file.
   * @return A boolean indicates if the type is acceptable.
   */
  private static boolean isValidFileType(String fileName) {
    String[] validTypes = {".txt", ".html"};
    for (String i : validTypes) {
      int len = fileName.length();
      String extension = fileName.substring(Math.max(len - i.length(), 0),
          len);
      if (extension.equals(i)) {
        return true;
      }
    }
    return false;
  }
}
