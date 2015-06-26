package fileutils;

import java.io.Serializable;

/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
public interface FileAvailability extends Serializable {
    public boolean isPartAvailable(int partNumber); //TODO: redesign the interface service!
    public boolean isFileAvailable();
}
