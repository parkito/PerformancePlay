package sockets.transfer.api;

import java.io.IOException;

/**
 * @author Artem Karnov @date 5/26/17.
 *         artem.karnov@t-systems.com
 */

public interface ServerApi {
    void start(int port) throws IOException;

    void stop() throws IOException;

}
