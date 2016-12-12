package se.umu.cs.ads.fildil;

import org.apache.commons.io.IOUtils;

import java.io.*;
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

        Process processFFmpeg = null;
        try {
            processFFmpeg = new ProcessBuilder(command).start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }

        Process processVLC = null;
        try {
                processVLC = new ProcessBuilder("vlc","-").start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int n;
        InputStream input = processFFmpeg.getInputStream();
        OutputStream out = processVLC.getOutputStream();
        byte[] buf = new byte[1024];
        try {
            while((n=input.read(buf)) > -1) {
                out.write(buf,0,n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }
}
