package test.mock;

import serialization.IObjectSerializer;

import java.util.HashMap;

/**
 * Mock serializer for testing
 */
public class MockObjectSerializer implements IObjectSerializer {

  // Just a store for objects, we don't really serialize them at all
  private HashMap<String, Object> objStore = new HashMap<>();

  /**
   * Just push the object to store.
   *
   * @param obj      Object to save
   * @param fileName File name to save
   */
  @Override
  public void serializeToFile(Object obj, String fileName) {
    // We don't really serialize...
    objStore.put(fileName, obj);
  }

  /**
   * Just return whatever is in the object store.
   *
   * @param fileName File name to save
   * @return Object you saved
   */
  @Override
  public Object deserializeFromFile(String fileName) {
    // We just return the object we stored within HashMap
    return objStore.get(fileName);
  }

  /**
   * Get current object store
   *
   * @return The object store.
   */
  public HashMap<String, Object> getObjStore() {
    return objStore;
  }
}
