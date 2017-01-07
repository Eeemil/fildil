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
        new Thread(this::exitCheck).start();
    }

    private void exitCheck() {
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE,"Interrupted while waiting for ffmpeg to exit", e);
            return;
        }
        int exitval = process.exitValue();
        if (exitval!=0) {
            byte[] buf = new byte[4096];
            try {
                process.getErrorStream().read(buf);
                String error = new String(buf, Charset.defaultCharset());
                LOGGER.severe("FFMPEG failed with code " + exitval + " : " +
                        (error.length() > 0 ? error : "No error message.") );
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE,"FFMPEG file failed. Also, error message could not be read",e);
            }
        } else {
            LOGGER.info("FFMPEG terminated successfully");
        }
    }

    /**
     * Blocks until ffmpeg has terminated
     * @return true if ffmpeg terminated successfully
     */
    public Boolean terminatedSuccessfully() throws InterruptedException {
        process.waitFor();
        return process.exitValue()==0;
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
