/**
 * Created by Dmitry Tishchenko on 24.06.15.
 */
public class ServerMessage {
    public ServerMessage(MessageType messageType, Object data) {
        this.messageType = messageType;
        this.data = data;
    }

    public enum MessageType {
        FilesList,
        ConnectionData
    }

    public final MessageType messageType;
    public final Object data;
}
