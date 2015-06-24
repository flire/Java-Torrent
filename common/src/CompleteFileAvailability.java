/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
public class CompleteFileAvailability implements FileAvailability {
    @Override
    public boolean isPartAvailable(int partNumber) {
        return true;
    }

    @Override
    public float getPercentage() {
        return 1.0f;
    }
}
