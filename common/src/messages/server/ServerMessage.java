package messages.server;

import java.io.Serializable;

/**
 * Created by Dmitry Tishchenko on 24.06.15.
 */
public class ServerMessage implements Serializable {
    public ServerMessage(MessageType messageType) {
        this.messageType = messageType;
    }

    public enum MessageType {
        ConnectionData,
        FileStatusUpdate,
        PartStatusUpdate
    }

    public final MessageType messageType;
}
