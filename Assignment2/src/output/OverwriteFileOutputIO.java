package output;

import driver.File;

/**
 * Overwrite a file when writing to an existing file
 *
 */
public class OverwriteFileOutputIO extends OutputIO {


  /**
   * File to write to
   */
  private File file;

  /**
   * Default constructor
   *
   * @param file File to write to
   */
  OverwriteFileOutputIO(File file) {
    this.file = file;
  }

  /**
   * Dump buffer to file
   */
  @Override
  public void flushBuffer() {
    if(!buffer.equals("")) {
      this.file.setContent(buffer);
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
