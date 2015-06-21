import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Dmitry Tishchenko on 21.06.15.
 */
public interface TorrentServer extends Remote {
    void register(TorrentLeech leecher) throws RemoteException;
}
