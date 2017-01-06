package se.umu.cs.ads.fildil.node;

import se.umu.cs.ads.fildil.proto.autogen.Chunk;
import se.umu.cs.ads.fildil.proto.autogen.ChunkRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by emil on 2016-12-30.
 */
public class PeerNode extends Node {
    private PeerManager peerManager;
    private final static String FLAG_PRIMARY_ADDR = "-prim";
    private String primAddr;

    public static void main(String[] args) {

        if(args.length == 0) {
            System.err.println("Usage: port [-prim addr:port] [addr:port]... [addr:port]");
            System.exit(0);
        }

        ArrayList<String> peers = new ArrayList<>();
        String primAddr = null;
        for(int i = 1; i < args.length; i++) {
                if(args[i].equals(FLAG_PRIMARY_ADDR)) {
                    i++;
                    primAddr = args[i];
                } else {
                    peers.add(args[i]);
                }

        }

        int port = new Integer(args[0]);
        PeerNode peerNode = primAddr == null ? new PeerNode(peers,port):
                                               new PeerNode(primAddr,peers,port);

        try {
            peerNode.start();
            if(primAddr != null) {
                peerNode.readFromPrimary();
            } else {
                peerNode.readStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Node fetching from other nodes
     * @param peers address to peer, on form such as "localhost:3124"
     */
    public PeerNode (ArrayList<String> peers, int port) {
        super(port);
        peerManager = new PeerManager(dataManager,port);
        peerManager.addPeers(peers);
    }

    public PeerNode (String primAddr, ArrayList<String> peers, int port) {
        super(port);
        peerManager = new PeerManager(dataManager,port);
        peerManager.addPeers(peers);
        this.primAddr = primAddr;
    }

    public void readStream() {
        //get a chunk from every possible server.
        //Grab 20 chunks from each.
        //If it takes to loong, it will abort and continue with another node.

        while(true) {
//            int id = dataManager.getHighestId();
//            Stack<Integer> que = new Stack<>();
            StreamerClient[] peers = peerManager.getPeers();

            for(StreamerClient peer:peers) {
                int id = dataManager.getHighestId();
                Chunk chunk = peer.requestChunk(id);
                if(chunk.getId() != -1) {
                    dataManager.addChunk(chunk);
                }
            }
        }

    }

    /**
     * Read from primary node tills there no more chunks to read.
     */
    public void readFromPrimary() throws InterruptedException {
        StreamerClient streamerClient = new StreamerClient(primAddr,peerManager.getPeerInfo());
        int idChunk = 0;
        int sleep = 1000;
        do {
            int nextIDChunk = dataManager.getHighestId();
            Chunk chunk = streamerClient.requestChunk(nextIDChunk);
            idChunk = chunk.getId();
            if(idChunk == DataManager.FLAG_NO_CHUNK) {
                Thread.sleep(sleep);
            } else if(idChunk >= 0 ) {
                dataManager.addChunk(chunk);
            }

        }while(idChunk != DataManager.FLAG_END_OF_STREAM);

    }

}
