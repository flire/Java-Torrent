import java.rmi.AlreadyBoundException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Dmitry Tishchenko on 22.06.15.
 */
public class Main {
    private static Server server;
    private static Registry registry;
    public static void main(String[] args) {
        server = new Server();
        final Thread mainThread = Thread.currentThread();
        try {
            TorrentServer stub = (TorrentServer) UnicastRemoteObject.exportObject(server, 0);

            registry = LocateRegistry.createRegistry(12345);
            registry.bind("TorrentServer", stub);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    registry.unbind("TorrentServer");
                } catch(RemoteException | NotBoundException e) { }
                finally {
                    System.out.println("Exit...");
//                    mainThread.join();
                }
            }
        });
    }
}
