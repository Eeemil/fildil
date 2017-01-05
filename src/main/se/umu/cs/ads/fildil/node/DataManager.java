package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import se.umu.cs.ads.fildil.proto.autogen.Chunk;
import se.umu.cs.ads.fildil.proto.autogen.ChunkRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-30.
 */
public class DataManager {
    private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());
    private final List<byte[]> data = new ArrayList<>();
    private final AtomicInteger dataSize = new AtomicInteger(0);
    public static final int CHUNK_SIZE = 1024;


    /**
     * @return the chunk of the highest sequence number that has been received
     */
    public Chunk getHighestChunk() {
        int highest;
        return getChunk(getHighestId());
    }

    protected int getHighestId() {
        synchronized (data) {
            return data.size()-1;
        }
    }

    public ChunkRequest chunkRequest() {
        ChunkRequest req = ChunkRequest.newBuilder().setId(getHighestId()).build();
        return req;
    }

    public Chunk getChunk(int id) {
        Chunk.Builder chunkBuilder = Chunk.newBuilder();
        byte[] buf;
        synchronized (data) {
            buf = data.get(id);
        }
        if (buf == null) {
            LOGGER.info("Trying to retrieve non-existing chunk");
            chunkBuilder.setId(-1);
            chunkBuilder.setBuf(ByteString.EMPTY);
        } else {
            chunkBuilder.setId(id);
            chunkBuilder.setBuf(ByteString.copyFrom(buf));
        }

        return chunkBuilder.build();
    }

    public void addChunk(Chunk chunk) {
        int id = chunk.getId();
        byte[] buf;
        synchronized (data) {
            try {
                buf = data.get(id);
            } catch (IndexOutOfBoundsException e) {
                buf = null;
                //We are only checking if element exists (it shouldn't)
            }
        }
        if (buf != null) {
            LOGGER.warning("Trying to add chunk with ID " + id + ", but chunk is already added");
            return;
        }
        byte[] dataEntry = chunk.getBuf().toByteArray();
        data.add(id,dataEntry);
        dataSize.addAndGet(dataEntry.length);
    }

    public int getTotalDataSize() {
        return dataSize.get();
    }
}
