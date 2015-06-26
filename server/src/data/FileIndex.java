package data;

import fileutils.FileAvailability;
import fileutils.FileDescription;
import fileutils.PartialFileAvailability;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dmitry Tishchenko on 23.06.15.
 */
public class FileIndex {
    private final int N_PARTS = 5;
    private class FileHolders {
        Set<UUID> partsHolders[];

        public FileHolders() {
            partsHolders = new Set[N_PARTS];
            for (int partIndex = 0; partIndex < N_PARTS; partIndex++) {
                partsHolders[partIndex] = new HashSet<>();
            }
        }

        public void register(UUID holder, FileAvailability availability) {
            for (int filePart = 0; filePart < N_PARTS; filePart++) {
                if (availability.isPartAvailable(filePart)) {
                    partsHolders[filePart].add(holder);
                }
            }
        }

        public FileAvailability getAvailability() {
            PartialFileAvailability result = new PartialFileAvailability(N_PARTS, false);
            for (int filePart = 0; filePart < N_PARTS; filePart++) {
                if (!partsHolders[filePart].isEmpty()) {
                    result.setPartAvailable(filePart);
                }
            }
            return result;
        }

        public UUID getRandomHolder(int filePart) {
            UUID[] holders = (UUID[])partsHolders[filePart].toArray();
            return holders[0]; //TODO:random
        }

        public boolean deregister(UUID holder) {
            boolean removeFile = true;
            for (Set<UUID> partHolders: partsHolders) {
                partHolders.remove(holder);
                if (!partHolders.isEmpty()) {
                    removeFile = false;
                }
            }
            return removeFile;
        }

        public void registerPart(UUID holder, int filePart) {
            Set<UUID> partHolders = partsHolders[filePart];
            partHolders.add(holder);
        }
    }
    private ConcurrentHashMap<String, FileHolders> filesAvailability;
    private ConcurrentHashMap<String, FileDescription> filesIndex;

    public FileIndex() {
        filesAvailability = new ConcurrentHashMap<>();
        filesIndex = new ConcurrentHashMap<>();
    }

    public void registerFiles(UUID holder, FileDescription files[]) {
        for (FileDescription fd: files) {
            if (fd.fileAvailability.isFileAvailable()) {
                registerFile(holder, fd);
            } else {
                deleteHolder(holder, fd.hash);
            }
        }
    }

    public void registerFile(UUID holder, FileDescription description) { //can process both startSeeding and registerFile
        String hash = description.hash;
        filesIndex.putIfAbsent(hash, description);
        if (!filesAvailability.containsKey(hash)) {
            filesAvailability.put(hash, new FileHolders());
        }
        filesAvailability.get(hash).register(holder, description.fileAvailability);
    }

    public FileAvailability getFileAvailability(String hash) {
        return filesAvailability.get(hash).getAvailability();
    }

    public void deleteHolder(UUID holder, String hash) {
        FileHolders partsHolders = filesAvailability.get(hash);
        boolean removeFile = partsHolders.deregister(holder);
        if (removeFile) {
            filesAvailability.remove(hash);
            filesIndex.remove(hash);
        }
    }

    public void deleteHolderFromEverywhere(UUID holder) {
        ArrayList<String> hashesToDelete = new ArrayList<>();
        for (Map.Entry<String, FileHolders> fileHolders: filesAvailability.entrySet()) {
            boolean hashToDelete = fileHolders.getValue().deregister(holder);
            if (hashToDelete) {
                hashesToDelete.add(fileHolders.getKey());
            }
        }
        for (String hash: hashesToDelete) {
            filesAvailability.remove(hash);
            filesIndex.remove(hash);
        }
    }

    public UUID getRandomHolder(String hash, int filePart) {
        return filesAvailability.get(hash).getRandomHolder(filePart);
    }

    public FileDescription[] getAvailableFiles() {
        FileDescription result[] = new FileDescription[filesIndex.size()];
        filesIndex.values().toArray(result);
        return result;
    }

    public void updatePart(UUID holder, String hash, int part) {
        FileHolders holders = filesAvailability.get(hash);
        holders.registerPart(holder, part);
    }
}
