package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import se.umu.cs.ads.fildil.proto.autogen.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-30.
 */
public class DataManager {
    private final Logger LOGGER = Logger.getLogger(DataManager.class.getName());
    private final List<byte[]> data = new ArrayList<>();

    /**
     * @return the chunk of the highest sequence number that has been received
     */
    public Chunk getHighestChunk() {
        int highest;
        synchronized (data) {
            highest = data.size();
        }
        return getChunk(highest);
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
            buf = data.get(id);
        }
        if (buf != null) {
            LOGGER.warning("Trying to add chunk with ID " + id + ", but chunk is already added");
            return;
        }
        data.add(id,chunk.getBuf().toByteArray());
    }
}
