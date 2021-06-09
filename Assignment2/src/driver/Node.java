package driver;

import java.io.Serializable;

/**
 * A node within the file system tree
 */
public class Node implements Serializable {
  // name of file/directory
  private String name;
  // file or directory
  private String type;
  // The parent node
  private Node parent;

  public Node(String inputName, String fileType) {
    name = inputName;
    type = fileType;
  }

  /**
   * Get the root of the tree which current node resides in
   *
   * @return Root of the tree
   */
  protected Node getRoot() {
    // Keep going until we hit null
    Node root = this;
    while (root.getParent() != null) {
      root = root.getParent();
    }
    return root;
  }

  /**
   * Set current node's parent
   *
   * @param parent New parent node
   */
  public void setParent(Node parent) {
    this.parent = parent;
  }

  /**
   * Get current node's parent
   *
   * @return The parent node
   */
  public Node getParent() {
    return this.parent;
  }

  /**
   * Get the name of current node
   *
   * @return Name of the current node
   */
  public String getName() {
    return name;
  }


  /**
   * Set the name of current node
   *
   * @return New name of the current node
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * TODO: Deprecate this method
   * Get type of the current node
   * Can be file or directory
   *
   * @return Type of current node
   */
  public String getType() {
    return type;
  }

  /**
   * Return absolute path of the current node
   *
   * @return The absolute path
   */
  public String getPath() {
    Node current = this;
    // Traverse back
    StringBuilder sb = new StringBuilder();
    while (current != null) {
      if (current.isDirectory()) {
        sb.insert(0, current.getName() + "/");
      } else {
        sb.insert(0, current.getName());
      }

      current = current.getParent();
    }
    return sb.toString();
  }

  /**
   * Return true if the node is a file, false otherwise
   *
   * @return If the node is a file
   */
  public boolean isFile() {
    return this.getType().equals("File");
  }

  /**
   * Return true if the node is a directory, false otherwise
   *
   * @return If the node is a directory
   */
  public boolean isDirectory() {
    return this.getType().equals("Directory");
  }

  /**
   * Override default comparator
   *
   * @param o Object to compare
   * @return True if nodes are equal
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Node)) {
      return false;
    }
    Node node = (Node) o;
    return node.getPath().equals(this.getPath());
  }
}