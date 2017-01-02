package se.umu.cs.ads.fildil.node;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.UUID;

/**
 * Manages _one_ peer-to-peer relation
 */
public class PeerManager {
    public final UUID uuid;
    private final int port;
    private final String address;
    private ManagedChannel channel;

    public PeerManager(UUID uuid, int port, String addr) {
        this.uuid = uuid;
        this.port = port;
        this.address=addr;
        channel = ManagedChannelBuilder.forAddress(addr,port)
                .usePlaintext(true)
                .build();
    }
}
