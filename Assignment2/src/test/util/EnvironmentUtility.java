package test.util;

import environment.Environment;
import java.lang.reflect.Field;

/**
 * Util functions for environments
 */
public class EnvironmentUtility {

  /**
   * Use reflection to teardown the environment singleton
   */
  public static final void destroySingletonInstance() throws Exception {
    // Use reflection to destroy the environment singleton
    Environment environment = Environment.createSingleInstance();
    Field field = (environment.getClass()).getDeclaredField("instance");
    field.setAccessible(true);
    field.set(null, null);
  }

}
