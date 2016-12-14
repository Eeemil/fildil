package se.umu.cs.ads.fildil;

import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.net.*;

/**
 * Created by c12ton on 12/14/16.
 */
public class UDPNet {
    public static final int CHUNK_SIZE= 1024;
    private InetAddress ipAddress;
    private static final int PORT= 1337;
    private static final int TIME_OUT = 500;

    private DatagramSocket socket;

    /**
     * @param address the recievers address
     * @throws SocketException
     */
    public UDPNet(String address) throws SocketException, UnknownHostException {
        ipAddress = InetAddress.getByName(address);
        setup();
    }

    private void setup() throws SocketException {
        socket = new DatagramSocket(PORT);
        socket.setSoTimeout(TIME_OUT);
    }

    public void sendChunk(byte[] bytes) {
       DatagramPacket sendPacket = new DatagramPacket(bytes,bytes.length,ipAddress,PORT);
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
            boolean empty = true;
            while(empty) {
                try {
                    socket.receive(receivePacket);
                    empty = false;
                } catch (SocketTimeoutException e) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  receivePacket.getData();
    }
}
