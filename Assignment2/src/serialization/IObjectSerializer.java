package serialization;

import java.io.IOException;

/**
 * Interface for classes that serializes an object
 */
public interface IObjectSerializer {
  public void serializeToFile(Object obj, String fileName)
          throws IOException;

  public Object deserializeFromFile(String fileName)
          throws IOException, ClassNotFoundException;
}
