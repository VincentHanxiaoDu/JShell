package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import util.StringUtil;

/**
 * Test for StringUtil
 * Coverage report:
 * Class  100%
 * Method 100%
 * Line   100%
 */
public class StringUtilTest {

  @Test
  public void testRemoveLastNCharsShouldWorkWithStringWithMoreThanNChars() {
    String s = "test123456";
    String result = StringUtil.removeLastNChars(s, 4);
    assertEquals(result, "test12");
  }

  @Test
  public void testRemoveLastNCharsShouldWorkWithStringWithLessThanNChars() {
    String s = "123";
    String result = StringUtil.removeLastNChars(s, 4);
    assertEquals(result, "");
  }

  @Test
  public void testRemoveLastNCharsShouldWorkWithStringWithNChars() {
    String s = "123";
    String result = StringUtil.removeLastNChars(s, 3);
    assertEquals(result, "");
  }

}
