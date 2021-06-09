package output;

/**
 * prints values to the standard error.
 */
public class ErrorOutputIO extends OutputIO {

  /**
   * Flush current buffer
   */
  @Override
  public void flushBuffer() {
    if (!buffer.equals("")) {
      System.out.println(buffer);
      clearBuffer();
    }
  }
}
