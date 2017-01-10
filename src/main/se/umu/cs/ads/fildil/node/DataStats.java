package se.umu.cs.ads.fildil.node;

import se.umu.cs.ads.fildil.proto.autogen.Chunk;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by c12ton on 1/10/17.
 */
public class DataStats {
    private static DataStats dataStats = new DataStats();

    private AtomicInteger missCounter = new AtomicInteger(0);
    private AtomicInteger hitCounter = new AtomicInteger(0);

    private AtomicInteger recievedChunksCounter = new AtomicInteger(0);
    private AtomicInteger sentChunksCounter = new AtomicInteger(0);

    private ArrayList<ChunkStat> chunkStats = new ArrayList<>();


    public static DataStats getInstance() {
        return dataStats;
    }

    public void addChunkData(int size, long t1, long t2) {
        ChunkStat stat = new ChunkStat(size,t2-t1);
        chunkStats.add(stat);
    }

    public void incrementMissCounter() {
        missCounter.incrementAndGet();
    }

    public void incrementHitCounter() {
        hitCounter.incrementAndGet();
    }

    public void incrementRecievedChunksCounter() {
        recievedChunksCounter.incrementAndGet();
    }

    public void incrementSentChunksCounter() {
        sentChunksCounter.incrementAndGet();
    }


    public String getResult() {
        return null;
    }

    private class ChunkStat {
        private int size;
        private long time;

        public ChunkStat(int size, long time) {
            this.size = size;
            this.time = time;
        }

        public long getTime() {
            return time;
        }

        public int getSize() {
            return size;
        }

    }

}
