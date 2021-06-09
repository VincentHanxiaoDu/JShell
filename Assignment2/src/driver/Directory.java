package driver;

import driver.exceptions.NodeAlreadyExistsException;
import driver.exceptions.NodeDoesNotExistException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import util.StringUtil;

/**
 * A class representing directories within the file system tree
 */
public class Directory extends Node {
  // for the folders it'll hold
  private ArrayList<Node> nodes = new ArrayList<Node>();

  /**
   * Constructor for a basic Directory
   */
  public Directory(String inputName) {
    super(inputName, "Directory");
  }

  /**
   * Get a node by its name. IMPORTANT: Node maybe its parent node, this
   * is why the function is not called getChildNodeByName :)
   * - .. Represents parent node - . Represents this - others will
   * attempt match with Node.getName()
   *
   * @param name Name of the node you wish to find
   * @return Node if node is found, null if not found
   */
  private Node getNodeByName(String name) throws NodeDoesNotExistException {
    // Using switch in this case because it is faster. With if statements,
    // you will have to do each comparison one-by-one, however switch will
    // cause JVM to load a value
    // table to concurrently match cases.
    switch (name) {
      case "..":
        return this.getParent();
      case ".":
        return this;
      default:
        // Find the child
        for (Node node : this.nodes) {
          if (node.getName().equals(name)) {
            // Found the node, immediately return, no need to loop anymore
            return node;
          }
        }
        // If nothing is found, return null
        throw new NodeDoesNotExistException("Node " + name +
                " does not exist.");
    }
  }

  /**
   * Get node while allowing provisional parents
   *
   * @param name Name of the node
   * @return A existing node or dangling node
   */
  private Node getNodeByNameWithProvision(String name) {
    try {
      // If found, return it
      return getNodeByName(name);
    } catch (NodeDoesNotExistException e) {
      // If not found, create a provision node
      File file = new File(name);
      file.setProvisionalParent(this);
      return file;
    }
  }

  /**
   * Get a node by relative path O(N*M) where N is elements within path,
   * M is avg # of children
   *
   * @param path The relative path you wish to follow
   * @return Node if node is found, null if not found
   */
  public Node getNodeByRelativePathArrayList(ArrayList<String> path) {
    // If the path length is 0, return null
    if (path.size() == 0) {
      return null;
    }
    // Now we must have at least 1 element within the array, find the first one
    Node node = this.getNodeByNameWithProvision(path.get(0));
    // If we can't find the first one, there is no need to go deeper
    if (node == null) {
      return null;
    }
    // If we only have 1 element within the path, this is our base case 
    // for the recursive call
    if (path.size() == 1) {
      return node;
    } else {
      // If the node we got is not a directory, cannot proceed, return null
      if (!node.getType().equals("Directory")) {
        return null;
      } else {
        // Otherwise we can safely cast the node to directory
        Directory dir = (Directory) node;
        // Pop first item
        path.remove(0);
        return dir.getNodeByRelativePathArrayList(path);
      }
    }
  }

  /**
   * Takes a path, relative or absolute as input, returns the node
   * at that position
   *
   * @param path The path to follow
   * @return A node
   */
  public Node getNodeByPathString(String path) {
    if(path.length() == 0) {
      return null; // Invalid path, just return null
    }
    if (path.equals("/")) {
      return this.getRoot();
    } else {
      // Check if the last character is /, if it is, then remove it
      if (path.length() > 1 && path.charAt(path.length() - 1) == '/') {
        path = StringUtil.removeLastNChars(path, 1);
      }
      if (path.charAt(0) == '/') {
        // We have an absolute path, remove char, parse dir and get
        // relative from root
        path = path.substring(1);
        ArrayList<String> pathArray =
            new ArrayList<>(Arrays.asList(path.split("/")));
        return ((Directory) this.getRoot()).
            getNodeByRelativePathArrayList(pathArray);
      } else {
        // We have an relative path, don't remove char
        ArrayList<String> pathArray =
            new ArrayList<>(Arrays.asList(path.split("/")));
        return this.getNodeByRelativePathArrayList(pathArray);
      }
    }
  }

  /**
   * Adds a new file (file or directory) to the directory
   *
   * @param newFile Node file to be added
   */
  public void addFile(Node newFile) throws NodeAlreadyExistsException {
    // add the file to files list
    // check if file already exists, if it does don't add anything
    if (nameExists(this, newFile.getName())) {
      throw new NodeAlreadyExistsException(newFile.getName());
    }    newFile.setParent(this);
    nodes.add(newFile);
  }

  /**
   * removes a file or directory from the directory
   *
   * @param delFile Node which is the file to delete
   */
  public void removeFile(Node delFile) {
    if (nodes.contains(delFile)) {
      int toDelete = nodes.indexOf(delFile);
      nodes.remove(toDelete);
    }
  }

  /**
   * removes a file or directory from the directory
   *
   * @param delFileName the name of the Node which is the file to delete
   */
  public void removeFile(String delFileName) {
    int i = 0;
    while (i < this.getFiles().size()) {
      Node curr = this.getFiles().get(i);
      if (curr.getName().equals(delFileName)) {
        removeFile(curr);
        break;
      }
      i++;
    }
  }

  /**
   * Check if the name already exists
   * @param dir target directory
   * @param name name of the new file
   * @return true if it exists, false otherwise.
   */
  private boolean nameExists(Directory dir, String name) {
    for (Node i: dir.getFiles()) {
      if (i.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the list of files which are in the directory
   *
   * @return List of files which the directory holds
   */
  public ArrayList<Node> getFiles() {
    return nodes;
  }
}