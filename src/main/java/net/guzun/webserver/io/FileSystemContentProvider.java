package net.guzun.webserver.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.activation.MimetypesFileTypeMap;

/**
 * The Class FileSystemContentProvider.
 */
public class FileSystemContentProvider implements ContentProvider {

    /** The Constant MIME_TYPES_MAP. */
    private static final MimetypesFileTypeMap MIME_TYPES_MAP = new MimetypesFileTypeMap();

    /** The file. */
    private File file;

    /**
     * Instantiates a new file system content provider.
     * @param path the path
     */
    public FileSystemContentProvider(String path) {
        file = new File(path);
    }

    /*
     * (non-Javadoc)
     * @see net.guzun.webserver.io.ContentProvider#exists()
     */
    @Override
    public boolean exists() {
        return file.exists();
    }

    /*
     * (non-Javadoc)
     * @see net.guzun.webserver.io.ContentProvider#list()
     */
    @Override
    public String[] list() {
        return file.list();
    }

    /*
     * (non-Javadoc)
     * @see net.guzun.webserver.io.ContentProvider#isDirectory()
     */
    @Override
    public boolean isDirectory() {
        return file.isDirectory();
    }

    /*
     * (non-Javadoc)
     * @see net.guzun.webserver.io.ContentProvider#getContentType()
     */
    @Override
    public String getContentType() {
        return MIME_TYPES_MAP.getContentType(file);
    }

    /*
     * (non-Javadoc)
     * @see net.guzun.webserver.io.ContentProvider#getContentStream()
     */
    @Override
    public InputStream getContentStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    /*
     * (non-Javadoc)
     * @see net.guzun.webserver.io.ContentProvider#getPath()
     */
    @Override
    public String getPath() {
        return file.getAbsolutePath();
    }

    /*
     * (non-Javadoc)
     * @see net.guzun.webserver.io.ContentProvider#getName()
     */
    @Override
    public String getName() {
        return file.getName();
    }

}
