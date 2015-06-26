package fileutils;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
public class PartedFile extends TorrentFile {
    private RandomAccessFile file;
    public PartedFile(String hash, long size, String name, String baseDirectory, int numberOfParts) throws IOException {
        this.hash = hash;
        this.size = size;
        this.name = name;
        this.baseDirectory = baseDirectory;
        this.numberOfParts = numberOfParts;
        this.partitioning = new PartialFileAvailability(numberOfParts, false);
        file = new RandomAccessFile(fullFilename(),"rw");
        file.setLength(size);
    }

    public void setPart(int partNumber, byte[] part) throws IOException {
        file.write(part, (int)getOffset(partNumber), (int)getPartSize(partNumber));
        partitioning.setPartAvailable(partNumber);
    }

    @Override
    protected String fullFilename() {
        return super.fullFilename()+".part";
    }

    @Override
    public float getPercentage() {
        int counter = 0;
        for (int i = 0; i < numberOfParts;i++) {
            if (isPartAvailable(i)) {
                counter++;
            }
        }
        return (float)counter / numberOfParts;
    }
}
