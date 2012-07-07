package net.guzun.webserver;

import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * Entry point
 */
public class Main {
    private static Logger logger = Logger.getLogger(Main.class);

    /**
     * default constructor
     */
    private Main() {

    }

    /**
     * Start application
     * @param argv arguments requires port and root folder
     */
    public static void main(String[] argv) {
        if (argv.length < 2) {
            System.out.println("Please provide port and path to the root folder.");
            System.out.println("Eg: \"java -jar ./webserver-0.01-SNAPSHOT.jar 8000 /home/user\"");
            return;
        }
        WebServer webServer;
        try {
            webServer = new WebServer(argv[0], argv[1]);
            Thread webServerThread = new Thread(webServer);
            webServerThread.start();
        } catch (IOException e) {
            logger.error("Server can't be started", e);
            return;
        }

        System.out.println("Press enter in order to shutdown the server");
        Scanner sc = new Scanner(System.in);
        boolean equals = false;
        do {
             equals = sc.nextLine().equals("");
        } while (!equals);

        webServer.stop();
        System.out.println("Server stopped.");
    }
}
