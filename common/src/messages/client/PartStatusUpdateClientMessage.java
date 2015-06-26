package messages.client;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class PartStatusUpdateClientMessage extends ClientMessage { //same as Server!
    public final String hash;
    public final int partNumber;

    public PartStatusUpdateClientMessage(String hash, int partNumber) {
        super(MessageType.PartStatusUpdate);
        this.hash = hash;
        this.partNumber = partNumber;
    }
}
