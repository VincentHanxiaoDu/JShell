package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import driver.exceptions.NodeAlreadyExistsException;
import java.util.ArrayList;
import driver.Directory;
import driver.Node;
import org.junit.Test;

public class DirectoryTest {
  
  @Test
  public void testShouldSetDirectoryNameCorrectly() {
    Directory directory = new Directory("testDir");
    assertEquals(directory.getName(), "testDir");
  }
  
  @Test
  public void testFindDirectoryGivenName() {
    Node directory = new Directory("findMe");
    assertEquals(((Directory) directory).getNodeByPathString("."), directory);
  }
  
  @Test
  public void testAddFileToDirectory() {
    try{
    Node parentDirectory = new Directory("parent");
    Node childDirectory = new Directory("child");
    ((Directory) parentDirectory).addFile(childDirectory);
    assertEquals(((Directory) parentDirectory).getNodeByPathString("child"),
        childDirectory);
    }catch (NodeAlreadyExistsException e) {
      fail("NodeAlreadyExistsException");
    }
  }
  
  @Test
  public void testGetFilesInsideDirectory() {
    try {
    Node parentDirectory = new Directory("parent");
    Node childDirectory = new Directory("child");
    Node childDirectory2 = new Directory("child2");
    ((Directory) parentDirectory).addFile(childDirectory);
    ((Directory) parentDirectory).addFile(childDirectory2);
    ArrayList<Node> files = new ArrayList<>();
    files.add(childDirectory);
    files.add(childDirectory2);
    assertEquals(((Directory) parentDirectory).getFiles(), files);
    }catch (NodeAlreadyExistsException e) {
      fail("NodeAlreadyExistsException");
    }
  }
  
  @Test
  public void testRemoveFileFromDirectory() {
    try {
    Node parentDirectory = new Directory("parent");
    Node childDirectory = new Directory("child");
    ((Directory) parentDirectory).addFile(childDirectory);
    ((Directory) parentDirectory).removeFile(childDirectory);
    ArrayList<Node> files = new ArrayList<>();
    assertEquals(((Directory) parentDirectory).getFiles(), files);
    }catch (NodeAlreadyExistsException e) {
      fail("NodeAlreadyExistsException");
    }
  }
}