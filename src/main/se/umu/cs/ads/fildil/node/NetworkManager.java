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

    public NetworkManager(DataManager dataManager, PeerManager peerManager) {
        this.dataManager = dataManager;
        this.server = new StreamerServer(dataManager,peerManager);
    }

    public void startStreaming() throws IOException {
        server.start();
    }

    public void stopStreaming() {
        server.stop();
    }
}
