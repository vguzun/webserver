package net.guzun.webserver.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.activation.MimetypesFileTypeMap;

public class FileSystemContentProvider implements ContentProvider {
    private static final MimetypesFileTypeMap MIME_TYPES_MAP = new MimetypesFileTypeMap();

    private File file;

    public FileSystemContentProvider(String path) {
        file = new File(path);
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public String[] list() {
        return file.list();
    }

    @Override
    public boolean isDirectory() {
        return file.isDirectory();
    }

    @Override
    public String getContentType() {
        return MIME_TYPES_MAP.getContentType(file);
    }

    @Override
    public InputStream getContentStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public String getPath() {
        return file.getAbsolutePath();
    }

    @Override
    public String getName() {
        return file.getName();
    }

}
