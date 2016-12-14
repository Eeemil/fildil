package se.umu.cs.ads.fildil;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //argv0: filename for video file
        if (args.length < 1) {
            System.err.println("Supply file name!");
            System.exit(1);
        }

        ArrayList<byte[]> chunks = null;
        try {
            chunks = VideoProperties.toChunks(args[0],1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(byte[] c:chunks) {
            try {
                System.out.write(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Done");
    }
}
