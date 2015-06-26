package network;

import data.FileIndex;
import messages.server.ConnectionDataServerMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
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
    private FileIndex index;
    private MessageSender sender;
    private MessageProcessor processor;
    private final int REGISTRAR_THREADS = 5;
    public Server() {
        clients = new ConcurrentHashMap<>();
        registrar = Executors.newFixedThreadPool(REGISTRAR_THREADS);
    }
    public void register(Socket client) {
        registrar.submit(new RegisteringTask(client));
    }

    public ObjectOutputStream getLeecherStream(UUID id) throws IOException{
        Socket clientSocket = clients.get(id);
        return new ObjectOutputStream(clientSocket.getOutputStream());
    }

    public ConnectionDataServerMessage getConnectionData(UUID holderToConnect) {
        Socket clientSocket = clients.get(holderToConnect);
        InetAddress addr = clientSocket.getInetAddress();
        return new ConnectionDataServerMessage(addr.getHostAddress(), clientSocket.getPort());
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

    public void deregister(UUID id) {
        Socket socket = clients.get(id);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clients.remove(socket);
        }
    }

    public void stop() {
        registrar.shutdown();
    }
}
