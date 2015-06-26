package messages.client;

import fileutils.FileDescription;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class FileStatusUpdateClientMessage extends ClientMessage {
    public final FileDescription files[];

    public FileStatusUpdateClientMessage(FileDescription[] files) {
        super(MessageType.FileStatusUpdate);
        this.files = files;
    }
}
