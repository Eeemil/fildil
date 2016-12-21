package se.umu.cs.ads.fildil;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import proto.*;
import proto.Chunk;
import proto.ChunkRequest;
import proto.Empty;
import proto.SendChunkReply;
import proto.StreamerGrpc;

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
    private BlockingQueue<proto.Chunk> blockingQueueClient;
    private BlockingQueue<proto.Chunk> blockingQueueServer;

    public void startServer(int port ) throws IOException, InterruptedException {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                blockingQueueServer = new LinkedBlockingDeque<>();
                try {
                    server = ServerBuilder.forPort(port)
                            .addService(new Streamer())
                            .build()
                            .start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        proto.Chunk chunk = proto.Chunk.newBuilder()
                                        .setBuf(byteString)
                                        .setId(i).build();

        blockingQueueServer.add(chunk);

    }


    public class Streamer extends StreamerGrpc.StreamerImplBase {
        @Override
        public void poll(Empty request, StreamObserver<Chunk> responseObserver) {

            try {
                Chunk chunk = blockingQueueServer.take();
                responseObserver.onNext(chunk);
                responseObserver.onCompleted();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

//        @Override
//        public void requestChunk(ChunkRequest request, StreamObserver<Chunk> responseObserver) {
//            super.requestChunk(request, responseObserver);
//        }
//
//        @Override
//        public void sendChunk(Chunk request, StreamObserver<SendChunkReply> responseObserver) {
//            super.sendChunk(request, responseObserver);
//        }
    }

    public void startClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host,port)
                                        .usePlaintext(true)
                                        .build();

        streamerStub = StreamerGrpc.newBlockingStub(channel);
        blockingQueueClient = new LinkedBlockingDeque<proto.Chunk>();
        LOGGER.info("Starting Client!!!");
        readStream();

    }

    private void readStream() {
        boolean isStreaming = true; //Implement stop function...

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isStreaming){
                    Empty request = Empty.newBuilder().build();
                    proto.Chunk chunk = streamerStub.poll(request);
                    blockingQueueClient.add(chunk);
                }
            }
        });
        t.start();
    }


    public byte[] getChunk() throws InterruptedException {
        proto.Chunk chunk = blockingQueueClient.take();
        return chunk.getBuf().toByteArray();
    }

}
