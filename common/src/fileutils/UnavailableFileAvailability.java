package fileutils;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class UnavailableFileAvailability implements FileAvailability {
    @Override
    public boolean isPartAvailable(int partNumber) {
        return false;
    }

    @Override
    public boolean isFileAvailable() {
        return false;
    }
}
