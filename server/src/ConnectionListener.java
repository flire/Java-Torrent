import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Dmitry Tishchenko on 22.06.15.
 */
public class ConnectionListener {
    private ServerSocket connectionsSocket;
    private Server server;
    private ExecutorService listener;
    private boolean stopped;

    public ConnectionListener(Server server) {
        this.server = server;
        listener = Executors.newSingleThreadExecutor();
        stopped = false;
    }

    public void startListen(int port) throws IOException {
        connectionsSocket = new ServerSocket(port);
        while (!stopped) {
            Socket socket = connectionsSocket.accept();
            server.register(socket);
        }
    }

    public void stopListen() {
        stopped = true;
    }
}
