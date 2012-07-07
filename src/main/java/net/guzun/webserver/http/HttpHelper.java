package net.guzun.webserver.http;

import java.io.PrintStream;

public class HttpHelper {

	public static void createHeader(PrintStream printStream, String responseCode, String contentType) {
		printStream.println("HTTP/1.1 " + responseCode);
		printStream.println("Content-Type: " + contentType);
		printStream.println();
    }
	
	public static void WriteHtmHead(PrintStream printStream) {
	    StringBuilder stringBuilder = new StringBuilder()
	    .append("<html>\r\n")
	    .append("\t<head>\r\n")
	    .append("\t\t<title>")
	    .append("Java WebServer")
	    .append("</title>\r\n")
	    .append("\t</head>\r\n")
	    .append("\t<body>\r\n");
	    printStream.append(stringBuilder.toString());
	}
	
	public static void WriteHtmTail(PrintStream printStream) {
		StringBuilder stringBuilder = new StringBuilder()
	    .append("\t</body\r\n")
	    .append("</html>\r\n");
	    printStream.append(stringBuilder.toString());
	}

}
