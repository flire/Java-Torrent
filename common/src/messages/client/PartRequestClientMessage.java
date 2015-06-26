package messages.client;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class PartRequestClientMessage extends ClientMessage {
    public PartRequestClientMessage(String hash, int part) {
        super(MessageType.PartRequest);
        this.hash = hash;
        this.part = part;
    }

    public final String hash;
    public final int part;
}
