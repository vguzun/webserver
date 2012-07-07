package net.guzun.webserver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;
import net.guzun.webserver.processors.HeaderProcessor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * The unit test for HeaderProcessor
 */
public class HeaderProcessorTest {

    /** The Constant ROOT_PATH. */
    private static final String ROOT_PATH = "/";

    /** The Constant LINE_SEPARATOR. */
    private static final String LINE_SEPARATOR = "\r\n";

    /** The Constant ATTRIBUTES_SEPARATOR. */
    private static final String ATTRIBUTES_SEPARATOR = ": ";

    /** The Constant PROTOCOL. */
    private static final String PROTOCOL = "HTTP/1.1";

    /** The Constant PATH. */
    private static final String PATH = "/pathto.folder/new.html";

    /** The Constant METHOD_GET. */
    private static final String METHOD_GET = "GET";

    /** The Constant HEADER_KEY1. */
    private static final String HEADER_KEY1 = "Accept-Charset";

    /** The Constant HEADER_VALUE1. */
    private static final String HEADER_VALUE1 = "utf-8";

    /** The Constant HEADER_KEY2. */
    private static final String HEADER_KEY2 = "Accept-Encoding";

    /** The Constant HEADER_VALUE2. */
    private static final String HEADER_VALUE2 = "gzip, deflate";

    /** The header processor. */
    private HeaderProcessor headerProcessor;

    /** The response. */
    @Mock
    private HttpResponse response;

    /** The simple get input stream. */
    private InputStream simpleGetInputStream;

    /** The output stream. */
    @Mock
    private OutputStream outputStream;

    /**
     * Sets the up.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    @Before
    public void setUp() throws UnsupportedEncodingException {
        MockitoAnnotations.initMocks(this);
        headerProcessor = new HeaderProcessor(null);
        simpleGetInputStream = new ByteArrayInputStream(getSimpleRequestString().getBytes("UTF-8"));
    }

    /**
     * Gets the simple request string.
     * @return the simple request string
     */
    private String getSimpleRequestString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(METHOD_GET).append(" ").append(PATH).append(" ").append(PROTOCOL).append(LINE_SEPARATOR)
                .append(HEADER_KEY1).append(ATTRIBUTES_SEPARATOR).append(HEADER_VALUE1).append(LINE_SEPARATOR)
                .append(HEADER_KEY2).append(ATTRIBUTES_SEPARATOR).append(HEADER_VALUE2).append(LINE_SEPARATOR)
                .append(LINE_SEPARATOR);
        return stringBuilder.toString();
    }

    /**
     * Process http get request.
     * @throws RequestProcessingException
     *             the request processing exception
     */
    @Test
    public void processHttpGetRequest() throws RequestProcessingException {

        HttpRequest request = new HttpRequest(ROOT_PATH, simpleGetInputStream);
        when(response.getOutputStream()).thenReturn(outputStream);
        headerProcessor.process(request, response);
        assertEquals(request.getMethod(), METHOD_GET);
        assertEquals(request.getPath(), PATH);
        assertEquals(request.getProtocol(), PROTOCOL);
        assertEquals(HEADER_VALUE1, request.getAttribute(HEADER_KEY1));
        assertEquals(HEADER_VALUE2, request.getAttribute(HEADER_KEY2));
    }
}
