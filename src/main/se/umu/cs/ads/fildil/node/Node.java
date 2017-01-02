package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import se.umu.cs.ads.fildil.proto.autogen.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-20.
 * //TODO: Use executorservice instead of ordinary threads
 */
public abstract class Node {
    private static final Logger LOGGER = Logger.getLogger(Node.class.getName());
    private final UUID uuid = UUID.randomUUID();
    protected final StreamerServer server;
    private ManagedChannel channel;
    protected StreamerGrpc.StreamerBlockingStub streamerStub;
    private BlockingQueue<Chunk> blockingQueueClient;
    private BlockingQueue<Chunk> blockingQueueServer;

    protected final DataManager dataManager = new DataManager();

    protected Node(int port) {
        server = new StreamerServer(dataManager,port);
    }

    public void startStreaming() throws IOException {
        server.start();
    }

    public void stopStreaming() {
        server.stop();
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
                Chunk chunk = streamerStub.poll(request);
                blockingQueueClient.add(chunk);
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
