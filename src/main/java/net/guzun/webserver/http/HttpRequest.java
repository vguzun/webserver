package net.guzun.webserver.http;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The Class HttpRequest.
 */
public class HttpRequest {

    /** The Constant BOUNDARY_ATTRIBUTE. */
    private static final String BOUNDARY_ATTRIBUTE = "boundary=";

    /** The input stream. */
    private HttpStreamReader inputStream;

    /** The root path. */
    private String rootPath;

    /** The method. */
    private String method;

    /** The path. */
    private String path;

    /** The protocol. */
    private String protocol;

    /** The attributes. */
    private Map<String, String> attributes = new HashMap<String, String>();

    /**
     * Instantiates a new http request.
     * @param rootPath the root path
     * @param inputStream the input stream
     */
    public HttpRequest(String rootPath, InputStream inputStream) {
        this.rootPath = rootPath;
        this.inputStream = new HttpStreamReader(inputStream);
    }

    /**
     * Gets the method.
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the method.
     * @param method the new method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets the path.
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path.
     * @param path
     *            the new path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets the protocol.
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Sets the protocol.
     * @param protocol the HTTP protocol
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Adds the atribute.
     * @param name the name
     * @param value
     *            the value
     */
    public void addAtribute(String name, String value) {
        attributes.put(name, value);
    }

    /**
     * Gets the attributes set.
     * @return the attributes set
     */
    public Set<String> getAttributesSet() {
        return attributes.keySet();
    }

    /**
     * Gets the attribute.
     * @param name the name
     * @return the attribute
     */
    public String getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Gets the uri path.
     * @return the uri path
     */
    public String getUriPath() {
        int questionMarkIndex = getPath().indexOf("?");
        if (questionMarkIndex == -1) {
            return getPath();
        } else {
            return getPath().substring(0, questionMarkIndex);
        }
    }

    /**
     * Gets the root path.
     * @return the root path
     */
    public String getRootPath() {
        return rootPath;
    }

    /**
     * Sets the root path.
     * @param rootPath the new root path
     */
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * Checks if is multipart.
     * @return true, if is multipart
     */
    public boolean isMultipart() {
        String contentType = getAttribute("Content-Type");
        if (!getMethod().equalsIgnoreCase(HttpConstants.METHOD_POST)) {
            return false;
        }

        if (contentType == null || !contentType.contains("multipart/form-data")) {
            return false;
        }

        return true;
    }

    /**
     * Gets the absolut path.
     * @return the absolut path
     */
    public String getAbsolutPath() {
        return getRootPath() + getUriPath();
    }

    /**
     * Gets the input stream.
     * @return the input stream
     */
    public HttpStreamReader getInputStream() {
        return inputStream;
    }

    /**
     * Gets the boundary.
     * @return the boundary
     */
    public byte[] getBoundary() {
        String contentType = getAttribute("Content-Type");
        int boundaryIndex = contentType.indexOf(BOUNDARY_ATTRIBUTE);
        byte[] boundary = (contentType.substring(boundaryIndex + BOUNDARY_ATTRIBUTE.length())).getBytes();
        return boundary;
    }
}
