package net.guzun.webserver;

import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Main {
	private static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] argv) {
		if (argv.length < 2) {
			System.out.println("Please provide port and path to the root folder.");
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
		while (!sc.nextLine().equals(""));

		webServer.stop();
		System.out.println("Server stopped.");
	}
}
