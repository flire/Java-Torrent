package messages.server;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class PartStatusUpdateServerMessage extends ServerMessage {
    public final String hash;
    public final int partNumber;
    public final boolean isAvailable;

    public PartStatusUpdateServerMessage(String hash, int partNumber, boolean isAvailable) {
        super(MessageType.PartStatusUpdate);
        this.hash = hash;
        this.partNumber = partNumber;
        this.isAvailable = isAvailable;
    }


}
