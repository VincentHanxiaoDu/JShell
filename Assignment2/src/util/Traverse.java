package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import driver.Directory;
import driver.Node;
import environment.Environment;


/**
 * A file traversal class which is defaulted to starting at the root
 * Allows the option of setting any directory as the starting point
 * Allows user to iterate through every single file in order.
 *
 * @param <E>
 */
public class Traverse<E> implements Iterable<Node>{
  /**
   * The node to start traversing from
   */
  private Node start;
  /**
   * Given the environment variable, sets start to be the root directory
   * @param env the Environment variabe
   */
  public Traverse(Environment env) {
    // get the root node
    start = env.getCurrentDir().getNodeByPathString("/");
    
  }
  
  /**
   * Sets the start node to path
   * @param env Environment the current environment variable
   * @param path String the path to set the starting node to
   */
  public void setStartPath(Environment env, String path) {
    start = env.getCurrentDir().getNodeByPathString(path);
  }

  /** 
   * Allows iteration through the nodes starting from start node
   */
  @Override
  public Iterator<Node> iterator() {
    return new FileWalker<Node>(start);
  }
  
  /**
   * The class that implements the Iterator
   *
   * @param <Node>
   */
  private static class FileWalker<Node> implements Iterator<driver.Node> {

    /**
     * The stack which holds the Nodes
     */
    Stack<driver.Node> stk;
    /**
     * initializes the stack by pushing the start node into it
     * @param start Node to start from
     */
    public FileWalker(driver.Node start) {
      stk = new Stack<driver.Node>();
      if (start != null) {
        stk.push(start);
      }
    }

    /** 
     * Checks if there is a next element
     */
    @Override
    public boolean hasNext() {
      return (!stk.isEmpty());
    }

    /** 
     * Returns the next element
     */
    @Override
    public driver.Node next() {
      driver.Node current = stk.pop();
      ArrayList<driver.Node> files = new ArrayList<driver.Node>();
      if (current.isDirectory()) {
        files = ((Directory) current).getFiles();
      }
      for (driver.Node file:files) {
        stk.push(file);
      }

      return current;
    }
    
  }
}