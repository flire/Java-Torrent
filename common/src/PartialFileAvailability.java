import java.util.Arrays;

/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
public class PartialFileAvailability implements FileAvailability {
    public int getNumberOfParts() {
        return numberOfParts;
    }

    private final int numberOfParts;
    private final boolean availability[];
    public PartialFileAvailability(int numberOfParts, boolean isAvailable) {
        this.numberOfParts = numberOfParts;
        this.availability = new boolean[numberOfParts];
        Arrays.fill(availability, isAvailable);
    }
    public boolean isPartAvailable(int partNumber) {
        return availability[partNumber]; //TODO:check index!
    }
    public void setPartAvailable(int partNumber) {
        availability[partNumber] = true; //TODO:check index!
    }

    public float getPercentage() {
        int counter = 0;
        for (boolean isPartAvailable: availability) {
            if (isPartAvailable) {
                counter++;
            }
        }
        return (float)counter / numberOfParts;
    }
}
