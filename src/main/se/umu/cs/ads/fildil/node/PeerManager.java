package se.umu.cs.ads.fildil.node;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import se.umu.cs.ads.fildil.proto.autogen.PeerInfo;

import java.util.Hashtable;
import java.util.UUID;

/**
 * Manages peer relations
 */
public class PeerManager {
    public final UUID uuid = UUID.randomUUID();
    private DataManager dataManager;
    protected final int port;
    private final String address;
    private ManagedChannel channel; //todo: needed?
    private Hashtable<UUID, StreamerClient> peers;


    public PeerManager(DataManager dataManager, int port) {
        this.port = port;//Do I need port here?
        this.dataManager = dataManager;
        this.address="localhost"; //todo: is this sane?
        this.peers = new Hashtable<>();
        channel = ManagedChannelBuilder.forAddress(this.address,port)
                .usePlaintext(true)
                .build();
    }

    protected void addPeer(PeerInfo peerInfo) {
        //todo: implement
    }


    protected PeerInfo toPeerInfo() {
        PeerInfo.Builder builder = PeerInfo.newBuilder();
        builder.setHighestChunk(dataManager.getHighestId());
        builder.setUuid(uuid.toString());
        for (StreamerClient c :
                peers.values()) {
            builder.putPeers(c.address, c.port);
        }
        return builder.build();
    }
}
