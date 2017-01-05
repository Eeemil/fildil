package se.umu.cs.ads.fildil.node;

import se.umu.cs.ads.fildil.proto.autogen.Chunk;

import java.io.IOException;

/**
 * Created by emil on 2016-12-30.
 */
public class PeerNode extends Node {
    private PeerManager peerManager;

    public static void main(String[] args) {
       // int port = args[0]
        // InetAddress = args[1]
//        PeerNode peerNode = new PeerNode();

//        try {
//            //Start thread
//            peerNode.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    /**
     * Node fetching from other nodes
     * @param uri address to peer, on form such as "localhost:3124"
     */
    public PeerNode (String uri, int port) {
        super(port);
        peerManager = new PeerManager(dataManager,port);
//        peerManager.addPeers(/*addresses*/);
    }

    //Start node without connecting to any server.
    public PeerNode (int port) {
        super(port);
    }

    public void readStream() {

        //get a chunk from every possible server.
        //Grab 20 chunks from each.
        //If it takes to loong, it will abort and continue with another node.

        Chunk chunk = dataManager.getHighestChunk();
    }

}
