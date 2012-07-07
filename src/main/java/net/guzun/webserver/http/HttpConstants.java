package net.guzun.webserver.http;

/**
 * Defines and holds HTTP related constants
 */
public class HttpConstants {
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    public static final String HTTP_OK = "200";
    public static final String HTTP_MOVED = "302";
    public static final String HTTP_NOT_FOUND = "404";
    public static final String HTTP_METHOD_NOT_ALLOWED = "405";

    public static final String HOST_ATTRIBUTE = "Host";

    /**
     * Default constructor
     */
    private HttpConstants() {

    }
}
