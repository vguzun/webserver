package net.guzun.webserver.io;

public class ContentProviderFactory {
    public ContentProvider getContentProvider(String path) {
        return new FileSystemContentProvider(path);
    }
}
