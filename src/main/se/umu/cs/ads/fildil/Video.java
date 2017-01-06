package se.umu.cs.ads.fildil;

import com.sun.deploy.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FFMPEG video-reader-container-class-thing
 */
public class Video {
    private static final Logger LOGGER = Logger.getLogger(Video.class.getName());
    private final Process process;

    /**
     * Starts a FFMPEG video stream
     * @param src path to video file
     * @throws IOException
     */
    public Video (String src) throws IOException {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(src);
        command.add("-f");
        command.add("asf");
        command.add("-");

        LOGGER.info("Starting FFMPEG: \"" + StringUtils.join(command, " ") + "\"");
        process = new ProcessBuilder(command).start();
    }

    /**
     * Blocks until ffmpeg has terminated
     * @return true if ffmpeg terminated successfully
     */
    public Boolean terminatedSuccessfully() {
        int ret = process.exitValue();
        if (ret != 0) {
            byte[] buf = new byte[4096];
            try {
                process.getErrorStream().read(buf);
                String error = new String(buf, Charset.defaultCharset());
                LOGGER.severe("FFMPEG failed with code " + ret + " : " +
                        (error.length() > 0 ? error : "No error message.") );
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE,"FFMPEG file failed. Also, error message could not be read",e);
            }
            return false;
        }
        LOGGER.info("FFMPEG terminated successfully");
        return true;
    }

    /**
     * @return the FFMPEG video stream
     * @throws IOException
     */
    public InputStream getStream() throws IOException {
        return process.getInputStream();
    }

    @Deprecated
    private void printErrorString() throws IOException {
        InputStream inerr = process.getErrorStream();

        byte[] buf = new byte[1024];
        int n;

        while((n=inerr.read(buf)) > -1) {
            buf = Arrays.copyOfRange(buf,0,n);
            String str = new String(buf, "UTF-8");
            System.err.print(str);
        }

    }

}
