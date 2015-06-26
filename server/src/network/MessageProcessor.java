package network;

import data.FileIndex;
import messages.client.ClientMessage;
import messages.client.FileStatusUpdateClientMessage;
import messages.client.PartRequestClientMessage;
import messages.client.PartStatusUpdateClientMessage;
import utils.ClientInputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class MessageProcessor {
    private ConcurrentLinkedQueue<ClientInputStream> connections;
    private ExecutorService listener;
    private FileIndex index;
    private Server server;
    private MessageSender sender;

    private class SingleProcessor implements Runnable {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                ClientInputStream is = connections.poll();
                try {
                    while (is.input.available()==0) {
                        connections.offer(is);
                        is = connections.poll();
                    }
                    ClientMessage message = (ClientMessage)is.input.readObject();
                    processMessage(is.id, message);
                } catch (IOException | ClassNotFoundException e) {
                    //TODO:log here
                    System.out.println(e.getMessage());
                } finally {
                    connections.offer(is);
                }
            }
        }
    }

    public MessageProcessor(UUID id, ObjectInputStream oisToRegister, FileIndex index, Server server, MessageSender sender, int numberOfThreads) {
        connections = new ConcurrentLinkedQueue<>();
        connections.offer(new ClientInputStream(id,oisToRegister));
        listener = Executors.newFixedThreadPool(numberOfThreads);
        for (int thread = 0; thread < numberOfThreads; thread++) {
            listener.execute(new SingleProcessor());
        }
        this.index = index;
        this.server = server;
        this.sender = sender;
    }

    private void processMessage(UUID id, ClientMessage message) {
        switch (message.messageType) {
            case FileStatusUpdate:
                processFileStatusUpdate(id, (FileStatusUpdateClientMessage) message);
                break;
            case PartStatusUpdate:
                processPartStatusUpdate(id, (PartStatusUpdateClientMessage)message);
                break;
            case PartRequest:
                processPartRequest(id, (PartRequestClientMessage)message);
                break;
            case Disconnect:
                disconnect(id);
                break;
        }
    }

    private void processPartRequest(UUID id, PartRequestClientMessage message) {
        try {
            sender.scheduleConnectionMessage(id, index.getRandomHolder(message.hash, message.part));
        } catch (IOException e) {
            //TODO: log!
            e.printStackTrace();
        }
    }

    private void disconnect(UUID id) {
        deregisterConnection(id);
        index.deleteHolderFromEverywhere(id);
        server.deregister(id);
    }

    private void processPartStatusUpdate(UUID id, PartStatusUpdateClientMessage message) {
        index.updatePart(id, message.hash, message.partNumber);
    }

    private void processFileStatusUpdate(UUID id, FileStatusUpdateClientMessage message) {
        index.registerFiles(id, message.files);
    }

    public void registerConnection(UUID id, ObjectInputStream ois) {
        connections.offer(new ClientInputStream(id,ois));
    }

    public void deregisterConnection(UUID id) {
        ClientInputStream cis = search(id);
        if (id != null) {
            connections.remove(cis);
        }

    }

    private ClientInputStream search(UUID id) {
        for (ClientInputStream cis: connections) {
            if (cis.id.equals(id)) {
                return cis;
            }
        }
        return null;
    }

    public void shutdown() {
        listener.shutdown();
    }
}
