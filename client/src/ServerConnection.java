import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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

    public FileIndex createFileIndexFromServer(FileDescription[] localData) {
        try {
            oos.writeObject(localData);
        } catch (IOException e) {}
        return null;
    }
}
