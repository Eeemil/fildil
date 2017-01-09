package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import se.umu.cs.ads.fildil.proto.autogen.Chunk;
import se.umu.cs.ads.fildil.proto.utils.ChunkUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-30.
 */
public class DataManager {

    public static final int FLAG_END_OF_STREAM_NOT_REACHED = -4;

    private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());
    private final Map<Integer,byte[]> data = new ConcurrentHashMap<>();
    private final AtomicInteger dataSize = new AtomicInteger(0);
    private int highestID = 0;
    private int endOfStreamID = FLAG_END_OF_STREAM_NOT_REACHED;
    public static final int CHUNK_SIZE = 1024;


    /**
     * @return the highest chunk id
     */
    protected int getHighestId() {
        return highestID;
    }

    /**
     * @return the end of stream ID
     */
    public int getEndOfStreamID() {
        return endOfStreamID;
    }

    /**
     * @param endOfStreamID the id AFTER the last chunk
     */
    public synchronized void setEndOfStreamID(int endOfStreamID) {
        if (endOfStreamID > this.endOfStreamID &&
                this.endOfStreamID != FLAG_END_OF_STREAM_NOT_REACHED) {
            LOGGER.warning("Trying to set end of stream ID to a higher ID than previously set: " + this.endOfStreamID + "->" + endOfStreamID + ", ignoring...");
            //Todo: peerManager may be trying to set end of stream ID to higher and higher numbers, check this
            return;
        }
        this.endOfStreamID = endOfStreamID;
    }

    /**
     * Returns a chunk if existent, by given id
     * @param id chunk id
     * @return chunk
     */
    public Chunk getChunk(int id) {
        if(id >= endOfStreamID ) {
            return ChunkUtils.createEndOfStreamChunk();
        } else if (!data.containsKey(id)) {
            return ChunkUtils.createNonExistantChunk();
        }

        byte[] buf = data.get(id);
        try {
            return Chunk.parseFrom(buf);
        } catch (InvalidProtocolBufferException e) {
            LOGGER.log(Level.SEVERE,"Could not parse chunk from data", e);
        }
        return null; //Should never happen
    }

    /**
     * Adds a chunk
     * @param chunk
     */
    public void addChunk(Chunk chunk) {
        int id = chunk.getId();
        synchronized (data) {
            if(!data.containsKey(chunk.getId())) {
                byte[] buf = chunk.toByteArray();
                data.put(chunk.getId(),buf);
                highestID = highestID < id ? id:highestID;
                dataSize.addAndGet(buf.length);
            } else {
                LOGGER.warning("Trying to add chunk with ID " + id + ", but chunk is already added");
                return;
            }
        }
    }

    /**
     * @return the total data size stored
     */
    public int getTotalDataSize() {
        return dataSize.get();
    }
}
