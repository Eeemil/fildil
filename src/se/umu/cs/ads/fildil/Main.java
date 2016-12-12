package se.umu.cs.ads.fildil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //argv0: filename for video file
        if (args.length < 1) {
            System.err.println("Supply file name!");
            System.exit(1);
        }

        String video = args[0];
        List<String> command = new ArrayList<String>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(video);
        command.add("-vcodec");
        command.add("mpeg2video");
        command.add("-acodec");
        command.add("mp2");
        command.add("-b:v");
        command.add("3M");
        command.add("-b:a");
        command.add("192k");
        command.add("-muxrate");
        command.add("10M");
        command.add("-f");
        command.add("asf");  //- video format (TEMP!)
        command.add("-");

        Process process = null;
        try {

//            process = new ProcessBuilder("ffmpeg", "-i " + video, "-vcodec " +
//                "mpeg2video", "-acodec mp2", "-b:v", "3M", "-b:a 192k", "-muxrate 10M",
//                "-f mpegts" , "-").start();

            process = new ProcessBuilder(command).start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }



        //Reads from STDERR
        //TODO: read from STDOUT to get stream
//        InputStream inputStream = process.getErrorStream();
        InputStream inputStream = process.getInputStream();
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
