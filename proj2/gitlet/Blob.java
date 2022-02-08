package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

public class Blob implements Serializable {
    private byte[] content;
    private String fileName; //absolute path
    private String id;

    public Blob(File f) {
        this.content = readContents(f);
        this.fileName = f.toString();
        this.id = sha1(readContentsAsString(f) + fileName);
    }

    public String saveBlobInBlobDir() {
        File outFile = join(new File(System.getProperty("user.dir")), ".gitlet", "blob", id);
        writeObject(outFile, this);
        return id;
    }

    public String saveBlobInFile(File f) {
        writeObject(f, this);
        return id;
    }

    public static Blob fromFile(File infile) {
        return readObject(infile, Blob.class);
    }

    public byte[] getContent() {
        return content;
    }

    public String getFileName() {
        return fileName;
    }

    public String getID() {
        return id;
    }
}
