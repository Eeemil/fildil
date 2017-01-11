package se.umu.cs.ads.fildil.node;

import com.google.protobuf.InvalidProtocolBufferException;
import se.umu.cs.ads.fildil.proto.autogen.Chunk;
import se.umu.cs.ads.fildil.proto.utils.ChunkUtils;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-30.
 */
public class PeerNode extends Node {
    private static final Logger LOGGER = Logger.getLogger(PeerNode.class.getName());
    private final static String FLAG_PRIMARY_ADDR = "-prim";
    private final static String FLAG_NO_VIDEO = "-novideo";
    private final int CHUNKS_PER_THREAD = 10;


    private int idCounter =  0;


    public static void main(String[] args) {
        Boolean playVideo = true;
        if(args.length == 0) {
            System.err.println("Usage: port ["+FLAG_PRIMARY_ADDR+" addr:port] [addr:port]... [addr:port]");
            System.exit(0);
        }

        ArrayList<String> peers = new ArrayList<>();
        String primAddr = null;
        for(int i = 1; i < args.length; i++) {
                if(args[i].equals(FLAG_PRIMARY_ADDR)) {
                    i++;
                    primAddr = args[i];
                } else if (args[i].equals(FLAG_NO_VIDEO)) {
                    playVideo = false;
                } else {
                    peers.add(args[i]);
                }

        }

        //Finding peers based on arguments
        int port = new Integer(args[0]);
        PeerNode peerNode = null;
        try {
            peerNode = new PeerNode(peers,port);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Could not start node", e);
            System.exit(1);
        }

        if (primAddr != null) {
            peerNode.setPrimary(primAddr);
        }

        try {
            peerNode.startReadingStream();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (playVideo) {
            LOGGER.info("Writing video stream to stdout");
            peerNode.writeToStdout();
        } else {
            LOGGER.info("Writing statistics to stdout");
            peerNode.printStats();
        }

    }

    /**
     * Node fetching from other nodes
     * @param peers address to peer, on form such as "localhost:3124"
     */
    public PeerNode (ArrayList<String> peers, int port) throws IOException {
        super(port);
        start();
        for (String uri :
                peers) {
            peerManager.addPeer(uri);
        }
    }

    private void setPrimary(String primAddr) {
        peerManager.setPrimary(primAddr);
    }
    /**
     *  Reads either the primary if given the address. Else
     *  reads from multiple known peers.
     * @throws InterruptedException
     */
    public void startReadingStream() throws InterruptedException {

        //TEMP!
        for(int i = 0; i < 3; i++) {
            Thread t = new Thread(this::readStream);
            t.start();
        }

    }

    /**
     * Read stream from random peer.
     */
    private void readStream() {

        int[] idsToFetch;
        while ((idsToFetch = getPendingChunkIDs()) != null){

            for(int i = 0; i < idsToFetch.length;) {
                Chunk chunk = peerManager.getChunk(idsToFetch[i]);

                switch (chunk.getId()) {
                    case ChunkUtils.FLAG_END_OF_STREAM:
                        dataManager.setEndOfStreamID(idsToFetch[i]);
                        return;
                    case ChunkUtils.FLAG_CHUNK_NO_EXISTS:
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            LOGGER.log(Level.SEVERE, "Terminated while waiting for retrying to refetch a chunk", e);
                        }
                        break;
                    default:
                        dataManager.addChunk(chunk);
                        LOGGER.finer("Got package: " + idsToFetch[i]);

                        if(idsToFetch[i] % 1000 == 0) {
                            LOGGER.finer("Got package: " + idsToFetch[i]);
                        }
                        i++;
                        break;
                }
            }
        }
    }

    public void printStats() {
        Chunk c = null;
        int i = 0;
        do {
            try {
                long t1 = System.currentTimeMillis();
                c = dataManager.getChunkBlocking(i++);
                long t2 = System.currentTimeMillis();
                System.out.println("#" + c.getId() + ":\t" + (t2-t1) + " ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (c.getId() != ChunkUtils.FLAG_END_OF_STREAM);

    }


    //Extends this, and write to stdout
    public void writeToStdout() {
                int i = 0;
                for(;;i++) {
                    try {
                        Chunk c = dataManager.getChunkBlocking(i);
//                        System.out.write(c.getBuf().toByteArray());
//                    } catch (InvalidProtocolBufferException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
    }

    /**
     * updates the queue by adding new incremented id:es if end of stream hasn't been found.
     * @retrun empty if there's no more work or array of id:es.
     */
    private synchronized int[] getPendingChunkIDs() {

        if (isEndOfStream()) {
            return null;
        }

        int[] chunkIDs = new int[CHUNKS_PER_THREAD];
        int id;
        int i;
        for(id = idCounter, i = 0; id < (idCounter + CHUNKS_PER_THREAD); id++, i++) {
            chunkIDs[i] = id;
        }
        idCounter = id;
        return chunkIDs;
    }

    /**
     * @return true if a chunk that has an end of stream has been found.
     */
    private boolean isEndOfStream() {
       return dataManager.getEndOfStreamID() != dataManager.FLAG_END_OF_STREAM_NOT_REACHED;
    }

}
