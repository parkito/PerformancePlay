package sockets.transfer;

import sockets.transfer.implementation.FileReceiver;
import sockets.transfer.implementation.FileSender;
import sockets.transfer.implementation.ServiceStarter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Artem Karnov @date 25.05.2017.
 *         artem.karnov@t-systems.com
 */

public class Main {
    // TODO: 26.05.2017 exit from app - finish it
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        FileReceiver fileReceiver = new FileReceiver();
        exec.execute(new ServiceStarter(fileReceiver, 4444));
        for (int i = 0; i < 10; i++) {
            exec.execute(new ServiceStarter(new FileSender(),
                    "/home/parkito/Downloads/source.pdf", "localhost", 4444));
        }
        exec.shutdown();
    }
}
