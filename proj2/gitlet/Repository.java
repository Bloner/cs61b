package gitlet;

import java.io.File;
import java.util.*;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  many class
 *  does at a high level.
 *
 *  @author Leon
 */
public class Repository {
    /**
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    public static final File BLOB_DIR = join(GITLET_DIR, "blob");

    public static final File COMMIT_DIR = join(GITLET_DIR, "commit");


    public static final File STAGE_ADD_DIR = join(GITLET_DIR, "stage", "add");

    public static final File STAGE_DEL_DIR = join(GITLET_DIR, "stage", "del");

    public static final File BRANCH_DIR = join(GITLET_DIR, "refs", "local");

    public static final File HEAD = join(GITLET_DIR, "Head");

    public static void setupPersistence() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(-1);
        } else {
            GITLET_DIR.mkdir();
            BLOB_DIR.mkdir();
            COMMIT_DIR.mkdir();
            STAGE_ADD_DIR.mkdirs();
            STAGE_DEL_DIR.mkdirs();

            BRANCH_DIR.mkdirs();

            Commit initialCommit = new Commit("initial commit", new Date(0));
            initialCommit.saveCommit();
            writeContents(HEAD, "refs/local/Master");
            writeContents(join(BRANCH_DIR, "Master"), initialCommit.getId());
        }
    }

    public static void makeCommit(String message, Date date) {
        if (message.equals("")) {
            System.out.println("Please enter a commit message.");
            return;
        }
        List<String> stageFileNameList = plainFilenamesIn(STAGE_ADD_DIR);
        if (stageFileNameList.size() == 0) {
            System.out.println("No changes added to the commit.");
            return;
        }
        //create new commit object
        File branch = join(GITLET_DIR, readContentsAsString(HEAD));
        String currentCommitId = readContentsAsString(branch);
        Commit currentCommit = Commit.fromFile(join(COMMIT_DIR, currentCommitId));
        Commit newCommit = new Commit(message, date, currentCommitId , new HashMap<String, String>(currentCommit.getMap()));
        //copy and paste blob from stage to blob folder and update the hashmap;
        for (String fileId: stageFileNameList) {
            File stageFile = join(STAGE_ADD_DIR, fileId);
            Blob b = Blob.fromFile(stageFile);
            b.saveBlobInBlobDir();
            String fileName = b.getFileName();
            newCommit.getMap().put(fileName, fileId);
            stageFile.delete();
        }
        //serialize the commit into the commit folder and update the branch content.
        String newCommitHash = newCommit.saveCommit();
        writeContents(branch, newCommitHash);
    }

    public static void addFile(String fileName) {
        File addFile = join(CWD, fileName);
        if (addFile.exists()) {
            //create a blob object of this file
            Blob b = new Blob(addFile);

            //get the hashmap of the currentCommit
            File branch = join(GITLET_DIR, readContentsAsString(HEAD));
            String currentCommitId = readContentsAsString(branch);
            HashMap<String, String> currentCommitMap = Commit.fromFile(join(COMMIT_DIR, currentCommitId)).getMap();

            //check if the file exist in the hashmap
            String fileIdInMap = currentCommitMap.getOrDefault(b.getFileName(),"None");
            if (fileIdInMap.equals("None") || !fileIdInMap.equals(b.getID())) {
                b.saveBlobInFile(join(STAGE_ADD_DIR, b.getID()));
            } else {
                String ansHash = Blob.fromFile(join(BLOB_DIR, fileIdInMap)).getID();
                if (ansHash.equals(b.getID())){
                    join(STAGE_ADD_DIR, ansHash).delete();
                }
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    public static void printLog() {
        HashMap<String, File> helper = new HashMap<>();
        List<String> filesInCommitFolder = plainFilenamesIn(COMMIT_DIR);
        for (String each: filesInCommitFolder) {
            helper.put(each, join(COMMIT_DIR, each));
        }
        File branch = join(GITLET_DIR, readContentsAsString(HEAD));
        String commitHash = readContentsAsString(branch);
        while (true){
            File commitFile = helper.get(commitHash);
            Commit commitObj = Commit.fromFile(commitFile);
            commitObj.printLog();
            commitHash = commitObj.getParentId();
            if (commitHash == null || commitHash.equals("")) {
                break;
            }
        }
    }

    public static void checkout(String fileName) {
        File branch = join(GITLET_DIR, readContentsAsString(HEAD));
        String commitHash = readContentsAsString(branch);
        checkout(commitHash, fileName);
    }

    public static void checkout(String hash, String fileName) {
        List<String> filesInCommitFolder = plainFilenamesIn(COMMIT_DIR);
        List<String> commitPool = new ArrayList<>();
        for (String each: filesInCommitFolder) {
            if(each.equals(hash)) {
                commitPool.add(each);
            }
        }
        if (commitPool.size() == 1) {
            File commitFile = join(COMMIT_DIR, hash);
            Commit commitObj = Commit.fromFile(commitFile);
            String fileHash = commitObj.getMap().getOrDefault(join(CWD, fileName).toString(), "None");
            if (fileHash.equals("None")) {
                System.out.println("File does not exist in that commit.");
            } else {
                Blob fileInBlob = Blob.fromFile(join(BLOB_DIR, fileHash));
                writeContents(join(CWD, fileName), fileInBlob.getContent());
            }
        }
        else if (commitPool.size() == 0) {
            System.out.println("No commit with that id exists.");
        }
    }

    public static void checkoutBranch(String branchName) {
        boolean checkRes = checkBranchExistsAndIsCurrentBranch(branchName,
                "No need to checkout the current branch.", "No such branch exists.");
        if (checkRes) {
            //check untracked
            //file once in the branch, changed now, but not tracked in the current branch; checkout will lose the change

            //delete files in working dir, which was tracked in prev branch but not cur branch

            //change branch from prev to cur
            writeContents(HEAD, readContentsAsString(join(BRANCH_DIR, branchName)));

            //clear stage
            removeAllFilesInFolder(STAGE_ADD_DIR);
            removeAllFilesInFolder(STAGE_DEL_DIR);
        }
        //a working file is untracked in the current branch and would be overwritten by the checkout
        //>>There is an untracked file in the way; delete it, or add and commit it first.
        //all file in the branchName -> working dir
        //delete file that tracked in currentBranch but not in branchName
    }

    private static void removeAllFilesInFolder(File dirName) {
        if (dirName.exists()) {
            List<String> filesList = plainFilenamesIn(dirName);
            for (String eachFile: filesList) {
                join(dirName, eachFile).delete();
            }
        }
    }

    public static void printGlobalLog() {
        List<String> filesInCommitFolder = plainFilenamesIn(COMMIT_DIR);
        for (String eachCommitFile: filesInCommitFolder) {
            Commit commitObj = Commit.fromFile(join(COMMIT_DIR, eachCommitFile));
            commitObj.printLog();
        }
    }

    public static void remove(String fileName) {
        File rmFile = join(CWD, fileName);
        /*HashMap<String, String> index = readObject(join(GITLET_DIR, "stage"), HashMap.class);
        String fn = index.getOrDefault(rmFile.toString(), "None");
        if (!fn.equals("None")) {
            join(STAGE_ADD_DIR, fn).delete();
        }
        else {

        }
        */
    }

    public static void findCommitIdThatHasTheMessage(String message) {
        List<String> filesInCommitFolder = plainFilenamesIn(COMMIT_DIR);
        int count = 0;
        for (String eachCommitFile: filesInCommitFolder) {
            Commit commitObj = Commit.fromFile(join(COMMIT_DIR, eachCommitFile));
            if (commitObj.getMessage().equals(message)) {
                System.out.println(commitObj.getId());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Found no commit with that message.");
        }
    }

    public static void createBranch(String branchName) {
        File currentBranch = join(GITLET_DIR, readContentsAsString(HEAD));
        HashSet<String> branchSet = new HashSet<>(plainFilenamesIn(BRANCH_DIR));
        if (branchSet.contains(branchName)) {
            System.out.println("A branch with that name already exists.");
        }
        else {
            writeContents(join(BRANCH_DIR, branchName), readContentsAsString(currentBranch));
        }
    }

    public static void removeBranch(String branchName) {
        Boolean checkRes = checkBranchExistsAndIsCurrentBranch(branchName,
                "Cannot remove the current branch.", "A branch with that name does not exist.");
        if (checkRes) {
            File toBeRemovedBranch = join(BRANCH_DIR, branchName);
            toBeRemovedBranch.delete();
        }
    }

    private static boolean checkBranchExistsAndIsCurrentBranch(String branchName, String msg1, String msg2) {
        String currentBranch = readContentsAsString(HEAD);
        if (currentBranch.equals("refs/local/" + branchName)) {
            System.out.println(msg1);
            return false;
        }
        if (!join(BRANCH_DIR, branchName).exists()) {
            System.out.println(msg2);
            return false;
        }
        return true;
    }

    public static void showStatus() {

    }
}
