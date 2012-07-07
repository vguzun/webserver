package net.guzun.webserver.io;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * The Interface ContentProvider.
 */
public interface ContentProvider {

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the path.
     *
     * @return the path
     */
    String getPath();

    /**
     * Exists.
     *
     * @return true, if successful
     */
    boolean exists();

    /**
     * Checks if is directory.
     *
     * @return true, if is directory
     */
    boolean isDirectory();

    /**
     * List.
     *
     * @return the string[]
     */
    String[] list();

    /**
     * Gets the content type.
     *
     * @return the content type
     */
    String getContentType();

    /**
     * Gets the content stream.
     *
     * @return the content stream
     * @throws FileNotFoundException the file not found exception
     */
    InputStream getContentStream() throws FileNotFoundException;

}
