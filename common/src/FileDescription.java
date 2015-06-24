/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
public class FileDescription {
    public final String hash;
    public final long size;
    public final String filename;
    public final FileAvailability fileAvailability;

    public FileDescription(String hash, long size, String filename, FileAvailability fileAvailability) {
        this.hash = hash;
        this.size = size;
        this.filename = filename;
        this.fileAvailability = fileAvailability;
    }
}
