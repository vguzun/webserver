package net.guzun.webserver.processors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import net.guzun.webserver.exceptions.RequestProcessingException;
import net.guzun.webserver.http.HttpConstants;
import net.guzun.webserver.http.HttpHelper;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;
import net.guzun.webserver.io.ContentProvider;
import net.guzun.webserver.io.ContentProviderFactory;

public class GetProcessor extends BaseProcessor {
	private static final int BUFFER_SIZE = 4096;
	private final ContentProviderFactory contentProviderFactory;
	public GetProcessor(RequestProcessor nextProcessor, ContentProviderFactory contentProviderFactoryParam) {
		super(nextProcessor);
		this.contentProviderFactory = contentProviderFactoryParam;
	}

	@Override
	public void process(HttpRequest request, HttpResponse response) throws RequestProcessingException {
		if (request.getMethod().equals(HttpConstants.METHOD_GET)) {
			writeResponse(request, response);
		} else {
			super.process(request, response);
		}
	}

	private void writeResponse(HttpRequest request, HttpResponse response) throws RequestProcessingException {
		String absolutePath = request.getAbsolutPath();
		OutputStream outputStream = response.getOutputStream();
		
		ContentProvider contentProvider = contentProviderFactory.getContentProvider(absolutePath);
		if (contentProvider.exists()) {
			if (contentProvider.isDirectory()) {
				boolean parentPathEnabled = request.getUriPath().equals("/");
				displayFolderContent(outputStream, contentProvider, parentPathEnabled);
			} else {
				displayFileContent(outputStream, contentProvider);
			}
		} else {
			displayNotFound(outputStream);
		}
	}

	private void displayNotFound(OutputStream outputStream) {
	    PrintStream printStream = new PrintStream(outputStream, true);
	    HttpHelper.createHeader(printStream, HttpConstants.HTTP_NOT_FOUND, "text/html");
	    HttpHelper.WriteHtmHead(printStream);
	    printStream.print("Resource not found");
	    HttpHelper.WriteHtmTail(printStream);
    }

	private void displayFileContent(OutputStream outputStream, ContentProvider contentProvider) throws RequestProcessingException {
	    PrintStream printStream = new PrintStream(outputStream, true);
	    String contentType = contentProvider.getContentType();
	    HttpHelper.createHeader(printStream, HttpConstants.HTTP_OK, contentType);
	    byte[] buffer = new byte[BUFFER_SIZE]; 
	    int len;
	    try {
	    	InputStream fileInputStream = contentProvider.getContentStream();

	    	while ((len = fileInputStream.read(buffer)) != -1) {
	    		outputStream.write(buffer, 0, len);
	    	}

	    	fileInputStream.close();
	    } catch (IOException e) {
	    	throw new RequestProcessingException(e);
	    }
    }

	private void displayFolderContent(OutputStream outputStream, ContentProvider contentProvider, boolean parentPathEnabled) {
		PrintStream printStream = new PrintStream(outputStream, true);
	    
	    HttpHelper.createHeader(printStream, HttpConstants.HTTP_OK, "text/html");
	    HttpHelper.WriteHtmHead(printStream);

	    StringBuilder html = new StringBuilder();
	    html.append("<ul id=\"folders\">");
	    
	    String[] list = contentProvider.list();
	    if (!parentPathEnabled) {
	    	html.append("<li><a href=\"../\">&lt;..&gt;</a></li>");
	    }
	    for (String fileItem : list) {
	    	ContentProvider childContent = contentProviderFactory.getContentProvider(contentProvider.getPath() + "/" + fileItem);
	    	if (childContent.isDirectory()) {
	    		html.append(String.format("<li><a href=\"%1$s/\">&lt;%1$s&gt;</a></li>", childContent.getName()));
	    	} else {
	    		html.append(String.format("<li><a href=\"%1$s\">%1$s</a></li>", childContent.getName()));
	    	}
	    }
	    html.append("</ul>");
	    html.append("<form action=\"#\" method=\"post\" enctype=\"multipart/form-data\">");
	    html.append("<input type=\"file\" name=\"file\"/><br/>");
	    html.append("<input type=\"submit\" name=\"submit\"/>"); 
	    html.append("</form>");
	    HttpHelper.WriteHtmTail(printStream);
	    printStream.print(html.toString());
    }
}
