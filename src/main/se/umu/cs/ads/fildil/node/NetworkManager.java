package se.umu.cs.ads.fildil.node;

import se.umu.cs.ads.fildil.proto.autogen.StreamerGrpc;

import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Created by emil on 2017-01-02.
 */
public class NetworkManager {
    private final DataManager dataManager;
    private final StreamerServer server;
    private final Hashtable<UUID,PeerManager> peers;


    public NetworkManager(DataManager dataManager, int port) {
        this.server = new StreamerServer(dataManager,port);
        this.dataManager = dataManager;
        peers = new Hashtable<>();
    }

    public void startStreaming() throws IOException {
        server.start();
    }

    public void stopStreaming() {
        server.stop();
    }
}
