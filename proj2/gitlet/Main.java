package gitlet;

import java.util.Date;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Leon
 */
public class Main {
    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(-1);
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                Repository.setupPersistence();
                break;

            case "add":
                validateNumArgs("add", args, 2);
                Repository.addFile(args[1]);
                break;

            case "commit":
                validateNumArgs("commit", args, 2);
                Repository.makeCommit(args[1], new Date());
                break;
            case "rm":
                // rm [file name]
                break;

            case "log":
                Repository.printLog();
                break;
            case "global-log":
                break;
            case "find":
                // find [commit message]
                break;
            case "status":
                // modification and untracked
                break;

            case "checkout":
                if (args.length == 3 && args[1].equals("--")) {
                    Repository.checkout(args[2]);
                }
                else if (args.length == 4 && args[2].equals("--")) {
                    Repository.checkout(args[1], args[3]);
                }
                else if (args.length == 2) {
                    // checkout [branch name]
                }
                break;
            case "branch":
                // branch [branch name]
                // create a new branch with the given name
                break;
            case "rm-branch":
                // rm-branch [branch name]
                break;
            case "reset":
                // reset [commit id]
                break;
            case "merge":
                // merge [branch name]
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(-1);
        }
    }

    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                    String.format("Invalid number of arguments for: %s.", cmd));
        }
    }
}
