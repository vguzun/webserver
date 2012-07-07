package net.guzun.webserver.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.guzun.webserver.exceptions.BadInputStreamException;

import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.MultipartStream.MalformedStreamException;

public class HttpStreamReader {
	public static final int HEADER_PART_SIZE_MAX = 10240;
	public static final byte CR = 0x0D;
	public static final byte LF = 0x0A;
	protected static final byte[] HEADER_SEPARATOR = {CR, LF, CR, LF };

	private InputStream inputStream;
	private MultipartStream multipartStream;

	public HttpStreamReader(InputStream inputStream) {
		this.inputStream = inputStream;
	}

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

	public void readBody(ByteArrayOutputStream data) throws BadInputStreamException {
		try {
			multipartStream.readBodyData(data);
		} catch (Exception e) {
			throw new BadInputStreamException(e);
		}
	}

	@SuppressWarnings("deprecation")
    public void upgradeToMultipart(byte[] boundary) {
		if (boundary == null) {
			throw new IllegalArgumentException("Boundary can't be null");
		}
		if (multipartStream == null) {
			multipartStream = new MultipartStream(inputStream, boundary);
		}
	}

	public boolean readBoundary() throws BadInputStreamException {
		try {
	        return multipartStream.readBoundary();
        } catch (MalformedStreamException e) {
        	throw new BadInputStreamException(e);
        }
	}

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
