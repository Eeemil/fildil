package se.umu.cs.ads.fildil.node;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Collects a variety of data for....
 */
public class DataStats {
    private static DataStats dataStats = new DataStats();

    private AtomicInteger missCounter = new AtomicInteger(0);
    private AtomicInteger hitCounter = new AtomicInteger(0);

    private AtomicInteger recievedChunksCounter = new AtomicInteger(0);
    private AtomicInteger sentChunksCounter = new AtomicInteger(0);

    private final ArrayList<ChunkStat> chunkStatsSent = new ArrayList<>();
    private final ArrayList<ChunkStat> chunkStatsReceived = new ArrayList<>();

    private final long startTime = System.currentTimeMillis();


    public static DataStats getInstance() {
        return dataStats;
    }

    //todo list the time the chunk as they arrive or list the time by the order we view them.

    public void chunkStatSent(int size, long t1, long t2) {
        ChunkStat stat = new ChunkStat(size,t2-t1);
        synchronized (chunkStatsSent) {
            chunkStatsSent.add(stat);
        }
    }

    public void chunkStatReceived(int size, long t1, long t2) {
        ChunkStat stat = new ChunkStat(size,t2-t1);
        synchronized (chunkStatsReceived) {
            chunkStatsReceived.add(stat);
        }
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


    public void printAverageBandwidthDown(long t2) {

        long elapsedTime = (t2 - startTime);
        long totTime = 0;
        long numberOfBits= 0;

        synchronized (chunkStatsReceived) {
            for (ChunkStat stat : chunkStatsReceived) {
                totTime += stat.getTime();
                numberOfBits += stat.getSize();
            }
        }
        numberOfBits *= 8;

        double speed = (((double) numberOfBits)/(1000*1000)) / (((double) totTime)/1000);
        System.out.printf("elapsed time: %d  download: %.6f  Mbit/s\n", elapsedTime, speed);
    }

    public void printMissedChunks(long t2) {
        long elapsedTime = (t2 - startTime);
        System.out.printf("elapsed time: %d missed chunks: %d", elapsedTime,missCounter);
    }

    public void printHitChunks(long t2) {
        long elapsedTime = (t2 - startTime);
        System.out.printf("elapsed time: %d hit chunks: %d", elapsedTime, hitCounter);
    }


    private class ChunkStat {
        private long time;
        private int size;

        public ChunkStat(int size, long time) {
            this.time = time;
            this.size = size;
        }

        public long getTime() {
            return time;
        }

        public int getSize() {
            return size;
        }
    }
}
