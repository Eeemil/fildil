package se.umu.cs.ads.fildil;

import com.google.protobuf.InvalidProtocolBufferException;
import se.umu.cs.ads.fildil.messages.Chunk;

import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        if (args.length > 1) {
            System.err.println("Starting as supplier, chopping chunks.");
            UDPNet udpNet = null;
            try {
                udpNet = new UDPNet(args[1]);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            ArrayList<Chunk> chunks = null;
            try {
                chunks = VideoProperties.toChunks(args[0],1024);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.err.println("Sending chunks.");

            for(int i = 0; i < 11540; i++ ) {
                System.err.println("Sending chunk: "+ i);
                udpNet.sendChunk(chunks.get(i));
            }

        } else {
            System.err.println("Starting as receiver, awaiting chunks.");
            UDPNet udpNet = null;
            try {
                udpNet = new UDPNet("localhost");
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }


            while(true) {
                try {
                    Chunk chunk = udpNet.getChunk();
                    System.out.write(chunk.getBuf().toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");
    }
}
