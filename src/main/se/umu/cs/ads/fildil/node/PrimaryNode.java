package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import se.umu.cs.ads.fildil.VideoProperties;
import se.umu.cs.ads.fildil.proto.autogen.Chunk;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-30.
 */
public class PrimaryNode extends Node {
    private static final Logger LOGGER = Logger.getLogger(PrimaryNode.class.getName());


    /**
     * Primary node, fetching from primary source
     * @param source
     */
    public PrimaryNode (Path source, int port) {
        super(port);
        Thread t = new Thread(() -> readPath(source));
        t.start();
    }

    private void readPath(Path path) {
        try {
            Chunk.Builder chunkBuilder = Chunk.newBuilder();
            byte[] buf = new byte[1024];
            InputStream in =  VideoProperties.getStream(path.toString());
            int n;
            for(int cnt = 0; (n=in.read(buf)) > -1;cnt++) {
                //buf = Arrays.copyOfRange(buf,0,n);
                chunkBuilder.setId(cnt);
                chunkBuilder.setBuf(ByteString.copyFrom(buf,0,n));
                dataManager.addChunk(chunkBuilder.build());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Could not read path " + path.toString(),e);
            abort();
        }
    }

    /**
     * Aborts everything in a suitable manner
     *
     * To be used in severe crashes and such
     */
    private void abort() {
        LOGGER.severe("Aborting...");
        //Todo: implement
    }
}
