package utils;

import java.io.ObjectInputStream;
import java.util.UUID;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class ClientInputStream {
    public final UUID id;
    public final ObjectInputStream input;

    public ClientInputStream(UUID id, ObjectInputStream input) {
        this.id = id;
        this.input = input;
    }
}
