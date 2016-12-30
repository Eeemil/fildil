package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import se.umu.cs.ads.fildil.Network.ChunkBuffer;
import se.umu.cs.ads.fildil.VideoProperties;
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
    private Server server;
    private ManagedChannel channel;
    private StreamerGrpc.StreamerBlockingStub streamerStub;
    private BlockingQueue<Chunk> blockingQueueClient;
    private BlockingQueue<Chunk> blockingQueueServer;

    protected final DataManager dataManager = new DataManager();

    protected Node(int port) {
        server = ServerBuilder.forPort(port)
                .addService(new PeerManager(dataManager))
                .build();

    }

    public void start() throws IOException {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> stopServer()));
        LOGGER.info("Starting node server...");
        server.start();
    }

    public void stopServer() {
        if(!server.isShutdown()) {
            LOGGER.info("Shutting down server...");
            server.shutdown();
            try {
                if (!server.awaitTermination(10, TimeUnit.SECONDS)) {
                    LOGGER.warning("Server did not shut down in 10 seconds, shutting down forcefully");
                    server.shutdownNow();
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING,"Interrupted while waiting for server termination, shutting down forcefully", e);
                server.shutdownNow();
            }
        }
        if(server.isShutdown()) {
            LOGGER.info("Server has been shut down");
        } else {
            LOGGER.severe("Shutdown request has been sent but server has not properly shut down yet");
        }

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
        boolean isStreaming = true; //Implement stop function...

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
