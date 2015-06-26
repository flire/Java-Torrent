package messages.seeder;

import java.io.Serializable;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class SeederMessage implements Serializable {
    public enum Status {
        OK,
        FileUnavailable,
        PartUnavailable,
        PartInconsistent
    }

    public final String hash;
    public final int part;
    public final Status status;
    public final byte data[];

    public SeederMessage(String hash, int part, byte[] data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Data is empty!");
        }
        this.hash = hash;
        this.part = part;
        this.status = Status.OK;
        this.data = data;
    }

    public SeederMessage(String hash, int part, Status status) throws IllegalArgumentException {
        if (status == Status.OK) {
            throw new IllegalArgumentException("Data expected, because status is OK!");
        }
        this.hash = hash;
        this.part = part;
        this.status = status;
        this.data = null;
    }
}
