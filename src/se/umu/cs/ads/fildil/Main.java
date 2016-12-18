package se.umu.cs.ads.fildil;

import se.umu.cs.ads.fildil.Network.UDP;
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


        UDP udpNet = null;

        try {
            udpNet = new UDP(address, port);
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
                chunks = VideoProperties.toChunks(video, 40024);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.err.println("Sending chunks.");

            for (int i = 0; i < chunks.size(); i++) {
                System.err.println("id: " + chunks.get(i).getId());
                udpNet.sendChunk(chunks.get(i), 1338);
//                udpNet.sendBytes(chunks.get(i).getBuf().toByteArray());
            }

        } else {
            System.err.println("Starting as receiver, awaiting chunks.");
            ArrayList<Chunk> chunks = new ArrayList<Chunk>();
            int cnt = 0;
            int tries = 0;
            while (true) {
//                try {
//                byte[] buf = udpNet.getBytes();
                Chunk chunk = udpNet.getChunk();
                if(cnt == chunk.getId()) {
                    System.err.println("Chunk retrived!");
                    System.out.write(chunk.getBuf().toByteArray());
                } else {
                    tries++;
                    if(tries > 50) {
                        tries = 0;
                        cnt = chunk.getId();
                        System.out.println("Doh lost chunk");
                    }
                }
//

//
//                if(cnt == chunk.getId() || (cnt < chunk.getId() && cnt+100 > chunk.getId())) {
////                    System.err.println("Cnt: " + cnt);
//                    cnt = chunk.getId()+1;
//                    System.out.write(chunk.getBuf().toByteArray());
//                } else {
////                    System.out.println("Darn: " + chunk.getId());
//                    tries++;
//                    if(tries > 50) {
//                        cnt = chunk.getId();
//                        tries = 50;
//                    }
//                    break;
//                }

            }

        }

//        System.out.println("Done");
    }

}//}
