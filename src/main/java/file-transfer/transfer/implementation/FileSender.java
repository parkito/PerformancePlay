package sockets.transfer.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sockets.transfer.api.ClientApi;
import sockets.transfer.factories.SocketFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Artem Karnov @date 25.05.2017.
 *         artem.karnov@t-systems.com
 */

public class FileSender implements ClientApi {
    private static final Logger logger = LogManager.getLogger(FileSender.class);

    public void sentFile(String absoluteFilePath, String server, int port) throws IOException {
        Socket socket = null;
        DataOutputStream outputData = null;
        FileInputStream inputStream = null;

        try {
            logger.info("Starting transferring of file " + absoluteFilePath);

            File file = new File(absoluteFilePath);
            socket = SocketFactory.getNewSocketInstance(server, port);

            logger.info("Connecting to " + socket.getInetAddress() + ":" + socket.getPort());

            outputData = new DataOutputStream(socket.getOutputStream());
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];

            while (inputStream.read(buffer) > 0) {
                outputData.write(buffer);
            }

            logger.info("File " + absoluteFilePath + " successfully transferred");
        } catch (IOException ex) {
            logger.error("File sending is unsuccessful");
            ex.printStackTrace();
        } finally {
            inputStream.close();
            outputData.close();
            socket.close();
        }
    }
}
