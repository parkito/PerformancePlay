package sockets.transfer.factories;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Artem Karnov @date 5/26/17.
 *         artem.karnov@t-systems.com
 */

public class ServerSocketFactory {
    private final static int DEFAULT_PORT = 4444;

    public static ServerSocket getNewServerSocketInstance(int port) throws IOException {
        return new ServerSocket(port);
    }

    public static ServerSocket getNewServerSocketInstance() throws IOException {
        return new ServerSocket(DEFAULT_PORT);
    }
}
