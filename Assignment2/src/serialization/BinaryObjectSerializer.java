package serialization;

import java.io.*;

/**
 * Serialize an object to a binary file
 */
public class BinaryObjectSerializer implements IObjectSerializer {
  /**
   * Serialize an object and write to a file.
   *
   * @param obj Object to serialize
   * @param fileName File to store the serialized content
   * @throws IOException
   */
  @Override
  public void serializeToFile(Object obj, String fileName)
          throws IOException {
    FileOutputStream fileOut = new FileOutputStream(fileName);
    ObjectOutputStream out = new ObjectOutputStream(fileOut);
    out.writeObject(obj);
    out.close();
    fileOut.close();
  }

  /**
   * Read an object from file
   * @param fileName File to read from
   * @return Deserialized object
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Override
  public Object deserializeFromFile(String fileName)
          throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream(fileName);
    ObjectInputStream in = new ObjectInputStream(fileIn);
    Object obj = in.readObject();
    in.close();
    fileIn.close();
    return obj;
  }
}
