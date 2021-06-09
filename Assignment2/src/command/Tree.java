package command;

import command.builder.CommandBuilder;
import driver.Node;
import util.IParameters;
import util.Traverse;
import output.StdOutputIO;
import util.exceptions.InvalidNumberOfArgumentsException;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Prints the entire file system in a tree format
 *
 */
public class Tree extends Command {

  /**
   * Construct the tree command
   *
   * @param params Parameters
   */
  public Tree(IParameters params) {
    super(params);
  }

  /**
   * Constructor used to inject dependencies
   *
   * @param builder Builder to use
   */
  private Tree(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new instance with builder
   *
   * @param builder Builder to use
   * @return Tree instance
   */
  public static Tree createWithBuilder(CommandBuilder builder) {
    return new Tree(builder);
  }

  /**
   * Run the tree command tree
   */
  @Override
  protected void execute() throws InvalidNumberOfArgumentsException {
    getParams().assertArgumentLength(0);
    Traverse traverse = new Traverse(this.getEnvironment());
    Iterator<Node> iterator = traverse.iterator();
    HashMap<Node, String> incrementTracker = new HashMap<>();
    incrementTracker.put(iterator.next(), "");
    String outPutTree = "\\\n";
    // printToOut("\\");
    while (iterator.hasNext()) {
      Node currentNode = iterator.next();
      incrementTracker.put(currentNode,
          incrementTracker.get(currentNode.getParent()) + " ");
      outPutTree += (incrementTracker.get(currentNode)
              + currentNode.getName() + "\n");
    }
    outPutTree = outPutTree.substring(0, outPutTree.length() - 1);
    printToOut(outPutTree);
    flushOutput();
  }


  /**
   * Get tree's manual.
   *
   * @return Manual for tree command.
   */
  @Override
  public String getManual() {
    return "tree\n"
        + "====================================\n"
        + "Display the entire file system as a tree";
  }
}
