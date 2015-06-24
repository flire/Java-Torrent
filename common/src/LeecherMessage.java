/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
public class LeecherMessage {
    public enum MessageType {
        FileRequest,//data is file hash + part number
        FileAvailabilityUpdate, //data is file hash + part number
        FileStartSeeding, // data is file hash
        FileStopSeeding, //data is file hash
        FileRegister //data is file hash + file size + filename + file availability

    }

    public final MessageType messageType;
    public final Object data;

    LeecherMessage(MessageType messageType, Object data) {
        this.messageType = messageType;
        this.data = data;
    }
}
