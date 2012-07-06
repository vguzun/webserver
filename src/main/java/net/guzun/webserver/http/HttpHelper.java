package net.guzun.webserver.http;

import java.io.PrintStream;

public class HttpHelper {

	public static void createHeader(PrintStream printStream, String responseCode, String contentType) {
		printStream.println("HTTP/1.1 " + responseCode);
		printStream.println("Content-Type: " + contentType);
		printStream.println();
    }

}
