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
    private final ArrayList<ChunkStat> chunkQoSStats = new ArrayList<>();

    private final ArrayList<BandwidthStat> bandwidthStatDurations = new ArrayList<>();


    private final long startTime = System.currentTimeMillis();


    public static DataStats getInstance() {
        return dataStats;
    }

    //todo list the time the chunk as they arrive or list the time by the order we view them.

    public void chunkStatSent(int size, int id, long t1, long t2) {
        ChunkStat stat = new ChunkStat(size,t2-t1);
        synchronized (chunkStatsSent) {
            chunkStatsSent.add(stat);
        }
    }

    /**
     *
     * @param size of chunk
     * @param t1
     * @param t2
     */
    public void addStatChunkReceived(int size, long t1, long t2) {
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


    /**
     * @param t2 the time when chunk was retrieved
     */
    public void addStatBandwidthDuration(long t2) {

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
//        System.out.printf("elapsed time: %d  download: %.6f  Mbit/s\n", elapsedTime, speed);
        bandwidthStatDurations.add(new BandwidthStat(elapsedTime,speed));
    }

    /**
     *
     * @param size of the chunk
     * @param t1
     * @param t2
     */
    public void addStatQoS(int size, long t1, long t2) {
        long time = t2 - t1;

        ChunkStat stat = new ChunkStat(size,time);
        synchronized (chunkQoSStats) {
            chunkQoSStats.add(stat);
        }
    }

    public void printQoSStat() {
        synchronized (chunkQoSStats) {
            for (int i = 0; i < chunkQoSStats.size(); i++) {
                ChunkStat stat = chunkQoSStats.get(i);
                System.out.printf("#QoS# id: %d time: %d\n", i, stat.time);
            }
        }
    }

    public void printDownloadBandwidth() {
        synchronized (bandwidthStatDurations) {
            for(BandwidthStat stat:bandwidthStatDurations) {
                System.out.printf("#Bandwidht# time: %d ms download: %.6f  Mbit/s\n", stat.elapsedTime, stat.getSpeed());
            }
        }
    }



    public void printMissedChunks(long t2) {
        long elapsedTime = (t2 - startTime);
        System.out.printf("elapsed time: %d missed chunks: %d", elapsedTime,missCounter);
    }

    public void printHitChunks(long t2) {
        long elapsedTime = (t2 - startTime);
        System.out.printf("elapsed time: %d hit chunks: %d", elapsedTime, hitCounter);
    }



    /**
     * Stores average speed for a duration.
     */
    private class BandwidthStat {
        private long elapsedTime;
        private double speed;

        public BandwidthStat(long elapsedTime, double speed) {
            this.elapsedTime = elapsedTime;
            this.speed = speed;
        }

        public long getElapsedTime() {
            return elapsedTime;
        }

        public double getSpeed() {
            return speed;
        }

    }

    /**
     * Stores time of retrieval and it's size
     */
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