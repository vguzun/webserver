package net.guzun.webserver.processors;

import static org.mockito.Mockito.when;

import java.io.OutputStream;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpHelper;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;
import net.guzun.webserver.io.ContentProvider;
import net.guzun.webserver.io.ResourcesFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * The unit test for GetProcessorTest
 */
public class GetProcessorTest {
    private static final String FILE = "file";
    private static final String FOLDER = "folder";
    private static final String PATH = "/path";
    private static final String PATH_TO_RESOURCE = "/path/to/resource";
    private GetProcessor getProcessor;
    @Mock
    private HttpHelper httpHelper;
    @Mock
    private ResourcesFactory resourcesFactory;
    @Mock
    private ContentProvider contentProviderRoot;
    @Mock
    private ContentProvider contentProviderFolder;
    @Mock
    private ContentProvider contentProviderFile;
    @Mock
    private HttpRequest request;
    @Mock
    private HttpResponse response;
    @Mock
    private OutputStream outputStream;
    private String[] folderList = new String[] {FOLDER, FILE };

    /**
     * Set up tests.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        getProcessor = new GetProcessor(httpHelper, resourcesFactory);
    }

    /**
     * Test a get folder request.
     * @throws RequestProcessingException the request processing exception
     */
    @Test
    public void processHttpGetFolderRequest() throws RequestProcessingException {
        when(request.getMethod()).thenReturn(RequestsTestHelper.METHOD_GET);
        when(request.getUriPath()).thenReturn(PATH);
        when(request.getUriPath()).thenReturn(PATH_TO_RESOURCE);
        when(request.getAbsolutPath()).thenReturn(PATH_TO_RESOURCE + PATH_TO_RESOURCE);
        when(resourcesFactory.getContentProvider(PATH_TO_RESOURCE + PATH_TO_RESOURCE))
            .thenReturn(contentProviderRoot);

        when(contentProviderRoot.exists()).thenReturn(true);
        when(contentProviderRoot.isDirectory()).thenReturn(true);
        when(contentProviderRoot.list()).thenReturn(folderList);
        when(contentProviderRoot.getPath()).thenReturn(PATH);

        when(response.getOutputStream()).thenReturn(outputStream);

        when(resourcesFactory.getContentProvider(PATH + "/" + FOLDER)).thenReturn(contentProviderFolder);
        when(contentProviderFolder.isDirectory()).thenReturn(true);

        when(resourcesFactory.getContentProvider(PATH + "/" + FILE)).thenReturn(contentProviderFile);
        when(contentProviderFile.isDirectory()).thenReturn(false);
        getProcessor.process(request, response);
    }

    /**
     * Test a get empty folder request.
     * @throws RequestProcessingException the request processing exception
     */
    @Test
    public void processHttpGetEmptyFolderRequest() throws RequestProcessingException {
        when(request.getMethod()).thenReturn(RequestsTestHelper.METHOD_GET);
        when(request.getUriPath()).thenReturn(PATH);
        when(request.getUriPath()).thenReturn(PATH_TO_RESOURCE);
        when(request.getAbsolutPath()).thenReturn(PATH_TO_RESOURCE + PATH_TO_RESOURCE);
        when(resourcesFactory.getContentProvider(PATH_TO_RESOURCE + PATH_TO_RESOURCE)).thenReturn(
                contentProviderRoot);

        when(contentProviderRoot.exists()).thenReturn(true);
        when(contentProviderRoot.isDirectory()).thenReturn(true);
        when(contentProviderRoot.list()).thenReturn(new String[] {});

        when(response.getOutputStream()).thenReturn(outputStream);

        getProcessor.process(request, response);
    }
}
