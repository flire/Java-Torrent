import java.rmi.RemoteException;

/**
 * Created by Dmitry Tishchenko on 22.06.15.
 */
public class Server implements TorrentServer {
    @Override
    public void register(TorrentLeech leecher) throws RemoteException {
        System.out.println("Registered leech!");
        leecher.sendMessage("Hello from server!");
    }
}
