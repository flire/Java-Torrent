import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Dmitry Tishchenko on 21.06.15.
 */
public class ServerConnection {
    public ServerConnection(String host) throws IllegalArgumentException, RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, 12345);
        TorrentServer server = (TorrentServer)registry.lookup("TorrentServer");

        TorrentLeech stub = (TorrentLeech) UnicastRemoteObject.exportObject(new TorrentLeech() {
            @Override
            public void sendMessage(String message) {
                System.out.println(message);
            }
        }, 0);
        server.register(stub); //will be used later

    }
}
