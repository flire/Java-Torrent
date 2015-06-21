import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Dmitry Tishchenko on 21.06.15.
 */
public interface TorrentLeech extends Remote {
    public void sendMessage(String message) throws RemoteException;

}