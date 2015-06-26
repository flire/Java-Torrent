package network;

import messages.server.ConnectionDataServerMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Dmitry Tishchenko on 26.06.15.
 */
public class MessageSender {
    private class SenderTaskInfo {
        public final ObjectOutputStream oos;
        public final ConnectionDataServerMessage message;

        private SenderTaskInfo(ObjectOutputStream oos, ConnectionDataServerMessage message) {
            this.oos = oos;
            this.message = message;
        }
    }
    private class SendingTask implements Runnable {
        @Override
        public void run() {
            while(!Thread.interrupted()) {
                try {
                    synchronized (tasksQueue) {
                        while (tasksQueue.isEmpty()) {
                            try {
                                tasksQueue.wait();
                            } catch (InterruptedException e) {
                            }
                        }
                        SenderTaskInfo sti = tasksQueue.poll();
                        sendMessage(sti.oos, sti.message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();//TODO: log!
                }
            }
        }
    }

    private void sendMessage(ObjectOutputStream oos, ConnectionDataServerMessage message) throws IOException{
        oos.writeObject(message);
    }

    private ConcurrentLinkedQueue<SenderTaskInfo> tasksQueue;
    private Server server;
    private ExecutorService executor;

    public MessageSender(Server server, int nThreads) {
        this.server = server;
        tasksQueue = new ConcurrentLinkedQueue<>();
        executor = Executors.newFixedThreadPool(nThreads);
        for (int thread = 0; thread < nThreads; thread++) {
            executor.execute(new SendingTask());
        }
    }

    public void scheduleConnectionMessage(UUID id, UUID holderToConnect) throws IOException {
        ObjectOutputStream oosToConnect = server.getLeecherStream(id);
        ConnectionDataServerMessage connectionData = server.getConnectionData(holderToConnect);
        synchronized (tasksQueue) {
            tasksQueue.offer(new SenderTaskInfo(oosToConnect, connectionData));
            tasksQueue.notify();
        }

    }

    public void shutdown() {
        executor.shutdown();
    }
}
