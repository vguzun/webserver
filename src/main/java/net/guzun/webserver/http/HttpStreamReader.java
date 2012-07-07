package net.guzun.webserver.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.guzun.webserver.exceptions.BadInputStreamException;

import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.MultipartStream.MalformedStreamException;

/**
 * The Class HttpStreamReader.
 */
public class HttpStreamReader {

    /** The Constant HEADER_PART_SIZE_MAX. */
    public static final int HEADER_PART_SIZE_MAX = 10240;

    /** The Constant CR. */
    public static final byte CR = 0x0D;

    /** The Constant LF. */
    public static final byte LF = 0x0A;

    /** The Constant HEADER_SEPARATOR. */
    protected static final byte[] HEADER_SEPARATOR = {CR, LF, CR, LF };

    /** The input stream. */
    private InputStream inputStream;

    /** The multipart stream. */
    private MultipartStream multipartStream;

    /**
     * Instantiates a new http stream reader.
     * @param inputStream the input stream
     */
    public HttpStreamReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Read headers.
     * @return the string
     * @throws BadInputStreamException the bad input stream exception
     */
    public String readHeaders() throws BadInputStreamException {
        try {
            if (multipartStream == null) {
                return readStreamHeaders();
            } else {
                return multipartStream.readHeaders();
            }
        } catch (MalformedStreamException e) {
            throw new BadInputStreamException(e);
        }
    }

    /**
     * Read body.
     * @param data the data
     * @throws BadInputStreamException the bad input stream exception
     */
    public void readBody(ByteArrayOutputStream data) throws BadInputStreamException {
        try {
            multipartStream.readBodyData(data);
        } catch (Exception e) {
            throw new BadInputStreamException(e);
        }
    }

    /**
     * Upgrade to multipart.
     * @param boundary the boundary
     */
    @SuppressWarnings("deprecation")
    public void upgradeToMultipart(byte[] boundary) {
        if (boundary == null) {
            throw new IllegalArgumentException("Boundary can't be null");
        }
        if (multipartStream == null) {
            multipartStream = new MultipartStream(inputStream, boundary);
        }
    }

    /**
     * Read boundary.
     * @return true, if successful
     * @throws BadInputStreamException the bad input stream exception
     */
    public boolean readBoundary() throws BadInputStreamException {
        try {
            return multipartStream.readBoundary();
        } catch (MalformedStreamException e) {
            throw new BadInputStreamException(e);
        }
    }

    /**
     * Read stream headers.
     * @return the string
     * @throws BadInputStreamException the bad input stream exception
     */
    private String readStreamHeaders() throws BadInputStreamException {
        int i = 0;
        byte b;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int size = 0;
        while (i < HEADER_SEPARATOR.length) {
            try {
                b = (byte) inputStream.read();
            } catch (IOException e) {
                throw new BadInputStreamException("Stream ended unexpectedly");
            }
            if (++size > HEADER_PART_SIZE_MAX) {
                throw new BadInputStreamException("Header section has more than " + HEADER_PART_SIZE_MAX
                        + " bytes (maybe it is not properly terminated)");
            }
            if (b == HEADER_SEPARATOR[i]) {
                i++;
            } else {
                i = 0;
            }
            baos.write(b);
        }

        return baos.toString();
    }

}
