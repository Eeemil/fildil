package se.umu.cs.ads.fildil.node;

import com.google.protobuf.ByteString;
import se.umu.cs.ads.fildil.Video;
import se.umu.cs.ads.fildil.proto.autogen.Chunk;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by emil on 2016-12-30.
 */
public class PrimaryNode extends Node {
    private static final Logger LOGGER = Logger.getLogger(PrimaryNode.class.getName());

    public static void main(String[] args) throws IOException {
        String DEFAULT_PATH = "movie/valve.mp4";
        String uri;
        if (args.length == 0) {
            System.err.println("No arguments specified, using default path");
            uri = DEFAULT_PATH;
        } else {
            uri = args[0];
        }
        Path path = FileSystems.getDefault().getPath("movie/valve.mp4");
        LOGGER.info("Path: " + path.toString());
        PrimaryNode node = new PrimaryNode(path, 8100);
        node.start();
        System.err.println("Running...");
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Exiting...");
                return;
            }
        }
    }
    /**
     * Primary node, fetching from primary source
     * @param source
     */
    public PrimaryNode (Path source, int port) throws UnknownHostException {
        super(port);
        Thread t = new Thread(() -> fillStream(source));
        t.start();
    }

    /**
     * Fills stream for distributing content
     * @param path path to content
     */
    private void fillStream(Path path) {
        LOGGER.info("Reading " + path.toString() + " into chunks of size " + DataManager.CHUNK_SIZE);
        Video video = null;
        try {
            video = new Video(path.toString());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not open video file stream: ", e);
            return;
        }
        try {
            Chunk.Builder chunkBuilder = Chunk.newBuilder();
            byte[] buf = new byte[DataManager.CHUNK_SIZE];

            InputStream in =  video.getStream();
            int bytesRead = 0;
            int cnt;
            for(cnt = 0; (bytesRead=in.read(buf,0,buf.length)) > -1;cnt++) {
                int offset = bytesRead;
                while ((bytesRead = in.read(buf, offset, buf.length - offset))
                        != -1) {
                    //Keep reading and move offset/limit in order to force-fill buffer.
                    offset += bytesRead;
                    if (offset >= buf.length) {
                        break;
                    }
                }

                if (bytesRead == -1) {
                    LOGGER.info("Fully read " + path.toString() + ", total: " + (cnt*DataManager.CHUNK_SIZE+offset) + " bytes");
                }

                //Total number of bytes read == the total offset distance moved
                //(the buffer may not be filled entirely on EOF)
                bytesRead=offset;

                chunkBuilder.setId(cnt);
                chunkBuilder.setBuf(ByteString.copyFrom(buf,0,bytesRead));
                dataManager.addChunk(chunkBuilder.build());
                if (cnt % 1000 == 0) {
                    LOGGER.finer("Status: read " + (cnt*DataManager.CHUNK_SIZE+bytesRead) + " bytes...");
                }

            }

            dataManager.setEndOfStreamID(cnt);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Could not read path " + path.toString(),e);
            stop();
        }

        try {
            if (!video.terminatedSuccessfully()) {
                LOGGER.severe("Video source did not terminate successfully, aborting...");
                stop();
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE,"Interrupted while checking exit status on video source",e);
        }
    }

}
