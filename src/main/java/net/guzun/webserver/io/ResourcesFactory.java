package net.guzun.webserver.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A factory for creating ContentProvider objects.
 */
public class ResourcesFactory {

    /**
     * Gets the content provider.
     * @param path the path
     * @return the content provider
     */
    public ContentProvider getContentProvider(String path) {
        return new FileSystemContentProvider(path);
    }

    /**
     * creates an output stream for provided path
     * @param filePath path to file
     * @return output stream
     * @throws IOException IO exception
     */
    public OutputStream createOutputStream(String filePath) throws IOException {
        return new FileOutputStream(filePath);
    }
}
