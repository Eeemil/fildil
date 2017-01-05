package se.umu.cs.ads.fildil.node;

import se.umu.cs.ads.fildil.proto.autogen.PeerInfo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Manages peer relations
 */
public class PeerManager {
    private static final Logger LOGGER = Logger.getLogger(PeerManager.class.getName());
    public final UUID uuid = UUID.randomUUID();
    private DataManager dataManager;
    protected final int port;
    private PeerInfo.Builder peerInfoBuilder;
    private Hashtable<UUID, StreamerClient> peers;


    public PeerManager(DataManager dataManager, int port) {
        this.port = port;//Do I need port here?
        this.dataManager = dataManager;
        this.peers = new Hashtable<>();
        peerInfoBuilder = PeerInfo.newBuilder();
        peerInfoBuilder.setUuid(uuid.toString());
    }

    protected PeerInfo getPeerInfo() {
        peerInfoBuilder.setHighestChunk(dataManager.getHighestId());
        return peerInfoBuilder.build();

    }

    public StreamerClient[] getPeers() {
        return peers.values().toArray(new StreamerClient[]{});
    }

    private void addPeer(UUID peerId, String uri) {

        if (peers.containsKey(peerId)) {
            LOGGER.info("Trying to add already-added peer "

                    + peerId.toString() + ", skipping...");
            return;
        }

        PeerInfo myInfo = getPeerInfo();
        //Todo: for report? Maybe only send partial peer list (to minimize overhead)
        StreamerClient peer = new StreamerClient(uri, myInfo);
        peers.put(peerId, peer);
        peerInfoBuilder.putPeers(peerId.toString(),uri);

        LOGGER.info("Added peer " + peerId.toString());
    }

    protected void addPeers(ArrayList<String> uris) {
        for(String uri:uris) {
            UUID id = UUID.randomUUID();
            addPeer(id,uri);
        }


//        for (Map.Entry<String, String> entry :
//                peers.entrySet()) {
//            UUID id = UUID.fromString(entry.getKey().replace("-",""));
//            String uri = entry.getValue();
//            addPeer(id, uri);
//        }



    }
}
