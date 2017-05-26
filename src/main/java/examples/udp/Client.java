package sockets.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @author Artem Karnov @date 25.05.2017.
 *         artem.karnov@t-systems.com
 */

public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);

    private final static int PORT = 4444;
    private final static String TEXT = "HELLO";
    private final static String ADDRESS = "localhost";

    public static void main(String args[]) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            byte[] m = new byte[10];
            m[1] = 'd';
            InetAddress aHost = InetAddress.getByName(ADDRESS);
            DatagramPacket request = new DatagramPacket(m, 2, aHost, PORT);
            aSocket.send(request);
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            logger.info("Reply: " + new String(reply.getData()));
        } catch (SocketException e) {
            logger.error("Socket: " + e.getMessage());
        } catch (IOException e) {
            logger.error("IO: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }
}
