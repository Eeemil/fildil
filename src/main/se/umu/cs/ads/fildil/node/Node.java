package se.umu.cs.ads.fildil.node;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-20.
 * //TODO: Use executorservice instead of ordinary threads
 */
public abstract class Node {
    private static final Logger LOGGER = Logger.getLogger(Node.class.getName());
    protected final NetworkManager networkManager;

    protected final DataManager dataManager = new DataManager();
    protected final PeerManager peerManager;

    protected Node(int port) throws UnknownHostException {
        peerManager = new PeerManager(dataManager, port);
        LOGGER.info("UUID: " + peerManager.uuid);
        networkManager = new NetworkManager(dataManager, peerManager);
    }

    public void start() throws IOException {
        LOGGER.info("Starting node...");
        networkManager.startStreaming();
    }

    public void stop() {
        LOGGER.info("Stopping node...");
        networkManager.stopStreaming();
    }
}
