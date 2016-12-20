package se.umu.cs.ads.fildil;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-20.
 */
public class Node {
    private static final Logger LOGGER = Logger.getLogger(Node.class.getName());
    private final UUID uuid = UUID.randomUUID();
    //private final Server server;

    public Node(int port) {
        //server = ServerBuilder.forPort(port)
        //        .addService(new StreamerImpl)
    }

}
