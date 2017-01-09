package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import se.umu.cs.ads.fildil.proto.autogen.Chunk;
import se.umu.cs.ads.fildil.proto.autogen.PeerInfo;

import java.util.*;
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
    private StreamerClient primaryNode = null;


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

    /**
     *
     * @param id
     * @return
     */
    public Chunk getChunk(int id) {

        List<StreamerClient>clients = null;
        clients = Arrays.asList(peers.values().toArray(new StreamerClient[]{}));

        if (primaryNode != null) {
            clients.add(primaryNode);
        }

        return randLoadBalance(clients,id);
    }

    protected void setPrimary(String primaryAddr) {
        primaryNode = new StreamerClient(primaryAddr,getPeerInfo());
    }

    public void addPeer(String uri) {

        PeerInfo myInfo = getPeerInfo();
        //Todo: for report? Maybe only send partial peer list (to minimize overhead)
        StreamerClient peer = new StreamerClient(uri, myInfo);

        if (peers.containsKey(peer.uuid)) {
            LOGGER.info("Trying to add already-added peer " + peer.uuid.toString() + ", skipping...");
            return;
        }

        peers.put(peer.uuid, peer);
        peerInfoBuilder.putPeers(peer.uuid.toString(),uri);
        LOGGER.info("Added peer " + peer.uuid.toString());
    }

    protected void addPeers(ArrayList<String> uris) {
        for(String uri:uris) {
            addPeer(uri);
        }
    }

    /**
     *
     * @param clients
     * @return
     */
    private Chunk randLoadBalance(List<StreamerClient> clients, int id) {
        Random gen = new Random();

        int idFlag = 0;
        //find client that has chunk
        while(!clients.isEmpty()) {
            int index = gen.nextInt(clients.size());
            StreamerClient client = clients.get(index);
            clients.remove(index);
            Chunk chunk = client.requestChunk(id);
            if(chunk.getId()>= 0) {
                return client.requestChunk(id);
            } else {
                idFlag = chunk.getId();
            }
        }

        return Chunk.newBuilder().setBuf(ByteString.EMPTY).setId(idFlag).build();
    }

}
