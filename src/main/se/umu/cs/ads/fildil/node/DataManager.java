package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import se.umu.cs.ads.fildil.proto.autogen.Chunk;
import se.umu.cs.ads.fildil.proto.autogen.ChunkRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-30.
 */
public class DataManager {

    public static final int FLAG_NO_CHUNK = -1;
    public static final int FLAG_END_OF_STREAM = -2;

    private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());
    private final Map<Integer,byte[]> data = new HashMap<>();
    private final AtomicInteger dataSize = new AtomicInteger(0);
    private int highestID = 0;
    private int endOfStreamID = 0;
    public static final int CHUNK_SIZE = 1024;


    /**
     * @return the chunk of the highest sequence number that has been received
     */
    public Chunk getHighestChunk() {
        int highest;
        return getChunk(getHighestId());
    }

    protected int getHighestId() {
        return highestID;
    }

    public int getEndOfStreamID() {
        return endOfStreamID;
    }

    public void setEndOfStreamID(int endOfStreamID) {
        this.endOfStreamID = endOfStreamID;
    }


    public Chunk getChunk(int id) {
        Chunk chunk = null;
        Chunk.Builder chunkBuilder = Chunk.newBuilder();


        synchronized (data) {
            if(id < data.size()) {
                byte[] buf = data.get(id);
                try {
                    if(buf != null) {
                        chunk = Chunk.parseFrom(buf);
                    }
                } catch (InvalidProtocolBufferException e) {
                    chunk = null;
                }
            }
        }

        if (chunk == null) {
            int idFlag = 0;
            if(id >= endOfStreamID) {
                idFlag = FLAG_END_OF_STREAM;
            } else {
                idFlag = FLAG_NO_CHUNK;
            }
            LOGGER.info("Trying to retrieve non-existing chunk");
            chunkBuilder.setId(idFlag);
            chunkBuilder.setBuf(ByteString.EMPTY);
            chunk = chunkBuilder.build();
        }

        return chunk;
    }

    public void addChunk(Chunk chunk) {
        int id = chunk.getId();
        byte[] buf =  null;
        synchronized (data) {
            if(!data.containsKey(chunk.getId())) {
                data.put(chunk.getId(),chunk.toByteArray());
                highestID = highestID < id ? id:highestID;
            } else {
                LOGGER.warning("Trying to add chunk with ID " + id + ", but chunk is already added");
                return;
            }
        }

        buf = chunk.toByteArray();
        dataSize.addAndGet(buf.length);
    }


    public int getTotalDataSize() {
        return dataSize.get();
    }
}
