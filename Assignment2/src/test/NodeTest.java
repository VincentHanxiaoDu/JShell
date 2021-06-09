package test;

import static org.junit.Assert.assertEquals;

import driver.Node;
import org.junit.Test;

/**
 * Test for Node
 * Coverage report:
 * Class  100%
 * Method 100%
 * Line   100%
 */
public class NodeTest {

  @Test
  public void testShouldSetNodeNameCorrectly() {
    Node node = new Node("name", "File");
    assertEquals(node.getName(), "name");
  }

  @Test
  public void testShouldSetNodeAsFileCorrectly() {
    Node node = new Node("name", "File");
    assertEquals(node.isFile(), true);
  }

  @Test
  public void testShouldSetNodeAsDirectoryCorrectly() {
    Node node = new Node("name", "Directory");
    assertEquals(node.isDirectory(), true);
  }

  @Test
  public void testShouldReturnCorrectType() {
    Node node = new Node("name", "File");
    assertEquals(node.getType(), "File");
  }

  @Test
  public void testShouldReturnCorrectPathRootFile() {
    Node node = new Node("name", "File");
    assertEquals(node.getPath(), "name");
  }

  @Test
  public void testShouldReturnCorrectPathRootDirectory() {
    Node node = new Node("", "Directory");
    assertEquals(node.getPath(), "/");
  }

  @Test
  public void testShouldSetParentCorrectly() {
    Node node = new Node("", "Directory");
    Node parent = new Node("haha", "Directory");
    node.setParent(parent);
    assertEquals(node.getParent(), parent);
  }

  @Test
  public void testShouldInitializeWithNullAsParent() {
    Node node = new Node("", "File");
    assertEquals(node.getParent(), null);
  }

}
