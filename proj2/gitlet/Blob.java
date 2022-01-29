package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

public class Blob implements Serializable {
    static final File BLOB_DIR = join(new File(System.getProperty("user.dir")), ".gitlet", "blob");
    private byte[] content;
    public String fileName;
    public String hash;

    public Blob(File f) {
        this.content = readContents(f);
        this.fileName = f.toString();
        this.hash = sha1(readContentsAsString(f) + fileName);
    }

    public String saveBlob() {
        File outFile = join(BLOB_DIR, hash);
        writeObject(outFile, this);
        return hash;
    }

    public String saveBlob(File f) {
        writeObject(f, this);
        return hash;
    }

    public static Blob fromFile(File infile) {
        return readObject(infile, Blob.class);
    }

    public byte[] getContent() {
        return content;
    }
}
