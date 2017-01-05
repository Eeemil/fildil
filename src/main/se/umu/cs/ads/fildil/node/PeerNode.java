package se.umu.cs.ads.fildil.node;

import se.umu.cs.ads.fildil.proto.autogen.Chunk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by emil on 2016-12-30.
 */
public class PeerNode extends Node {
    private PeerManager peerManager;
    private final static String FLAG_PRIMARY_ADDR = "-prim";
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("Usage: port  [addr:port]... [addr:port]");
            System.exit(0);
        }

        ArrayList<String> peers = new ArrayList<>();
        for(int i = 1; i < args.length; i++) {
                peers.add(args[i]);
        }

        int port = new Integer(args[0]);
        PeerNode peerNode = new PeerNode(peers,port);

        try {
            peerNode.start();
        } catch (IOException e) {
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

    //Start node without connecting to any server.
    public PeerNode (int port) {
        super(port);
        peerManager = new PeerManager(dataManager,port);
    }

    public void readStream() {

        //get a chunk from every possible server.
        //Grab 20 chunks from each.
        //If it takes to loong, it will abort and continue with another node.

        Chunk chunk = dataManager.getHighestChunk();
    }

    public void readFromPrimary(String prim) {
        StreamerClient streamerClient = new StreamerClient(prim,peerManager.getPeerInfo());

    }

}
