package se.umu.cs.ads.fildil.Network;

import com.google.protobuf.InvalidProtocolBufferException;
//import org.apache.commons.lang.ArrayUtils;
import se.umu.cs.ads.fildil.proto.autogen.Chunk;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Created by c12ton on 12/14/16.
 */
public class UDP {
    private static final int TIME_OUT = 500;
    private static final int PACKET_SIZE = 65507;
    private static final int PACKET_HEADER_SIZE = 4;

    private InetAddress ipAddress;
    private int port;
    private DatagramSocket socket;

    /**
     * @param address the recievers address
     * @throws SocketException
     */
    public UDP(String address, int port) throws SocketException, UnknownHostException {
        ipAddress = InetAddress.getByName(address);
        this.port = port;
        setup();
    }

    private void setup() throws SocketException {
        socket = new DatagramSocket(port);
        socket.setSoTimeout(TIME_OUT);
    }

    public void sendChunk(Chunk chunk, int port) {

        byte[] header = ByteBuffer.allocate(PACKET_HEADER_SIZE)
                                    .order(ByteOrder.LITTLE_ENDIAN)
                                        .putInt(chunk.toByteArray()
                                                    .length).array();
//
//        byte[] data = ArrayUtils.addAll(header,chunk.toByteArray());
//        DatagramPacket sendPacket = new DatagramPacket(data, data.length,
//                                                       ipAddress,port);
        try {
//            socket.send(sendPacket);
            Thread.sleep(25);
//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for chunk.
     * @return
     */
    public Chunk getChunk() throws InvalidProtocolBufferException {
        byte[] buffer = new byte[PACKET_SIZE]; //rename this to header
        DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
        try {
            boolean empty = true;
            while(empty) {
                try {
                    socket.receive(packet);
                    empty = false;
                } catch (SocketTimeoutException e) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] chunkData = parseRecievedPacket(packet.getData());
        Chunk chunk = Chunk.parseFrom(chunkData);
        return  chunk;
    }

    private byte[] parseRecievedPacket(byte[] data) {
        byte[] header = Arrays.copyOfRange(data,0,PACKET_HEADER_SIZE);
        int chunkSize = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN).getInt();
        byte[] chunkData = Arrays.copyOfRange(data,PACKET_HEADER_SIZE,
                                              chunkSize+PACKET_HEADER_SIZE);
        return chunkData;
    }
}
