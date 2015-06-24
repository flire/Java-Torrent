import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dmitry Tishchenko on 24.06.15.
 */
public class FileIndex {
    private final int N_PARTS;
    private final String baseDirectory;

    private ConcurrentHashMap<String, TorrentFile> index;
    private ConcurrentHashMap<String, FileAvailability> globalAvailability;

    public FileIndex(int N_PARTS, String baseDirectory) {
        index = new ConcurrentHashMap<>();
        globalAvailability = new ConcurrentHashMap<>();
        this.N_PARTS = N_PARTS;
        this.baseDirectory = baseDirectory;
    }

    public void register(FileDescription[] files) {
        for (FileDescription description: files) {
            try {
                PartedFile newFile = new PartedFile(description.hash,
                        description.size,
                        description.filename,
                        baseDirectory,
                        N_PARTS);
                index.put(description.hash, newFile);
                globalAvailability.put(description.hash, description.fileAvailability);
                System.out.println("Registered file "+description.filename);
            } catch (IOException e) {
                //TODO: log here
            }
        }
    }

    public void registerFullFile(String path) throws IOException {
        File file = new File(path);
        FullFile newFile = new FullFile(file.getName(), file.getParent(), N_PARTS);
        index.put(newFile.hash, newFile);
        globalAvailability.put(newFile.hash, new PartialFileAvailability(N_PARTS, true));
        System.out.println("Registered file "+newFile.getName());
    }

    public void updateLocally(FileDescription[] files) {
        for (FileDescription description: files) {
            TorrentFile file = index.get(description.hash);
        }
    }
}
