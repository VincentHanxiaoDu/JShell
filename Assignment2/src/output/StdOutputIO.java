package output;

/**
 * Write content to the screen
 */
public class StdOutputIO extends OutputIO {

  /**
   * Flush the buffer
   */
  @Override
  public void flushBuffer() {
    if (!buffer.equals("")) {
      System.out.println(buffer);
      clearBuffer();
    }
  }

  /**
   * Flush the buffer without new line
   */
  public void flushBufferNoNewline() {
    if (!buffer.equals("")) {
      System.out.print(buffer);
      clearBuffer();
    }
  }
}
