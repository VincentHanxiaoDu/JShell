package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import driver.Directory;
import driver.File;
import driver.Node;
import driver.exceptions.NodeAlreadyExistsException;
import environment.Environment;
import test.mock.MockOutputIO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.util.EnvironmentUtility;
import util.Traverse;

import java.util.ArrayList;

/**
 * Test suite for traverse
 */
public class TraverseTest {
  Environment env;
  MockOutputIO out;
  ArrayList<Node> allFiles = new ArrayList<Node>();

  @Before
  public void setup() {
    try {
      env = Environment.createSingleInstance();
      Directory root = new Directory("");
      allFiles.add(root);
      root.addFile(new Directory("test"));
      allFiles.add(root.getNodeByPathString("test"));
      ((Directory) root.getNodeByPathString("test")).addFile(new Directory("test3"));
      allFiles.add(root.getNodeByPathString("test/test3"));
      root.addFile(new File("sample"));
      allFiles.add(root.getNodeByPathString("sample"));
      root.addFile(new File("test2"));
      allFiles.add(root.getNodeByPathString("test2"));
      env.setCurrentDir(root);
      out = new MockOutputIO();
    } catch (NodeAlreadyExistsException e) {
      fail("NodeAlreadyExistsException");
    }
  }

  @After
  public void teardown() throws Exception {
    EnvironmentUtility.destroySingletonInstance();
    allFiles.clear();
  }

  @Test
  public void testTraverseIterations() {
    Traverse<Node> traverse = new Traverse<Node>(env);
    ArrayList<Node> traverseFiles = new ArrayList<Node>();

    for (Node i:traverse) {
      traverseFiles.add(i);
    }
    assertTrue(traverseFiles.containsAll(allFiles));
  }
}