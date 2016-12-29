package se.umu.cs.ads.fildil.Network;

import se.umu.cs.ads.fildil.proto.autogen.Chunk;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by timmy on 15/12/16.
 */
public class ChunkBuffer {
    private ArrayList<Integer> requests;
    private ArrayList<Chunk> unorderd;
    private BlockingQueue<Chunk> ordered;


    public ChunkBuffer() {
        ordered = new LinkedBlockingDeque<Chunk>();

    }

    /**
     *
     * @return
     */
    public Chunk getChunk() throws InterruptedException {
        return ordered.take();
    }

    private void orderChunks() {
        //Thread that fetches blocks
        // order them

    }

    /**
     *
     * @param id
     */
    private void requestChunk(int id) {

    }

    //Temp buffer, storing unsorted chunks

    //private RequestBlock
    //private getChunk

}
