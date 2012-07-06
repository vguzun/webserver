package net.guzun.webserver.processors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.activation.MimetypesFileTypeMap;

import net.guzun.webserver.http.HttpConstants;
import net.guzun.webserver.http.HttpHelper;
import net.guzun.webserver.http.HttpRequest;
import net.guzun.webserver.http.HttpResponse;

public class GetProcessor extends BaseProcessor {

	public GetProcessor(RequestProcessor nextProcessor) {
		super(nextProcessor);
	}

	@Override
	public void process(HttpRequest request, HttpResponse response) {
		if (request.getMethod().equals(HttpConstants.METHOD_GET)) {
			processGetRequest(request, response);
		} else {
			super.process(request, response);
		}
	}

	private void processGetRequest(HttpRequest request, HttpResponse response) {
		createHtml(request, response);

	}

	private void createHtml(HttpRequest request, HttpResponse response) {
		String absolutePath = request.getAbsolutPath();
		PrintStream printStream = new PrintStream(response.getOutputStream(), true);
		File file = new File(absolutePath);
		if (file.exists()) {
			if (file.isDirectory()) {
				StringBuilder html = new StringBuilder();
				String[] list = file.list();
				HttpHelper.createHeader(printStream, HttpConstants.HTTP_OK, "text/html");
				html.append("<html>\r\n");
				html.append("<head>\r\n");
				html.append("    <title>\r\n");
				html.append("Heya");
				html.append("</title>\r\n");
				html.append("</head>\r\n");
				html.append("<body>\r\n");
				html.append(absolutePath);
				html.append("<ul id=\"folders\">");
				if (!request.getUriPath().equals("/")) {
					html.append("<li><a href=\"../\">&lt;..&gt;</a></li>");
				}
				for (String fileItem : list) {
					File childFile = new File(file, fileItem);
					if (childFile.isDirectory()) {
						html.append(String.format("<li><a href=\"%1$s/\">&lt;%1$s&gt;</a></li>", fileItem));
					} else {
						html.append(String.format("<li><a href=\"%1$s\">%1$s</a></li>", fileItem));
					}
				}
				html.append("</ul>");
				html.append("<form action=\"#\" method=\"post\" enctype=\"multipart/form-data\">");
				html.append("<input type=\"file\" name=\"file\"/><br/>");
				html.append("<input type=\"submit\" name=\"submit\"/>"); 
				html.append("</form>");
				html.append("</body>\r\n");
				html.append("</html>\r\n");
				printStream.print(html.toString());
			} else {
				String contentType = new MimetypesFileTypeMap().getContentType(file);
				HttpHelper.createHeader(printStream, HttpConstants.HTTP_OK, contentType);
				byte[] buffer = new byte[4096]; 
				int len;
				try {
					FileInputStream fileInputStream = new FileInputStream(file);
					OutputStream outputStream = response.getOutputStream();

					while ((len = fileInputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, len);
					}

					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			HttpHelper.createHeader(printStream, HttpConstants.HTTP_NOT_FOUND, "text/html");
			StringBuilder html = new StringBuilder();
			html.append("<html>\r\n");
			html.append("<head>\r\n");
			html.append("    <title>\r\n");
			html.append("Heya");
			html.append("</title>\r\n");
			html.append("</head>\r\n");
			html.append("<body>\r\n");
			html.append("Resource not found");
			html.append("</body>\r\n");
			html.append("</html>\r\n");
			printStream.print(html.toString());
		}

	}
}
