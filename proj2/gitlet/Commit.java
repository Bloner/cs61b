package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  commit the file
 *  does at a high level.
 *
 *  @author Leon
 */
public class Commit implements Serializable {
    /**
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    static final File COMMIT_DIR = join(new File(System.getProperty("user.dir")), ".gitlet", "commit");
    String message;
    Date timeStamp;
    String parentId;
    String secondParentId;
    HashMap<String, String> map;

    public Commit(String m, Date d) {
        this.message = m;
        this.timeStamp = d;
        this.map = new HashMap<>();
    }

    public Commit(String m, Date d, String p, HashMap<String, String> mp) {
        this.message = m;
        this.timeStamp = d;
        this.parentId = p;
        this.map = mp;
    }

    public String saveCommit() {
        String hash = sha1(timeStamp.toString());
        File outFile = join(COMMIT_DIR, hash);
        writeObject(outFile, this);
        return hash;
    }

    public static Commit fromFile(File inFile) {
        return readObject(inFile, Commit.class);
    }

    public String toLocalDate() {
        String res = this.timeStamp.toString();
        if (res.charAt(8) == '0') {
            res  = res.replaceFirst("0", "");
        }
        res = res.replace("HKT ", "");
        return res + " +0800";
    }
}
