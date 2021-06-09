package util;

/**
 * Removes last n characters from a string
 * Can add more string utilities that may be needed in the future
 */
public class StringUtil {

  /**
   * Return a new string with n chars removed
   * @param s String
   * @param n n
   * @return New string
   */
  public static String removeLastNChars(String s, int n) {
    if(s.length() < n) {
      return "";
    }
    return s.substring(0, s.length() - n);
  }

}
