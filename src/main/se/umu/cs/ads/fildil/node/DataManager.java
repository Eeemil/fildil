package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
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

    public static final int FLAG_NO_CHUNK = -1;
    public static final int FLAG_END_OF_STREAM = -2;


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
//            return data.size() == 0 ? 0:data.size() -1;
            return data.size();
        }
    }

//    public Chunk getChunk(int id) {
//        Chunk.Builder chunkBuilder = Chunk.newBuilder();
//        byte[] buf = null;
//        System.out.println("datasize: " + data.size());
//        System.out.println("id: " + id);
//
//        synchronized (data) {
//            if(id < data.size()) {
//                buf = data.get(id);
//            }
//        }
//
//        if (buf == null) {
//            LOGGER.info("Trying to retrieve non-existing chunk");
//            chunkBuilder.setId(-1);
//            chunkBuilder.setBuf(ByteString.EMPTY);
//        } else {
//            chunkBuilder.setId(id);
//            chunkBuilder.setBuf(ByteString.copyFrom(buf));
//        }
//
//        return chunkBuilder.build();
//    }

    public Chunk getChunk(int id) {
        Chunk chunk = null;
        Chunk.Builder chunkBuilder = Chunk.newBuilder();


        synchronized (data) {
            if(id < data.size()) {
                byte[] buf = data.get(id);
                try {
                    chunk =Chunk.parseFrom(buf);
                } catch (InvalidProtocolBufferException e) {
                    chunk = null;
                }
            }
        }

        if (chunk == null) {
            LOGGER.info("Trying to retrieve non-existing chunk");
            chunkBuilder.setId(-1);
            chunkBuilder.setBuf(ByteString.EMPTY);
            chunk = chunkBuilder.build();
        }

        return chunk;
    }

    public void addChunk(Chunk chunk) {
        int id = chunk.getId();
        byte[] buf =  null;
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

//        byte[] dataEntry = chunk.getBuf().toByteArray();
        id = id == FLAG_END_OF_STREAM ? data.size():id;
        data.add(id,chunk.toByteArray());

//        dataSize.addAndGet(dataEntry.length);
    }


//    public void addChunk(Chunk chunk) {
//        int id = chunk.getId();
//        byte[] buf;
//        synchronized (data) {
//            try {
//                buf = data.get(id);
//            } catch (IndexOutOfBoundsException e) {
//                buf = null;
//                //We are only checking if element exists (it shouldn't)
//            }
//        }
//        if (buf != null) {
//            LOGGER.warning("Trying to add chunk with ID " + id + ", but chunk is already added");
//            return;
//        }
//
//        byte[] dataEntry = chunk.getBuf().toByteArray();
//        id = id == FLAG_END_OF_STREAM ? data.size():id;
//        data.add(id,dataEntry);
//
//        dataSize.addAndGet(dataEntry.length);
//    }

    public int getTotalDataSize() {
        return dataSize.get();
    }
}
