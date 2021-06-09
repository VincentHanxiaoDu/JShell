package output;

import driver.File;

/**
 * Class to append output to a given file given existing file
 *
 */
public class AppendFileOutputIO extends OutputIO {

  /**
   * File to write to
   */
  private File file;

  /**
   * Default constructor
   *
   * @param file File to write to
   */
  AppendFileOutputIO(File file) {
    this.file = file;
  }

  /**
   * Dump buffer to file
   */
  @Override
  public void flushBuffer() {
    if(!buffer.equals("")) {
      this.file.appendContent(buffer);
    }
    clearBuffer();
  }

  /**
   * Flush the buffer without newline
   */
  public void flushBufferNoNewline() {
    this.flushBuffer();
  }

}

