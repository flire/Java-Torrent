import fileutils.FileDescription;
import fileutils.UnavailableFileAvailability;
import messages.client.ClientMessage;
import messages.client.FileStatusUpdateClientMessage;
import messages.client.PartStatusUpdateClientMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Dmitry Tishchenko on 21.06.15.
 */
public class ServerConnection {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ServerConnection(String host) throws IOException {
        socket = new Socket(host, 12345);
        System.out.println("Got server connection!");
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
    }

    public void registerGlobally(FileDescription[] localData) throws IOException{
        ClientMessage filesStatus = new FileStatusUpdateClientMessage(localData);
        synchronized (oos) {
            oos.writeObject(filesStatus);
        }
    }

    public void registerSingleFileGlobally(FileDescription localDescription) throws IOException {
        FileDescription[] arrayToSend = {localDescription};
        registerGlobally(arrayToSend);
    }

    public void deregisterGlobally(FileDescription fileToDeregister) throws IOException {
        FileDescription description = new FileDescription(
                fileToDeregister.hash,
                fileToDeregister.size,
                fileToDeregister.filename,
                new UnavailableFileAvailability()
        );
        registerSingleFileGlobally(description);
    }

    public void registerPart(String hash, int part) throws IOException {
        ClientMessage partStatus = new PartStatusUpdateClientMessage(hash, part);
        synchronized (oos) {
            oos.writeObject(partStatus);
        }
    }

    public void disconnect() throws IOException {
        try {
            ClientMessage disconnectMessage = new ClientMessage(ClientMessage.MessageType.Disconnect);
            synchronized (oos) {
                oos.writeObject(disconnectMessage);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            socket.close();
        }

    }
}
