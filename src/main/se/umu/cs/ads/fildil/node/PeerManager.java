package se.umu.cs.ads.fildil.node;

import io.grpc.stub.StreamObserver;
import se.umu.cs.ads.fildil.proto.autogen.*;

/**
 * Created by emil on 2016-12-30.
 */
public class PeerManager extends StreamerGrpc.StreamerImplBase {
    private final DataManager dataManager;

    public PeerManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void poll(Empty request, StreamObserver<Chunk> responseObserver) {
        Chunk ret = dataManager.getHighestChunk();
        responseObserver.onNext(ret);
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
