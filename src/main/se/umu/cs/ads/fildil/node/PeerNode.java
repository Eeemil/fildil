package se.umu.cs.ads.fildil.node;

import se.umu.cs.ads.fildil.proto.autogen.Chunk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-30.
 */
public class PeerNode extends Node {
    private static final Logger LOGGER = Logger.getLogger(PeerNode.class.getName());

    private PeerManager peerManager;
    private final static String FLAG_PRIMARY_ADDR = "-prim";
    private String primAddr;
    private final Object cntLock = new Object();
    private int  idTaskCnt =  0;
    private boolean endOfStream = false;

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
            peerNode.startReadingStream();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.err.println("Running");
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Exiting...");
                return;
            }
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

    /**
     *  Reads either the primary if given the address. Else
     *  reads from multiple known peers.
     * @throws InterruptedException
     */
    public void startReadingStream() throws InterruptedException {
        //Multiple threads start here
        if(primAddr != null) {
            readFromPrimary();
        } else {
            //TEMP!
            for(int i = 0; i < 3; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    readPeerStream();
                }
            };
            t.start();
            }
        }
    }

    /**
     * Read stream from random peer.
     */
    private void readPeerStream() {

        int[] tasks = null;

        do {
            tasks = getTasks();

            for(int i = 0; tasks != null && i < tasks.length;) {
                Chunk chunk = peerManager.getChunk(tasks[i]);

                if(chunk.getId() != DataManager.FLAG_NO_CHUNK) {
                    dataManager.addChunk(chunk);
//                    System.out.println();
                    LOGGER.finer("Got package: " + tasks[i]);
                    i++;
                    //DEBUG purpose
//                    if(tasks[i] % 1000 == 0) {
//                        LOGGER.finer("Got package: " + tasks[i]);
//                    }

                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (chunk.getId() == DataManager.FLAG_END_OF_STREAM) {
                    setEndOfStream(true);
                    dataManager.setEndOfStream(chunk.getId());
                    break;
                }
            }
        } while(tasks != null);
    }

    /**
     * Read from primary node tills there no more chunks to read.
     */
    private void readFromPrimary() throws InterruptedException {
        StreamerClient streamerClient = new StreamerClient(primAddr,peerManager.getPeerInfo());
        int sleep = 100;
        int[] tasks = null;

        while ((tasks = getTasks()) != null){
            for(int i = 0; i < tasks.length;) {
                Chunk chunk = streamerClient.requestChunk(tasks[i]);
                if(chunk.getId() != DataManager.FLAG_NO_CHUNK) {
//                    System.out.println("Dah!: " + tasks[i]);
                    LOGGER.finer("Got package: " + tasks[i]);
                    dataManager.addChunk(chunk);
                    //DEBUG purpose
                    if(tasks[i] % 1000 == 0) {
                        LOGGER.finer("Got package: " + tasks[i]);
                    }

                    i++;
                } else {
                    Thread.sleep(100);
                }

                if (chunk.getId() == DataManager.FLAG_END_OF_STREAM) {
                    setEndOfStream(true);
                    dataManager.setEndOfStream(chunk.getId());
                }
            }
        }
    }



    /**
     * updates the que by adding new incremented id:es if end of stream hasn't been found.
     * @retrun empty if there's no more work or array of id:es.
     */
    private int[] getTasks() {

        if(!isEndOfStream()) {
            //add random number
            synchronized (cntLock) {
//                Random gen = new Random();
//                int incValue = gen.ints(idTaskCnt,10);
                int incValue = 10;
                int[] tasks = new int[incValue];
                int id;
                int i;
                for(id = idTaskCnt, i = 0; id < (idTaskCnt+incValue); id++, i++) {
                    tasks[i] = id;
                }
                idTaskCnt = id;
                return tasks;
            }
        }

        return null;
    }

    /**
     * @return true if a chunk that has an end of stream has been found.
     */
    private boolean isEndOfStream() {
        synchronized (cntLock) {
            return endOfStream;
        }
    }

    /**
     * @param b
     */
    private void setEndOfStream(boolean b) {
        synchronized (cntLock) {
            endOfStream = b;
        }
    }

}
