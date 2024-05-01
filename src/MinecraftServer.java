import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MinecraftServer {

    static int MAX_BYTE_SIZE = 2097151;

    public static void main(String[] args) {
        int port = 25565; // Minecraft default port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Minecraft server listening on port " + port);
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                    DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                    while (true) {
                        // Read data from the client
                        byte[] packetData = new byte[MAX_BYTE_SIZE];
                        //inputStream.readFully(packetData); // Read the rest of the packet data
                        int packetLength = inputStream.read(packetData,0,MAX_BYTE_SIZE); // Example: read packet ID

                        byte[] slice = Arrays.copyOfRange(packetData, 0, packetLength);

                        // Handle the received packet based on its ID
                        if (packetLength <= 0)
                            continue;

                        handlePacket(slice, output);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handlePacket(byte[] packetData, DataOutputStream d) {

        ByteReader reader = new ByteReader(packetData);
        int packetId = reader.readByte();

        System.out.println("Received packet with ID " + packetId + " and data " + ByteUtils.readByteArray(packetData));

        if (packetId == Enums.Packet_Type.LOGIN_REQUEST.address) { // Example: handle login packet
            // TODO Decode and process login packet data
            System.out.println("Received login packet");

        } else if (packetId == Enums.Packet_Type.HANDSHAKE.address) {
            // Handle another type of packet
            System.out.println("Received handshake packet");

            byte[] int1Bytes = ByteBuffer.allocate(2).putShort(Short.reverseBytes((short)Enums.Packet_Type.HANDSHAKE.address)).array();
            byte[] int2Bytes = ByteBuffer.allocate(2).putShort(Short.reverseBytes((short)1)).array();

            byte[] byteArray = ByteUtils.concatByteArrays(int1Bytes, int2Bytes, "-".getBytes());
            try {
                d.write(byteArray);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Returned handshake packet" + ByteUtils.readByteArray(byteArray));

        } else if (packetId == Enums.Packet_Type.SERVER_LIST_PING.address) {
            // Handle another type of packet
            System.out.println("Received server list packet");

            byte[] int1Bytes = ByteBuffer.allocate(2).putShort(Short.reverseBytes((short)Enums.Packet_Type.KICK.address)).array();

            byte[] byteArray = ByteUtils.concatByteArrays(int1Bytes, "A Minecraft Server§5§10".getBytes());
            try {
                d.write(byteArray);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Returned server list ping packet" + ByteUtils.readByteArray(byteArray));

        }
        //TODO add other cases!
    }
}
