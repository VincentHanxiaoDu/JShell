package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import driver.Directory;
import driver.File;
import driver.Node;
import org.junit.Test;

public class FileTest {
  File file;
  @Before
  public void initializeFile() {
    file = new File("newFile");
  }
  @Test
  public void testShouldSetFileNameCorrectly() {
    assertEquals(file.getName(), "newFile");
  }
  
  @Test
  public void testCreateDanglingFile() {
    Node parent = new Directory("parent");
    file.setProvisionalParent(parent);
    assertTrue(file.isDangling());
  }
  
  @Test
  public void testPromoteProvisionalParent() {
    Node parent = new Directory("parent");
    file.setProvisionalParent(parent);
    file.promoteProvisionalParent();
    assertTrue(!file.isDangling());
  }
  
  @Test
  public void testWriteToFile() {
    file.setContent("hello world");
    assertEquals(file.getContent(), "hello world");
  }
  
  @Test
  public void testAppendToFile() {
    file.setContent("hello");
    file.appendContent(" world");
    assertEquals(file.getContent(), "hello world");
  }
}