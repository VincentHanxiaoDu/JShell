package environment;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * A regular stack class
 */
public class Stack<T> implements Serializable {


  /**
   * implement Stack with an LinkedList
   */
  private LinkedList<T> stack;

  // default constructor
  public Stack() {
    // initialize the stack as an empty LinkedList
    this.stack = new LinkedList<>();
  }

  /**
   * Push an element to the stack.
   *
   * @param element The string element to push.
   */
  public void push(T element) {
    // add the element to the top
    this.stack.add(0, element);
  }

  /**
   * Get current size size
   *
   * @return stack size
   */
  public int getSize() {
    return stack.size();
  }

  /**
   * Pop a string element from the stack.
   *
   * @return The top element of the stack, or null if there is no elements in
   * the stack.
   */
  public T pop() {
    if (!this.stack.isEmpty()) {
      // remove and return the top element
      return this.stack.removeFirst();
    }
    // return null if there is no element
    return null;
  }

  /**
   * Return the top element of the stack, or null if there is no element in the
   * stack.
   *
   * @return The top element of the stack.
   */
  public T peek() {
    if (!this.stack.isEmpty()) {
      // return the top element
      return this.stack.getFirst();
    }
    // return null if there is no element
    return null;
  }

  /**
   * Return an array representation of the reversed stack
   *
   * @return An array representation of the reversed stack
   */
  public Object[] toArray() {
    return this.stack.toArray();
  }

  public T[] toArray(T[] array) {
    return this.stack.toArray(array);
  }
}