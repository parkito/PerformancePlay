package sockets.tcp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Artem Karnov @date 25.05.2017.
 *         artem.karnov@t-systems.com
 */

public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);

    private final static int PORT = 4444;
    private final static String ADDRESS = "localhost";

    public static void main(String[] args) throws IOException {
        logger.info("Welcome to Client side");
        logger.info("Connecting to " + ADDRESS + ":" + PORT);

        Socket socket = new Socket(ADDRESS, PORT);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader lineReader = new BufferedReader(new InputStreamReader(System.in));
        String input, output;

        while ((input = lineReader.readLine()) != null) {
            outputWriter.println(input);
            output = inputReader.readLine();
            logger.info(output);
            if (input.equalsIgnoreCase("close"))
                break;
            if (input.equalsIgnoreCase("exit"))
                break;
        }

        outputWriter.close();
        inputReader.close();
        lineReader.close();
        socket.close();
    }
}
