package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import se.umu.cs.ads.fildil.proto.autogen.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-20.
 * //TODO: Use executorservice instead of ordinary threads
 */
public abstract class Node {
    private static final Logger LOGGER = Logger.getLogger(Node.class.getName());
    private final UUID uuid = UUID.randomUUID();
    protected final NetworkManager networkManager;
    private ManagedChannel channel;
    protected StreamerGrpc.StreamerBlockingStub streamerStub;
    private BlockingQueue<Chunk> blockingQueueClient;
    private BlockingQueue<Chunk> blockingQueueServer;

    protected final DataManager dataManager = new DataManager();
    protected final PeerManager peerManager;

    protected Node(int port) {
        peerManager = new PeerManager(dataManager,port);
        networkManager = new NetworkManager(dataManager,port);
    }

    public void start() throws IOException {
        LOGGER.info("Starting node...");
        networkManager.startStreaming();
    }

    public void stop() {
        LOGGER.info("Stopping node...");
        networkManager.stopStreaming();
    }

    @Deprecated
    public void sendChunk(byte[] data, int i) {

        ByteString byteString = ByteString.copyFrom(data);
        Chunk chunk = Chunk.newBuilder()
                                        .setBuf(byteString)
                                        .setId(i).build();

        blockingQueueServer.add(chunk);

    }

    @Deprecated
    public void startClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host,port)
                                        .usePlaintext(true)
                                        .build();

        streamerStub = StreamerGrpc.newBlockingStub(channel);

        blockingQueueClient = new LinkedBlockingDeque<Chunk>();
        LOGGER.info("Starting Client!!!");
        readStream();

    }

    @Deprecated
    private void readStream() {
        boolean isStreaming = true; //Implement stopStreaming function...

        Thread t = new Thread(() -> {
            while(isStreaming){
                Empty request = Empty.newBuilder().build();
                //Chunk chunk = streamerStub.poll(request);
                //blockingQueueClient.add(chunk);
            }
        });
        t.start();
    }

    @Deprecated
    public byte[] getChunk() throws InterruptedException {
        Chunk chunk = blockingQueueClient.take();
        return chunk.getBuf().toByteArray();
    }

}
