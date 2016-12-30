package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import se.umu.cs.ads.fildil.proto.autogen.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-20.
 */
public class Node {
    private static final Logger LOGGER = Logger.getLogger(Node.class.getName());
    private final UUID uuid = UUID.randomUUID();
    private Server server;
    private ManagedChannel channel;
    private StreamerGrpc.StreamerBlockingStub streamerStub;
    private BlockingQueue<Chunk> blockingQueueClient;
    private BlockingQueue<Chunk> blockingQueueServer;

    public void startServer(int port ) throws IOException, InterruptedException {

        Thread t = new Thread(() -> {
            blockingQueueServer = new LinkedBlockingDeque<>();
            try {
                DataManager dataManager = new DataManager();
                server = ServerBuilder.forPort(port)
                        .addService(new PeerManager(dataManager))
                        .build()
                        .start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.start();

        LOGGER.info("Started server at port: " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** DERP SHUTTING DOWN!");
                Node.this.stopServer();
                System.err.println("*** Server is killed");
            }
        });
        blockUntilShutdown();
    }

    public void stopServer() {
        if(server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public void sendChunk(byte[] data, int i) {

        ByteString byteString = ByteString.copyFrom(data);
        Chunk chunk = Chunk.newBuilder()
                                        .setBuf(byteString)
                                        .setId(i).build();

        blockingQueueServer.add(chunk);

    }

    public void startClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host,port)
                                        .usePlaintext(true)
                                        .build();

        streamerStub = StreamerGrpc.newBlockingStub(channel);
        blockingQueueClient = new LinkedBlockingDeque<Chunk>();
        LOGGER.info("Starting Client!!!");
        readStream();

    }

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


    public byte[] getChunk() throws InterruptedException {
        Chunk chunk = blockingQueueClient.take();
        return chunk.getBuf().toByteArray();
    }

}
