package messages.leecher;

import java.io.Serializable;

/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
public class LeecherMessage implements Serializable {
    public final String hash;
    public final int part;

    public LeecherMessage(String hash, int part) {
        this.hash = hash;
        this.part = part;
    }
}
