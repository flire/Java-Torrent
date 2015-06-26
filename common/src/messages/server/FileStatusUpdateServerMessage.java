package messages.server;

import fileutils.FileDescription;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class FileStatusUpdateServerMessage extends ServerMessage {
    public final int numberOfParts;
    public final FileDescription files[];
//    public final boolean isAvailable; //FileAvailability part of service
    FileStatusUpdateServerMessage(int numberOfParts, FileDescription files[]) {
        super(MessageType.FileStatusUpdate);
        this.numberOfParts = numberOfParts;
        this.files = files;
    }
}
