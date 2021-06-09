package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import environment.Stack;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Stack
 * Coverage report:
 * Class  100%
 * Method 100%
 * Line   100%
 */
public class StackTest {

  Stack<String> stack;

  @Before
  public void initializeStack() {
    stack = new Stack<>();
  }

  @Test
  public void testShouldInitializeWithSizeZero() {
    assertEquals(stack.getSize(), 0);
  }

  @Test
  public void testShouldReturnCorrectSize() {
    stack.push("1");
    stack.push("2");
    stack.push("3");
    assertEquals(stack.getSize(), 3);
  }

  @Test
  public void testPopShouldPopLastElementAndDecreaseSizeByOne() {
    stack.push("1");
    stack.push("2");
    String elem = stack.pop();
    assertEquals(stack.getSize(), 1);
    assertEquals(elem,  "2");
  }

  @Test
  public void testPeekShouldReturnLastElementAndPreserveSize() {
    stack.push("1");
    stack.push("2");
    String elem = stack.peek();
    assertEquals(stack.getSize(), 2);
    assertEquals(elem, "2");
  }

  @Test
  public void testEmptyPopShouldReturnNullAndPreserveZeroSize() {
    stack.push("1");
    stack.push("2");
    stack.push("3");
    stack.pop();
    stack.pop();
    stack.pop();
    String elem = stack.pop();
    assertEquals(stack.getSize(), 0);
    assertNull(null);
  }

  @Test
  public void testEmptyPeekShouldReturnNullAndPreserveZeroSize() {
    String elem = stack.peek();
    assertEquals(stack.getSize(), 0);
    assertNull(elem);
  }

  @Test
  public void testEmptyStackShouldReturnEmptyArrayOfStrings() {
    Object[] list = stack.toArray();
    assertEquals(list.length, 0);
  }

  @Test
  public void testStackShouldReturnArrayOfStringsWithSameSizeAndElements() {
    stack.push("1");
    stack.push("2");
    Object[] list = stack.toArray();
    assertEquals(list[0], "2");
    assertEquals(list[1], "1");
    assertEquals(list.length, stack.getSize());
  }

}
