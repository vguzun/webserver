package net.guzun.webserver.processors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentDisposition {
	private static final String DISPOSITION_FORM_EXPRESSION = "Content-Disposition: form-data; name=\".*\"; filename=\"(.*){1}\".*";
	private static final Pattern DISPOSITION_PATTERN = Pattern.compile(DISPOSITION_FORM_EXPRESSION);
	private String fileName = null;

	public ContentDisposition(String line) {
		Matcher matcher = DISPOSITION_PATTERN.matcher(line);
		if (matcher.matches()) {
			fileName = matcher.group(1);
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
