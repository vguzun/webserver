package net.guzun.webserver.processors;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpHelper;
import net.guzun.webserver.http.HttpResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * The unit test for DefaultProcessorTest
 */
public class DefaultProcessorTest {

    private DefaultProcessor defaultProcessor;
    @Mock private HttpResponse response;
    @Mock private HttpHelper httpHelper;
    @Mock private OutputStream outputStream;

    /**
     * Set up tests.
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    @Before
    public void setUp() throws UnsupportedEncodingException {
        MockitoAnnotations.initMocks(this);

        defaultProcessor = new DefaultProcessor(httpHelper);
    }

    /**
     * Process http get request.
     * @throws RequestProcessingException the request processing exception
     * @throws IOException IO exception
     */
    @Test
    public void processHttpGetRequest() throws RequestProcessingException, IOException {
        when(response.getOutputStream()).thenReturn(outputStream);

        defaultProcessor.process(null, response);
    }

}
