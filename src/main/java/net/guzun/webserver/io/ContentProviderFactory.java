package net.guzun.webserver.io;

/**
 * A factory for creating ContentProvider objects.
 */
public class ContentProviderFactory {

    /**
     * Gets the content provider.
     * @param path the path
     * @return the content provider
     */
    public ContentProvider getContentProvider(String path) {
        return new FileSystemContentProvider(path);
    }
}
