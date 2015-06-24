import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
abstract public class TorrentFile {
    protected String hash;
    protected long size;
    protected String name;
    protected String baseDirectory;
    protected PartialFileAvailability partitioning;
    protected RandomAccessFile file;

    public String getHash() {
        return hash;
    }
    public String getName() {
        return name;
    }

    public boolean isPartAvailable(int partNumber) {
        return partitioning.isPartAvailable(partNumber);
    }

    protected String fullFilename() {
        return baseDirectory + File.pathSeparator + name;
    }

    protected long getOffset(int partNumber) {
        return partNumber * getPartSize(0);
    }

    protected long getPartSize(int partNumber) {
        int numberOfParts = partitioning.getNumberOfParts();
        long result = size / numberOfParts;
        if (partNumber == numberOfParts -1) { //lastPart
            result += size % numberOfParts;
        }
        return result;
    }

    public byte[] getPart(int partNumber) throws IOException {
        long partSize = getPartSize(partNumber);
        byte[] result = new byte[(int)partSize];
        file.readFully(result, (int)getOffset(partNumber), (int)partSize);
        return result;
    }

    abstract public void setPart(int partNumber, byte[] part) throws IOException; //TODO:remove this method

}
