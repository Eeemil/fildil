package se.umu.cs.ads.fildil.node;

import io.grpc.ManagedChannel;

/**
 * Container class for information specific to a certain peer
 */
public class StreamerClient {
    public final String address;
    public final int port;
    private ManagedChannel channel = null;
    public StreamerClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

}
