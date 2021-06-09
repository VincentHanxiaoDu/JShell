package output;

/**
 * Buffer bahaviour for outputs
 * Using enum for this since it makes no sense to use a class
 * But basically the same as
 * static int DEFAULT = 0;
 * static int AUTO_FLUSH = 1;
 */
public enum BufferBehaviour {
  // Default behaviour, user must manually flush the buffer
  DEFAULT,
  // Will automatically flush the buffer
  AUTO_FLUSH
}
