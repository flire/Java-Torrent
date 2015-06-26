package messages.server;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class ConnectionDataServerMessage extends ServerMessage {
    public final String host;
    public final int port;

    public ConnectionDataServerMessage(String host, int port) {
        super(MessageType.ConnectionData);
        this.host = host;
        this.port = port;
    }
}
