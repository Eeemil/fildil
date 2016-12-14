package se.umu.cs.ads.fildil;

import com.google.protobuf.InvalidProtocolBufferException;
import se.umu.cs.ads.fildil.messages.Chunk;

import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.err.println("Usage: address port [video]");
        }

        String address = args[0];
        int port = Integer.parseInt(args[1]);


        UDPNet udpNet = null;

        try {
            udpNet = new UDPNet(address, port);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        if (args.length > 2) {
            String video = args[2];
            System.err.println("Starting as supplier, chopping chunks.");

            ArrayList<Chunk> chunks = null;
            try {
                chunks = VideoProperties.toChunks(video, 1024);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.err.println("Sending chunks.");

            for (int i = 0; i < chunks.size(); i++) {
                udpNet.sendChunk(chunks.get(i), 1338);
            }

        } else {
            System.err.println("Starting as receiver, awaiting chunks.");
            ArrayList<Chunk> chunks = new ArrayList<Chunk>();
            int cnt = 0;
            while (true) {
//                try {
                Chunk chunk = udpNet.getChunk();

//                if(cnt == chunk.getId())


                System.out.write(chunk.getBuf().toByteArray());
////                    break;

            }
        }

//        System.out.println("Done");
    }

}//}
