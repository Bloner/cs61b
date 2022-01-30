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

    public static final File STAGE_DIR = join(GITLET_DIR, "stage");

    public static final File HEAD = join(GITLET_DIR, "Head");

    public static final File MASTER_BRANCH = join(GITLET_DIR, "Master");//装的是master最近的commit的hash值

    public static void setupPersistence() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(-1);
        }
        else {
            GITLET_DIR.mkdir();
            BLOB_DIR.mkdir();
            COMMIT_DIR.mkdir();
            STAGE_DIR.mkdir();
            writeContents(HEAD, "Master");
            writeContents(MASTER_BRANCH, sha1(new Date(0) + "initial commit"));
            makeCommit("initial commit", new Date(0));
        }
    }

    public static void makeCommit(String message, Date date) {
        if (message.equals("")) {
            System.out.println("Please enter a commit message.");
            return;
        }
        if (date.equals(new Date(0))) {
            Commit newCommit = new Commit(message, date);
            newCommit.saveCommit();
            return;
        }
        List<String> stageFileNameList = plainFilenamesIn(STAGE_DIR);
        if (stageFileNameList.size() == 0) {
            System.out.println("No changes added to the commit.");
            return;
        }
        //create new commit object
        File branch = join(GITLET_DIR, readContentsAsString(HEAD));
        String currentCommitHash = readContentsAsString(branch);
        Commit currentCommit = Commit.fromFile(join(COMMIT_DIR, currentCommitHash));
        Commit newCommit = new Commit(message, date, currentCommitHash , new HashMap<String, String>(currentCommit.map));
        //copy and paste blob from stage to blob folder and update the hashmap;
        for (String fileHash: stageFileNameList) {
            File stageFile = join(STAGE_DIR, fileHash);
            Blob b = Blob.fromFile(stageFile);
            b.saveBlob();
            String fileName = b.fileName;
            newCommit.map.put(fileName, fileHash);
            stageFile.delete();
        }
        //serialize the commit into the commit folder and update the branch content.
        String newCommitHash = newCommit.saveCommit();
        writeContents(branch, newCommitHash);// write back branch
    }

    public static void addFile(String name) {
        File addFile = join(CWD, name);
        if (addFile.exists()) {
            //create a blob object of this file
            Blob b = new Blob(addFile);

            //get the hashmap of the currentCommit
            File branch = join(GITLET_DIR, readContentsAsString(HEAD));
            String currentCommitHash = readContentsAsString(branch);
            HashMap<String, String> currentCommitMap = Commit.fromFile(join(COMMIT_DIR, currentCommitHash)).map;

            //check if the file exist in the hashmap
            String ans = currentCommitMap.getOrDefault(b.fileName,"None");
            if (ans.equals("None") || !ans.equals(b.hash)) {
                b.saveBlob(join(STAGE_DIR, b.hash));
            }
            else {
                String ansHash = Blob.fromFile(join(BLOB_DIR, ans)).hash;
                if (ansHash.equals(b.hash)){
                    join(STAGE_DIR, b.hash).delete();
                }
            }
        }
        else {
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
        int  n = 0;
        while (n < 4){
            File commitFile = helper.get(commitHash);
            Commit commitObj = Commit.fromFile(commitFile);
            String info = "";
            if (commitObj.secondParentId == null) {
                info = String.format("===\ncommit %s\nDate: %s\n%s\n",
                        commitHash, commitObj.toLocalDate(), commitObj.message);
            }
            else {
                info = String.format("===\ncommit %s\nMerge: %7s %7s\nDate: %s\n%s\n",
                        commitHash, commitObj.parentId, commitObj.secondParentId,
                        commitObj.toLocalDate(), commitObj.message);
            }
            System.out.println(info);
            commitHash = commitObj.parentId;
            if (commitHash == null || commitHash.equals("")) {
                break;
            }
            n++;
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
            String fileHash = commitObj.map.getOrDefault(join(CWD, fileName).toString(), "None");
            if (fileHash.equals("None")) {
                System.out.println("File does not exist in that commit.");
            }
            else {
                Blob fileInBlob = Blob.fromFile(join(BLOB_DIR, fileHash));
                writeContents(join(CWD, fileName), fileInBlob.getContent());
            }
        }
        else if (commitPool.size() == 0) {
            System.out.println("No commit with that id exists.");
        }
    }
}
