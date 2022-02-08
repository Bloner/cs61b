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
    private String message;
    private Date timeStamp;
    private String parentId;
    private String secondParentId;
    private String id;
    private HashMap<String, String> map;

    public Commit(String m, Date d) {
        this.message = m;
        this.timeStamp = d;
        this.id = sha1(timeStamp.toString() + message);
        this.map = new HashMap<>();
    }

    public Commit(String m, Date d, String p, HashMap<String, String> mp) {
        this.message = m;
        this.timeStamp = d;
        this.id = sha1(timeStamp.toString() + message);
        this.parentId = p;
        this.map = mp;
    }

    public String saveCommit() {
        File outFile = join(COMMIT_DIR, id);
        writeObject(outFile, this);
        return id;
    }

    public static Commit fromFile(File inFile) {
        return readObject(inFile, Commit.class);
    }

    public String toLocalDate() {
        String res = this.timeStamp.toString();
        if (res.charAt(8) == '0') {
            res  = res.replaceFirst("0", "");
        }
        res = res.replace("GMT ", "");
        res = res.replace("HKT ", "");
        return res + " +0000";
    }

    public void printLog() {
        String info = "";
        if (secondParentId == null) {
            info = String.format("===\ncommit %s\nDate: %s\n%s\n",
                    id, toLocalDate(), message);
        }
        else {
            info = String.format("===\ncommit %s\nMerge: %7s %7s\nDate: %s\n%s\n",
                    id, parentId, secondParentId, toLocalDate(), message);
        }
        System.out.println(info);
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public String getSecondParentId() {
        return secondParentId;
    }
}
