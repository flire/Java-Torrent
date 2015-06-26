package messages.client;

import java.io.Serializable;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class ClientMessage implements Serializable {
    public ClientMessage(MessageType messageType) {
        this.messageType = messageType;
    }

    public enum MessageType {
        FileStatusUpdate,
        PartStatusUpdate,
        PartRequest,
        Disconnect
    }

    public final MessageType messageType;
}
