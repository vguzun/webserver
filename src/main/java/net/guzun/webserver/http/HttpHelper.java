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
    public static void createResponseHeader(PrintStream printStream, String responseCode, String contentType) {
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

    /**
     * Sends a redirect http code
     * @param request request
     * @param printStream stream to output
     */
    public static void createResponseRedirect(HttpRequest request, PrintStream printStream) {
        printStream.println("HTTP/1.1 302 Moved Temporarily");
        printStream.print("Location: ");

        String host = request.getAttribute(HttpConstants.HOST_ATTRIBUTE);
        if (host != null) {
            printStream.print("http://" + host + "/");
        }
        printStream.println(request.getPath());
        printStream.println();
    }
}
