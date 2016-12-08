package se.umu.cs.ads.fildil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        //argv0: filename for video file
        if (args.length < 1) {
            System.err.println("Supply file name!");
            System.exit(1);
        }
        String video = args[0];
        Process process = null;
        try {
            process = new ProcessBuilder("ffmpeg", "-i " + video, "-vcodec " +
                    "mpeg2video", "-acodec mp2", "-b:v", "3M", "-b:a 192k", "-muxrate 10M",
                    "-f mpegts" , "-").start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
        //Reads from STDERR
        //TODO: read from STDOUT to get stream
        InputStream inputStream = process.getErrorStream();
        byte[] buf = new byte[1024];
        int nRead;

        try {
            inputStream.read();
            System.out.println(inputStream.available());
            while ((nRead = inputStream.read(buf, 0, buf.length)) != -1) {
                System.out.println("Read " + nRead + "bytes");
                String str = new String(buf, "UTF-8");
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done");

    }
}
