package net.guzun.webserver.processors;

/**
 * An unit tests request helper
 */
public class RequestsTestHelper {
    public static final String ROOT_PATH = "/";
    public static final String LINE_SEPARATOR = "\r\n";
    public static final String ATTRIBUTES_SEPARATOR = ": ";
    public static final String PROTOCOL = "HTTP/1.1";
    public static final String PATH = "/pathto.folder/new.html";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_UNKNOWN = "unknown";
    public static final String HEADER_KEY1 = "Accept-Charset";
    public static final String HEADER_VALUE1 = "utf-8";
    public static final String HEADER_KEY2 = "Accept-Encoding";
    public static final String HEADER_VALUE2 = "gzip, deflate";

    /**
     * default constructor
     */
    private RequestsTestHelper() {

    }
    /**
     * Gets the simple request string.
     * @param method request method to return
     * @return the simple request string
     */
    public static String getSimpleRequestString(String method) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(method).append(" ").append(PATH).append(" ").append(PROTOCOL).append(LINE_SEPARATOR)
                .append(HEADER_KEY1).append(ATTRIBUTES_SEPARATOR).append(HEADER_VALUE1).append(LINE_SEPARATOR)
                .append(HEADER_KEY2).append(ATTRIBUTES_SEPARATOR).append(HEADER_VALUE2).append(LINE_SEPARATOR)
                .append(LINE_SEPARATOR);
        return stringBuilder.toString();
    }
}
