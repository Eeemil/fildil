package se.umu.cs.ads.fildil.proto.utils;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import se.umu.cs.ads.fildil.proto.autogen.Chunk;

/**
 * Created by eeemil on 2017-01-09.
 */
public class ChunkUtils {

    public static final int FLAG_CHUNK_NO_EXISTS = -1;
    public static final int FLAG_END_OF_STREAM = -2;

    public static Chunk createNonExistantChunk() {
        return createChunk(FLAG_CHUNK_NO_EXISTS,null);
    }

    public static Chunk createChunk(int id, byte[] data) {
        ByteString byteString;
        if (data == null) {
            byteString = ByteString.EMPTY;
        } else {
            byteString = ByteString.copyFrom(data);
        }
        Chunk.Builder builder = Chunk.newBuilder();
        builder.setId(id);
        builder.setBuf(byteString);
        return builder.build();
    }

    public static Chunk createEndOfStreamChunk() {
        return createChunk(FLAG_END_OF_STREAM, null);
    }
}
