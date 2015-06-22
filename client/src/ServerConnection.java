import java.io.IOException;
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
    public ServerConnection(String host) throws IOException {
        Socket socket = new Socket(host, 12345);
        System.out.println("Got server connection!");

    }
}
