package se.umu.cs.ads.fildil;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.lang.ArrayUtils;
import se.umu.cs.ads.fildil.messages.Chunk;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

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

    public void sendChunk(Chunk chunk) {

        byte[] size = ByteBuffer.allocate(4).putInt(chunk.toByteArray().length).array();

        byte[] data = ArrayUtils.addAll(size,chunk.toByteArray());
        DatagramPacket sendPacket = new DatagramPacket(data, data.length,
                                                       ipAddress,PORT);
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
    public Chunk getChunk() throws InvalidProtocolBufferException {
        byte[] data = new byte[4]; //rename this to header
        DatagramPacket receivePacket = new DatagramPacket(data,data.length);
        try {
            boolean empty = true;
            while(empty) {
                try {
                    socket.receive(receivePacket);
                    int size = ByteBuffer.wrap(receivePacket.getData()).get();
                    System.out.println("Size: "+ size);
                    data = new byte[size];
                    receivePacket = new DatagramPacket(data,data.length);
                    empty = false;
                } catch (SocketTimeoutException e) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Chunk chunk = Chunk.parseFrom(receivePacket.getData());
        return  chunk;
    }

}
