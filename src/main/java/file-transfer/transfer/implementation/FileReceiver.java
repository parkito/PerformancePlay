package sockets.transfer.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sockets.transfer.api.ServerApi;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Karnov @date 25.05.2017.
 *         artem.karnov@t-systems.com
 */

public class FileReceiver implements ServerApi, Runnable {
    private static final Logger logger = LogManager.getLogger(FileReceiver.class);

    private int port;
    private static List<Socket> clientSocketsPool;
    private static ServerSocket serverSocket;
    private static volatile int fileNumber;
    private static boolean jobStatus;

    public void start(int port) throws IOException {
        logger.info("Server is starting");
        jobStatus = true;
        clientSocketsPool = new ArrayList<>();
        this.port = port;

        try {
            if (serverSocket == null) {
                serverSocket = new ServerSocket(port);
            }
            logger.info("Server is started on port " + port);
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("Couldn't listen to port " + port);
        }

        while (rationalWaiter()) {
            try {
                Socket client = serverSocket.accept();
                clientSocketsPool.add(client);
                fileNumber++;
                Thread thread = new Thread(new FileReceiver());
                thread.start();
            } catch (IOException ex) {
                ex.printStackTrace();
                logger.error("Can't accept client");
            }
        }

    }

    @Override
    public void stop() throws IOException {
        try {
            logger.info("Trying stop server on " + port + " port");
            serverSocket.close();
            System.exit(0);
        } catch (IOException ex) {
            logger.error("Trying of stopping is unsuccessful");
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        DataInputStream dataInputStream = null;
        FileOutputStream fileOutputStream = null;
        Socket client = clientSocketsPool.get(fileNumber - 1);

        try {
            File file = new File("/home/parkito/Downloads/result.pdf" + fileNumber + ".pdf");
            dataInputStream = new DataInputStream(client.getInputStream());
            fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[4096];

            int currentData, totalRead = 0;
            while ((currentData = dataInputStream.read(buffer, 0, buffer.length)) > 0) {
                totalRead += currentData;
                fileOutputStream.write(buffer, 0, currentData);
            }

            logger.info("Received data " + totalRead);
            logger.info("File " + file.getAbsolutePath() + " successfully received");
        } catch (IOException ex) {
            logger.error("File transfer was corrupted");
            ex.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
                dataInputStream.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean rationalWaiter() {
        try {
            logger.info("wait");
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jobStatus;
    }

}
