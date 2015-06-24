/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
public interface FileAvailability {
    public boolean isPartAvailable(int partNumber);
    public float getPercentage();
}
