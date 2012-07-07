package net.guzun.webserver.io;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface ContentProvider {

    String getName();

    String getPath();

    boolean exists();

    boolean isDirectory();

    String[] list();

    String getContentType();

    InputStream getContentStream() throws FileNotFoundException;

}
