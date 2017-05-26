package sockets.transfer.implementation;

import sockets.transfer.api.ClientApi;
import sockets.transfer.api.ServerApi;

import java.io.IOException;

/**
 * @author Artem Karnov @date 25.05.2017.
 *         artem.karnov@t-systems.com
 */

public class ServiceStarter implements Runnable {
    private ClientApi client;
    private ServerApi server;
    private String serverAddress;
    private int port;
    private String absoluteFileName;

    public ServiceStarter(ServerApi client, int port) {
        this.server = client;
        this.port = port;
    }

    public ServiceStarter(ClientApi client, String absoluteFileName, String serverAddress, int port) {
        this.client = client;
        this.absoluteFileName = absoluteFileName;
        this.serverAddress = serverAddress;
        this.port = port;
    }

    @Override
    public void run() {
        if (client == null) {
            try {
                server.start(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                client.sentFile(absoluteFileName, serverAddress, port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
