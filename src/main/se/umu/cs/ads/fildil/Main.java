package se.umu.cs.ads.fildil;

import java.io.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {


        if (args.length < 1) {
            System.err.println("Usage: video");
        }

        Node node = new Node();

        try {
            node.startServer(1337);
            Thread.sleep(1000);
            node.startClient("localhost",1337);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        serverSendChunksThread(node,args[0]);

        clientReadChunksThread(node);

    }

    public static void serverSendChunksThread(Node node, String src) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    byte[] buf = new byte[1024];
                    InputStream in =  VideoProperties.getStream(src);
                    int n;
                    for(int cnt = 0; (n=in.read(buf)) > -1;cnt++) {
                        buf = Arrays.copyOfRange(buf,0,n);
                        node.sendChunk(buf,cnt);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public static void clientReadChunksThread(Node node) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        byte[] bytes = node.getChunk();
                        System.out.write(bytes);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

}
