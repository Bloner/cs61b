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
                if (Repository.GITLET_DIR.exists()) {
                    validateNumArgs("add", args, 2);
                    Repository.addFile(args[1]);
                }
                break;

            //To Do
            case "commit":
                if (Repository.GITLET_DIR.exists()) {
                    validateNumArgs("commit", args, 2);
                    Repository.makeCommit(args[1], new Date());
                }
                break;

            //To Do
            case "rm":
                if (Repository.GITLET_DIR.exists()) {
                    validateNumArgs("rm", args, 2);
                    Repository.remove(args[1]);
                }
                break;

            case "log":
                if (Repository.GITLET_DIR.exists()) {
                    Repository.printLog();
                }
                break;

            case "global-log":
                if (Repository.GITLET_DIR.exists()) {
                    Repository.printGlobalLog();
                }
                break;

            case "find":
                if (Repository.GITLET_DIR.exists()) {
                    validateNumArgs("find", args, 2);
                    Repository.findCommitIdThatHasTheMessage(args[1]);
                }
                break;

            //To Do
            case "status":
                if (Repository.GITLET_DIR.exists()) {
                    validateNumArgs("status", args, 1);
                    Repository.showStatus();
                }
                break;

            //To Do
            case "checkout":
                if (Repository.GITLET_DIR.exists()) {
                    if (args.length == 3 && args[1].equals("--")) {
                        Repository.checkout(args[2]);
                    }
                    else if (args.length == 4 && args[2].equals("--")) {
                        Repository.checkout(args[1], args[3]);
                    }
                    else if (args.length == 2) {
                        Repository.checkoutBranch(args[1]);// checkout [branch name]
                    }
                }
                break;

            case "branch":
                if (Repository.GITLET_DIR.exists()) {
                    validateNumArgs("branch", args, 2);
                    Repository.createBranch(args[1]);
                }
                break;

            case "rm-branch":
                if (Repository.GITLET_DIR.exists()) {
                    validateNumArgs("rm-branch", args, 2);
                    Repository.removeBranch(args[1]);
                }
                break;

            //To Do
            case "reset":
                // reset [commit id]
                break;

            //To Do
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
