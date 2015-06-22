import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Dmitry Tishchenko on 22.06.15.
 */
public class Server {
//    private ArrayList<Socket> clients;
    private ConcurrentHashMap<UUID, Socket> clients;
    private ExecutorService registrar;
    private final int REGISTRAR_THREADS = 5;
    public Server() {
        clients = new ConcurrentHashMap<>();
        registrar = Executors.newFixedThreadPool(REGISTRAR_THREADS);
    }
    public void register(Socket client) {
        registrar.submit(new RegisteringTask(client));
    }

    private class RegisteringTask implements Callable {

        private Socket socketToRegister;

        public RegisteringTask(Socket socket) {
            this.socketToRegister = socket;
        }

        @Override
        public Object call() throws Exception {
            UUID clientUUID = UUID.randomUUID();
            clients.put(clientUUID, socketToRegister);
            System.out.println("Registered leech "+clientUUID.toString());
            return clientUUID;
        }
    }

    public void stop() {
        registrar.shutdown();
    }
}
