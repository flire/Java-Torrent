package fileutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
public class FullFile extends TorrentFile {
    public FullFile(String name, String baseDirectory, int numberOfParts) throws IOException {
        this.hash = calcHash();
        this.file = new RandomAccessFile(fullFilename(),"r");
        this.size = file.length();
        this.name = name;
        this.baseDirectory = baseDirectory;
        this.partitioning = new PartialFileAvailability(numberOfParts, true);
        file.setLength(size);
    }

    private String calcHash() throws IOException {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e);
        }
        FileInputStream fis = new FileInputStream(fullFilename());
        byte[] dataBytes = new byte[1024];

        int nread = 0;

        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }

        byte[] mdbytes = md.digest();

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    @Override
    public float getPercentage() {
        return 1.0f;
    }
}
