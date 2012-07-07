package net.guzun.webserver.http;

import java.io.PrintStream;

/**
 * The Class HttpHelper.
 */
public class HttpHelper {

    /**
     * Write html head.
     *
     * @param printStream the print stream
     */
    public void writeHtmHead(PrintStream printStream) {
        StringBuilder stringBuilder = new StringBuilder()
            .append("<html>\r\n")
            .append("\t<head>\r\n")
            .append("\t\t<title>")
            .append("Simple Java WebServer")
            .append("</title>\r\n")
            .append("\t</head>\r\n")
            .append("\t<body>\r\n");
        printStream.append(stringBuilder.toString());
    }

    /**
     * Write htm tail.
     *
     * @param printStream the print stream
     */
    public void writeHtmTail(PrintStream printStream) {
        StringBuilder stringBuilder = new StringBuilder().append("\t</body\r\n").append("</html>\r\n");
        printStream.append(stringBuilder.toString());
    }
    /**
     * Sends HTTP header.
     *
     * @param printStream the print stream
     * @param responseCode the response code
     * @param contentType the content type
     */
    public void createResponseHeader(PrintStream printStream, String responseCode, String contentType) {
        printStream.println("HTTP/1.1 " + responseCode);
        printStream.println("Content-Type: " + contentType);
        printStream.println();
    }

    /**
     * Sends a redirect http code
     * @param request request
     * @param printStream stream to output
     */
    public void createResponseRedirect(HttpRequest request, PrintStream printStream) {
        printStream.println("HTTP/1.1 302 Moved Temporarily");
        printStream.print("Location: ");

        String host = request.getAttribute(HttpConstants.HOST_ATTRIBUTE);
        if (host != null) {
            printStream.print("http://" + host + "/");
        }
        printStream.println(request.getPath());
        printStream.println();
    }

    /**
     * Send a internal server error
     * @param printStream stream to output
     */
    public void createResponseInternallError(PrintStream printStream) {
        createResponseHeader(printStream, HttpConstants.HTTP_INTERNAL_ERROR, "text/html");
        writeHtmHead(printStream);
        printStream.println("<h1>500 Server Internall Error occured</h1>");
        writeHtmTail(printStream);
    }
}
