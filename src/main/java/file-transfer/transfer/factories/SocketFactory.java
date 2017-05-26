package sockets.transfer.factories;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Artem Karnov @date 5/26/17.
 *         artem.karnov@t-systems.com
 */

public class SocketFactory {
    private final static int DEFAULT_PORT = 4444;
    private final static String DEFAULT_SERVER = "localhost";

    public static Socket getNewSocketInstance(String server, int port) throws IOException {
        return new Socket(server, port);
    }

    public static Socket getNewSocketInstance() throws IOException {
        return new Socket(DEFAULT_SERVER, DEFAULT_PORT);
    }
}
