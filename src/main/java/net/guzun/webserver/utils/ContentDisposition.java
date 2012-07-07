package net.guzun.webserver.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class ContentDisposition.
 */
public class ContentDisposition {

    /** The Constant DISPOSITION_FORM_EXPRESSION. */
    private static final String DISPOSITION_FORM_EXPRESSION =
            "Content-Disposition: form-data; name=\".*\"; filename=\"(.*){1}\".*";

    /** The Constant DISPOSITION_PATTERN. */
    private static final Pattern DISPOSITION_PATTERN = Pattern.compile(DISPOSITION_FORM_EXPRESSION);

    /** The file name. */
    private String fileName = null;

    /**
     * Instantiates a new content disposition.
     * @param line the line
     */
    public ContentDisposition(String line) {
        Matcher matcher = DISPOSITION_PATTERN.matcher(line);
        if (matcher.matches()) {
            fileName = matcher.group(1);
        }
    }

    /**
     * Gets the file name.
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     * @param fileName the new file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
