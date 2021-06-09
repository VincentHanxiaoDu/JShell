package test.mock;

import output.BufferBehaviour;
import output.IOutputIO;


/**
 * ErrorOutputIO for testing.
 * Won't document as this is purely for testing
 */
public class MockOutputIO implements IOutputIO {

  // Current output content
  public String content = "";
  public String buffer = "";

  /**
   * Write to the output
   * @param value Value to write
   */
  @Override
  public void write(String value) {
    this.buffer += value;
  }

  /**
   * Flush the buffer
   */
  @Override
  public void flushBuffer() {
    content = buffer;
  }

  public void setBufferBehaviour(BufferBehaviour value) {
    // Empty, you can't do anything here
  }

  public BufferBehaviour getBufferBehaviour() {
    return null;
  }

  @Override
  public void flushBufferNoNewline() {
    // TODO Auto-generated method stub
    content = buffer;
    
  }
}
