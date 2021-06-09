package output;

import driver.File;

public class OutputIO implements IOutputIO {

  // Current string buffer
  protected String buffer = "";

  // Behaviour of the buffering system
  private BufferBehaviour bufferBehaviour = BufferBehaviour.DEFAULT;

  /**
   * Write to output stream
   * @param value Value to write
   */
  public final void write(String value) {
    this.buffer += value;
    if(bufferBehaviour == BufferBehaviour.AUTO_FLUSH) {
      flushBuffer();
    }
  }

  /**
   * Flush the buffer and write to actual stream
   */
  public void flushBuffer() {
    // This method must be overwritten
  }
  
  public void flushBufferNoNewline() {
    // This method must be overwritten
  }

  /**
   * Clear current buffer
   */
  protected void clearBuffer() {
    buffer = "";
  }

  /**
   * Set the behaviour of buffer
   * @param behaviour The behaviour you wish to set
   */
  public void setBufferBehaviour(BufferBehaviour behaviour) {
    bufferBehaviour = behaviour;
  }

  /**
   * Get the behaviour of buffer
   * @return BufferBehaviour
   */
  public BufferBehaviour getBufferBehaviour() {
    return bufferBehaviour;
  }

  /**
   * Make a standard output IO if nothing is passed
   * @return StdOutputIO
   */
  public static OutputIO make() {
    return new StdOutputIO();
  }

  /**
   * Make the standard error output
   * @return ErrorOutputIO
   */
  public static OutputIO makeError() {
    return new ErrorOutputIO();
  }

  /**
   * Make file output IO depending on the operator
   * @param operator can be >> or >
   * @param file the file you wish to write to
   * @return AppendFileOutputIO or OverwriteFileOutputIO depending on operator
   */
  public static OutputIO make(String operator, File file) {
    switch(operator) {
      case ">>":
        return new AppendFileOutputIO(file);
      case ">":
        return new OverwriteFileOutputIO(file);
      default:
        return null;
    }
  }

}
