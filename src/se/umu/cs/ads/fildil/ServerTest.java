package se.umu.cs.ads.fildil;

import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.net.*;

/**
 * Created by c12ton on 12/14/16.
 */
public class ServerTest {
    public static final int CHUNK_SIZE= 1024;
    private InetAddress ipAddress;
    private int port = 1337;

    private DatagramSocket socket;

    /**
     * @param IPAddress recievers address
     * @throws SocketException
     */
    public ServerTest(InetAddress IPAddress) throws SocketException {
        setup();
    }

    private void setup() throws SocketException {
        socket = new DatagramSocket(1337);
    }

    public void sendChunk(byte[] bytes) {
       DatagramPacket sendPacket = new DatagramPacket(bytes,bytes.length,ipAddress,port);
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for chunk.
     * @return
     */
    public byte[] getChunk() {
        byte[] data = new byte[CHUNK_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(data,data.length);
        try {
            socket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
