package se.umu.cs.ads.fildil.node;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import se.umu.cs.ads.fildil.proto.autogen.Chunk;
import se.umu.cs.ads.fildil.proto.autogen.ChunkRequest;
import se.umu.cs.ads.fildil.proto.autogen.PeerInfo;
import se.umu.cs.ads.fildil.proto.autogen.StreamerGrpc;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Container class for information specific for one peer.
 */
public class StreamerClient {
    private static final Logger LOGGER = Logger.getLogger(StreamerClient.class.getName());
    public final String uri;
    private final ManagedChannel channel;
    private final StreamerGrpc.StreamerBlockingStub client;
    private int highestChunk = -1;
    public final UUID uuid;
    public final String addr;
    private PeerInfo peerInfo = null;


    /**
     * Initialize a client connection
     * @param uri address to peer, on form such as "localhost:3124"
     * @param myInfo information corresponding to the peer contacting the other peer (for mutual exchange of information)
     */
    public StreamerClient(String uri, PeerInfo myInfo) {
        this.uri = uri;
        this.channel = ManagedChannelBuilder.forTarget(uri)
                .usePlaintext(true)
                .idleTimeout(10, TimeUnit.SECONDS)
                .build();
        client = StreamerGrpc.newBlockingStub(channel);
        PeerInfo otherInfo = client.poll(myInfo);
        uuid = UUID.fromString(otherInfo.getUuid());
        addr = otherInfo.getAddress();
        this.peerInfo = otherInfo;
        this.highestChunk = otherInfo.getHighestChunk();
    }

    public StreamerClient(String uri, UUID uuid) {
        this.uuid = uuid;
        this.uri = uri;
        this.channel = ManagedChannelBuilder.forTarget(uri)
                .usePlaintext(true)
                .idleTimeout(10, TimeUnit.SECONDS)
                .build();
        client = StreamerGrpc.newBlockingStub(channel);
        addr = uri;
    }

    /**
     * Send a poll-request to the peer and update information belonging to it.
     * @param myInfo information corresponding to the peer contacting the other peer (for mutual exchange of information)
     * @return the updated peerInfo
     */
    public PeerInfo updateInfo(PeerInfo myInfo) {
        StreamerGrpc.StreamerBlockingStub client = StreamerGrpc.newBlockingStub(channel);
        return this.peerInfo = client.poll(myInfo);
    }

    public Chunk requestChunk(int id) {
        ChunkRequest request = ChunkRequest.newBuilder().setId(id).build();
        return client.requestChunk(request);
    }

    /**
     * @return information corresponding to a client
     */
    public PeerInfo getPeerInfo() {
        return peerInfo;
    }
}
