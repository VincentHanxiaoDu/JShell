// **********************************************************
// Assignment2:
// Student1: Chenhao Zou
// UTORID user_name: zouchenh
// UT Student #: 1004473269
// Author: Chenhao Zou
//
// Student2: 
// UTORID user_name: rahma421
// UT Student #: 1003536202
// Author: Kasra Rahmani
//
// Student3: Jun Zheng
// UTORID user_name: zhengj69
// UT Student #: 1002993246
// Author: Jun Zheng
//
// Student4:
// UTORID user_name: duhanxia
// UT Student #: 1004439529
// Author: Hanxiao Du
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import command.Shell;

/**
 * Program entry point
 */
public class JShell {

  /**
   * Runs a new shell
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    Shell shell = Shell.createShellWithNewEnvironment();
    shell.run();
  }

}
