import java.util.Arrays;

/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
public class FilePartitioning {
    public int getNumberOfParts() {
        return numberOfParts;
    }

    private final int numberOfParts;
    private final boolean availability[];
    public FilePartitioning(int numberOfParts, boolean isAvailable) {
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
}
