package net.guzun.webserver.processors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.fileupload.MultipartStream.MalformedStreamException;

import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;

public class HeaderProcessor extends BaseProcessor {
	public static final int HEADER_PART_SIZE_MAX = 10240;
	public static final byte CR = 0x0D;
	public static final byte LF = 0x0A;
	protected static final byte[] HEADER_SEPARATOR = { CR, LF, CR, LF };
	private final byte[] buffer;

	private int head;
	private int tail;

	/**
	 * The content encoding to use when reading headers.
	 */
	private String headerEncoding;
	private int bufSize = 1024;

	public HeaderProcessor(RequestProcessor nextProcessor) {
		super(nextProcessor);
		buffer = new byte[bufSize];
	}

	@Override
	public void process(HttpRequest request, HttpResponse response) {
		try {
			String headersString = readHeaders(request.getInputStream());
			String[] headers = headersString.split("\n");
			processtHeadTitle(request, headers[0]);
			for (int i = 1; i < headers.length - 1; i++) { // ignoring last line
														   // as it it empty
														   // after the split
				processHeaderItem(request, headers[i]);
			}
		} catch (MalformedStreamException e) {
			e.printStackTrace();
		}

		super.process(request, response);
	}

	private void processtHeadTitle(HttpRequest request, String line) {
		String[] headerParams = line.split(" ");
		request.setMethod(headerParams[0]);
		request.setPath(headerParams[1]);
		request.setProtocol(headerParams[2]);
	}

	private void processHeaderItem(HttpRequest request, String line) {
		int separatorIndex = line.indexOf(":");
		String name = line.substring(0, separatorIndex);
		String value = line.substring(separatorIndex + 2);
		request.addAtribute(name, value);
	}

	private String readHeaders(InputStream inputStream) throws MalformedStreamException {
		int i = 0;
		byte b;
		// to support multi-byte characters
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int size = 0;
		while (i < HEADER_SEPARATOR.length) {
			try {
				b = readByte(inputStream);
			} catch (IOException e) {
				throw new MalformedStreamException("Stream ended unexpectedly");
			}
			if (++size > HEADER_PART_SIZE_MAX) {
				throw new MalformedStreamException("Header section has more than " + HEADER_PART_SIZE_MAX
				                + " bytes (maybe it is not properly terminated)");
			}
			if (b == HEADER_SEPARATOR[i]) {
				i++;
			} else {
				i = 0;
			}
			baos.write(b);
		}

		String headers = null;
		if (headerEncoding != null) {
			try {
				headers = baos.toString(headerEncoding);
			} catch (UnsupportedEncodingException e) {
				// Fall back to platform default if specified encoding is not
				// supported.
				headers = baos.toString();
			}
		} else {
			headers = baos.toString();
		}

		return headers;
	}

	private byte readByte(InputStream inputStream) throws IOException {
		// Buffer depleted ?
		if (head == tail) {
			head = 0;
			// Refill.
			tail = inputStream.read(buffer, head, bufSize);
			if (tail == -1) {
				// No more data available.
				throw new IOException("No more data is available");
			}

		}
		return buffer[head++];
	}
}
