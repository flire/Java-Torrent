import network.Server;

import java.io.IOException;

/**
 * Created by Dmitry Tishchenko on 22.06.15.
 */
public class Main {
    private static Server server;
    private static ConnectionListener listener;
    public static void main(String[] args) {
        server = new Server();
//        try {
//            TorrentServer stub = (TorrentServer) UnicastRemoteObject.exportObject(server, 0);
//
//            registry = LocateRegistry.createRegistry(12345);
//            registry.bind("TorrentServer", stub);
//        } catch (RemoteException | AlreadyBoundException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
        //        No more RMI. It got killed by some close-minded teacher.
        Thread shutdownHook = new Thread() {
            @Override
            public void run() {
//                try {
//                    registry.unbind("TorrentServer");
//                } catch(RemoteException | NotBoundException e) { }
//                finally {
//                    System.out.println("Exit...");
////                    mainThread.join();
//                }
                listener.stopListen();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        listener = new ConnectionListener(server);
        try {
            listener.startListen(12345);
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
            Runtime.getRuntime().removeShutdownHook(shutdownHook);
            server.stop();
            System.exit(1);
        }

    }
}
