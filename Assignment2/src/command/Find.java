package command;

import command.builder.CommandBuilder;
import driver.Node;
import environment.Environment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import util.IParameters;
import util.Traverse;

public class Find extends Command {

  private boolean validFlags = false;

  public Find(IParameters params) {
    super(params);
  }

  /**
   * Constructor used to inject dependencies
   *
   * @param builder Builder to use
   */
  private Find(CommandBuilder builder) {
    super(builder);
  }

  /**
   * Create a new find instance with builder
   * @param builder Builder to use
   * @return New find instance
   */
  public static Find createWithBuilder(CommandBuilder builder) {
    return new Find(builder);
  }

  @Override
  protected void execute() {
    IParameters params = this.getParams();
    Environment env = this.getEnvironment();
    if (params.getArguments().length >= 5) {
      Hashtable<String, String> flags = checkFlags(params);
      if (validFlags) {
        String[] args = params.getArguments();
        for (String i : Arrays.copyOfRange(args, 0, args.length - 4)) {
          Node target = env.getCurrentDir().getNodeByPathString(i);
          if (target == null || !target.isDirectory()) {
            printToErr(i + ": No such directory.");
          } else {
            int index = 1;
            String type =
                flags.get("-type").equals("d") ? "directories" : "files";
            printToOut("Search " + type + " in " + i + ":\n");
            for (Node node : getSearchResult(env, flags, i)) {
              String path = node.getPath();
              if (path.charAt(path.length() - 1) == '/') {
                path = path.substring(0, path.length() - 1);
              }
              printToOut(index + ". " + path + "\n");
              index++;
            }
            flushOutput();
          }
        }

      }
    } else {
      printToErr("Invalid number of arguments.");
    }
  }

  private ArrayList<Node> getSearchResult(Environment env,
      Hashtable<String, String> flags,
      String path) {
    ArrayList<Node> result = new ArrayList<>();
    Traverse<Node> traverse = new Traverse<>(env);
    traverse.setStartPath(env, path);
    if (flags.get("-type").equals("f")) {
      for (Node node : traverse) {
        if (node.isFile() && node.getName().equals(flags.get("-name"))) {
          result.add(node);
        }
      }
    } else if (flags.get("-type").equals("d")) {
      boolean isRoot = true;
      for (Node node : traverse) {
        if (!isRoot && node.isDirectory() && node.getName()
            .equals(flags.get("-name"))) {
          result.add(node);
        } else if (isRoot) {
          isRoot = false;
        }
      }
    }
    return result;
  }


  private Hashtable<String, String> checkFlags(IParameters params) {
    Hashtable<String, String> flags = new Hashtable<>();
    String[] args = params.getArguments();
    int i = args.length - 4;
    while (i < args.length) {
      if (args[i].equals("-type") && (i + 1 < args.length)) {
        if (args[i + 1].matches("[fd]")) {
          flags.put(args[i], args[i + 1]);
          i += 2;
        } else {
          printToErr("Invalid flag: -type " + args[i + 1]);
          return flags;
        }
      } else if (args[i].equals("-name") && (i + 1 < args.length)) {
        if (args[i + 1].matches("^\"(.*?)\"$")) {
          flags
              .put(args[i], args[i + 1].substring(1, args[i + 1].length() - 1));
          i += 2;
        } else {
          printToErr("Invalid flag: -name " + args[i + 1]);
          return flags;
        }
      } else {
        printToErr("Invalid flag: " + args[i]);
        return flags;
      }
    }
    if (flags.size() == 2) {
      this.validFlags = true;
    }
    return flags;
  }

  public String getManual() {
    return "find [DIR ...] -name \"[Name]\" -type [d/f]\n"
        + "====================================\n"
        + "Find all node named [Name] in type "
        + "[d(directory)/f(file)]";

  }
}
