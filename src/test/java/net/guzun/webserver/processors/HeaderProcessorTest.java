package net.guzun.webserver.processors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * The unit test for HeaderProcessor
 */
public class HeaderProcessorTest {

    private HeaderProcessor headerProcessor;

    @Mock private HttpResponse response;
    private InputStream simpleGetInputStream;
    private HttpRequest request;
    @Mock private OutputStream outputStream;

    /**
     * Set up tests.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    @Before
    public void setUp() throws UnsupportedEncodingException {
        MockitoAnnotations.initMocks(this);
        headerProcessor = new HeaderProcessor();
        simpleGetInputStream = new ByteArrayInputStream(
                RequestsTestHelper.getSimpleRequestString(RequestsTestHelper.METHOD_GET).getBytes("UTF-8"));
        request = new HttpRequest(RequestsTestHelper.ROOT_PATH, simpleGetInputStream);
    }

    /**
     * Process http get request.
     * @throws RequestProcessingException the request processing exception
     */
    @Test
    public void processHttpGetRequest() throws RequestProcessingException {
        when(response.getOutputStream()).thenReturn(outputStream);
        headerProcessor.process(request, response);
        assertEquals(RequestsTestHelper.METHOD_GET, request.getMethod());
        assertEquals(RequestsTestHelper.PATH, request.getPath());
        assertEquals(RequestsTestHelper.PROTOCOL, request.getProtocol());
        assertEquals(RequestsTestHelper.HEADER_VALUE1, request.getAttribute(RequestsTestHelper.HEADER_KEY1));
        assertEquals(RequestsTestHelper.HEADER_VALUE2, request.getAttribute(RequestsTestHelper.HEADER_KEY2));
    }
}
