package se.umu.cs.ads.fildil.node;

import se.umu.cs.ads.fildil.proto.autogen.Chunk;
import se.umu.cs.ads.fildil.proto.autogen.PeerInfo;
import se.umu.cs.ads.fildil.proto.utils.ChunkUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Manages peer relations
 */
public class PeerManager {
    private static final Logger LOGGER = Logger.getLogger(PeerManager.class.getName());
    public final UUID uuid = UUID.randomUUID();
    public final String addr;
    private DataManager dataManager;
    protected final int port;
    private PeerInfo.Builder peerInfoBuilder;
    private ConcurrentHashMap<String, StreamerClient> peers;
    private StreamerClient primaryNode = null;



    public PeerManager(DataManager dataManager, int port) throws UnknownHostException {
        this.port = port;//Do I need port here?
        this.dataManager = dataManager;
        this.peers = new ConcurrentHashMap<>();
        peerInfoBuilder = PeerInfo.newBuilder();
        peerInfoBuilder.setUuid(uuid.toString());
        addr = InetAddress.getLocalHost().getHostAddress() + ":" + port;
        peerInfoBuilder.setAddress(addr);
        peerInfoBuilder.putPeers(uuid.toString(),addr);
    }

    /**
     * @return up-to-date information about the current peer
     */
    protected PeerInfo getPeerInfo() {
        peerInfoBuilder.setHighestChunk(dataManager.getHighestId());
        return peerInfoBuilder.build();
    }

    /**
     * Retrieves a chunk from a random peer
     * @param id
     * @return
     */
    public Chunk getChunk(int id) {

        ArrayList<StreamerClient> clients = null;
        clients = new ArrayList<>(Arrays.asList(peers.values().toArray(new StreamerClient[]{})));

        if (primaryNode != null) {
            clients.add(primaryNode);
        }

        Chunk ret = null;
        ret = randomLoadBalance(clients,id);


        return ret;
    }

    /**
     * Set the primary peer address, if a primary node is known.
     * @param primaryAddr
     */
    protected void setPrimary(String primaryAddr) {
        primaryNode = new StreamerClient(primaryAddr,getPeerInfo());
    }

    /**
     * Adds a single peer
     * @param uri to peer
     */
    public void addPeer(String uri) {

        if(uri.equals(addr)) {
            System.out.println("Not adding myself");
            return;
        }

        PeerInfo myInfo = getPeerInfo();
        //Todo: for report? Maybe only send partial peer list (to minimize overhead)
        StreamerClient peer = new StreamerClient(uri, myInfo);
        System.out.println("Address: " + uri);

        if (peers.containsKey(peer.uuid.toString())) {
            LOGGER.info("Trying to add already-added peer " + peer.uuid.toString() + ", skipping...");
            return;
        }


        peers.put(peer.uuid.toString(), peer);
        peerInfoBuilder.putPeers(peer.uuid.toString(),uri);
        LOGGER.info("Added peer " + peer.uuid.toString());
    }

    /**
     * Adds a set of peer from a list of URI:s
     * @param uris
     */
    protected void addPeers(ArrayList<String> uris) {
        for(String uri:uris) {
            addPeer(uri);
        }
    }

    /**
     * Return chunk from a random suitable peer
     * @param clients that are currently active
     * @return chunk
     */
    private Chunk randomLoadBalance(List<StreamerClient> clients, int id) {
        Random gen = new Random();

        //find client that has chunk else remove them from list
        while(clients.size() > 0) {
            int index = gen.nextInt(clients.size());

            Chunk chunk = getChunkFromClient(clients.get(index),id);
            clients.remove(index);

            if(chunk.getId() >= 0
                    || chunk.getId() == ChunkUtils.FLAG_END_OF_STREAM) {
                return chunk;
            }

        }

        //If no chunk found then return chunk as non existant
        return ChunkUtils.createNonExistantChunk();
    }


    /**
     *  Tries to retrive a specific chunk from client, if connection fails client will
     *  be removed from peers.
     * @param client to request the chunk from
     * @param id for the chunk
     * @return chunk for given id.
     */
    private Chunk getChunkFromClient(StreamerClient client, int id) {
        Chunk ret = ChunkUtils.createNonExistantChunk();
        try {
            ret = client.requestChunk(id);
        }catch(io.grpc.StatusRuntimeException e ) {
            String uuid = client.uuid.toString();
            removePeer(uuid);
            if(primaryNode != null && primaryNode.uuid.toString().equals(uuid)) {
                primaryNode = null;
            }
            LOGGER.info("Client disconnected: " + uuid);
        } catch (NullPointerException e){}

        return ret;
    }

    /**
     * Remove peer from both peers and peerInfo.
     * @param uuid
     */
    private void removePeer(String uuid) {
        peers.remove(uuid);
        Map<String,String> infoPeers = new HashMap<>();
        String[] uuids = peers.keySet().toArray(new String[]{});

        for(String u:uuids) {
            infoPeers.put(u,peers.get(uuid).addr);
        }

        infoPeers.put(this.uuid.toString(),this.addr);
        peerInfoBuilder.putAllPeers(infoPeers);

    }
}
