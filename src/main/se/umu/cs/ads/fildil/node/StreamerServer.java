package se.umu.cs.ads.fildil.node;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import se.umu.cs.ads.fildil.proto.autogen.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-30.
 */
public class StreamerServer extends StreamerGrpc.StreamerImplBase {
    private final DataManager dataManager;
    private final Server server;
    private static final Logger LOGGER = Logger.getLogger(StreamerServer.class.getName());
    private final PeerManager peerManager;

    public StreamerServer(DataManager dataManager, PeerManager peerManager) {
        this.dataManager = dataManager;
        this.peerManager = peerManager;
        this.server = ServerBuilder.forPort(peerManager.port).addService(this).build();

    }

    public void start() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> stop()));
        LOGGER.info("Starting node server...");
        server.start();
        LOGGER.info("Node server started.");
    }

    public void stop() {
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

    @Override
    public void poll(PeerInfo request, StreamObserver<PeerInfo> responseObserver) {
        peerManager.addPeer(request);
        responseObserver.onNext(peerManager.toPeerInfo());
        responseObserver.onCompleted();
    }

    @Override
    public void requestChunk(ChunkRequest request, StreamObserver<Chunk> responseObserver) {
        Chunk ret = dataManager.getChunk(request.getId());
        responseObserver.onNext(ret);
        responseObserver.onCompleted();
    }

    @Override
    public void receiveChunk(Chunk request, StreamObserver<ReceiveChunkReply> responseObserver) {
        dataManager.addChunk(request);
        responseObserver.onNext(ReceiveChunkReply.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
