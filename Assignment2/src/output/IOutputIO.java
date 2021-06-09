package output;

/**
 * OutputIO interface
 */
public interface IOutputIO {
  void write(String value);
  void flushBuffer();
  void flushBufferNoNewline();
  void setBufferBehaviour(BufferBehaviour behaviour);
  BufferBehaviour getBufferBehaviour();
}
