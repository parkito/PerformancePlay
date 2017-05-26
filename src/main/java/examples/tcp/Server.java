package sockets.tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Artem Karnov @date 25.05.2017.
 *         artem.karnov@t-systems.com
 */

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    private final static int PORT = 4444;

    public static void main(String[] args) throws IOException {
        logger.info("Welcome to Server side");

        ServerSocket server = null;
        Socket client = null;

        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            logger.error("Couldn't listen to port 4444");
            System.exit(-1);
        }

        try {
            logger.info("Waiting for a client...");
            client = server.accept();
            logger.info("Client connected");
        } catch (IOException e) {
            logger.error("Can't accept");
            System.exit(-1);
        }

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter outputWritter = new PrintWriter(client.getOutputStream(), true);
        String input;

        logger.info("Wait for messages");

        while ((input = inputReader.readLine()) != null) {
            if (input.equalsIgnoreCase("exit"))
                break;
            outputWritter.println("Server ::: " + input);
            logger.info(input);
        }

        outputWritter.close();
        inputReader.close();
        client.close();
        server.close();
    }
}
