package driver;

import driver.exceptions.NodeAlreadyExistsException;

/**
 * Contains a plain file and its contents
 *
 */
public class File extends Node {

  /**
   * content of the file
   */
  private String content = "";

  /**
   * A provisional parent, if is set, this file is dangling
   */
  private Node provisionalParent;

  /**
   * Constructor for a basic File
   */
  public File(String inputName) {
    super(inputName, "File");
  }

  /**
   * Set a provisional parent for this file
   *
   * @param parent Provisional parent
   */
  public void setProvisionalParent(Node parent) {
    this.provisionalParent = parent;
  }

  /**
   * Return true if this file has a provisional parent A dangling file is added
   * to tree once you write to it
   *
   * @return If this file is dangling
   */
  public boolean isDangling() {
    return this.provisionalParent != null;
  }

  /**
   * Change provisional parent to actual parent
   */
  public void promoteProvisionalParent() {
    setParent(provisionalParent);
    try {
      ((Directory) provisionalParent).addFile(this);
    } catch (NodeAlreadyExistsException e) {
      // Suppress the error
      // This will never happen
    }
    provisionalParent = null;
  }

  /**
   * Get current file's content
   *
   * @return File's content
   */
  public String getContent() {
    return this.content;
  }

  /**
   * Set current file's content
   *
   * @param content Content to set
   */
  public void setContent(String content) {
    if (isDangling()) {
      promoteProvisionalParent();
    }
    this.content = content;
  }

  /**
   * Append a string to current content
   *
   * @param content Content to append
   */
  public void appendContent(String content) {
    if (isDangling()) {
      promoteProvisionalParent();
    }
    this.content = this.getContent() + content;
  }

  /**
   * Get current provisional parent
   */
  public Node getProvisionalParent() {
    return provisionalParent;
  }
}
