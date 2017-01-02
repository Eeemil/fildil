package se.umu.cs.ads.fildil.node;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by emil on 2016-12-30.
 */
public class PeerNode extends Node {


    /**
     * Node fetching from other nodes
     * @param addresses
     */
    public PeerNode (List<InetAddress> addresses, int port) {
        super(port);
    }


}
