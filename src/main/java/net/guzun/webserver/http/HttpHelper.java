package net.guzun.webserver.http;

import java.io.PrintStream;

/**
 * The Class HttpHelper.
 */
public class HttpHelper {

    /**
     * Sends HTTP header.
     *
     * @param printStream the print stream
     * @param responseCode the response code
     * @param contentType the content type
     */
    public static void createHeader(PrintStream printStream, String responseCode, String contentType) {
        printStream.println("HTTP/1.1 " + responseCode);
        printStream.println("Content-Type: " + contentType);
        printStream.println();
    }

    /**
     * Write html head.
     *
     * @param printStream the print stream
     */
    public static void writeHtmHead(PrintStream printStream) {
        StringBuilder stringBuilder = new StringBuilder().append("<html>\r\n").append("\t<head>\r\n")
                .append("\t\t<title>").append("Java WebServer").append("</title>\r\n").append("\t</head>\r\n")
                .append("\t<body>\r\n");
        printStream.append(stringBuilder.toString());
    }

    /**
     * Write htm tail.
     *
     * @param printStream the print stream
     */
    public static void writeHtmTail(PrintStream printStream) {
        StringBuilder stringBuilder = new StringBuilder().append("\t</body\r\n").append("</html>\r\n");
        printStream.append(stringBuilder.toString());
    }

    /**
     * Instantiates a new http helper.
     */
    private HttpHelper() {

    }

}
