package sockets.transfer.api;

import java.io.IOException;

/**
 * @author Artem Karnov @date 5/26/17.
 *         artem.karnov@t-systems.com
 */

public interface ClientApi {
    void sentFile(String absoluteFilePath, String server, int port) throws IOException;
}
