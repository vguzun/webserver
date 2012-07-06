package net.guzun.webserver.http;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequest {
	private InputStream inputStream;
    private String rootPath;
    private String method;
    private String path;
    private String protocol;
    private Map<String, String> attributes = new HashMap<String, String>();
    
    public HttpRequest(String rootPath, InputStream inputStream) {
    	this.rootPath = rootPath;
    	this.inputStream = inputStream;
    	
    }
    
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
    public void addAtribute(String name, String value) {
    	attributes.put(name, value);
    }
    
    public Set<String> getAttributesSet() {
    	return attributes.keySet();
    }
    
    public String getAttribute(String name) {
    	return attributes.get(name);
    }
    
    public String getUriPath() {
    	int questionMarkIndex = getPath().indexOf("?");
		if (questionMarkIndex == -1) {
			return getPath();
		} else {
			return getPath().substring(0, questionMarkIndex);
		}
    }

	public String getRootPath() {
	    return rootPath;
    }

	public void setRootPath(String rootPath) {
	    this.rootPath = rootPath;
    }
	
	public boolean isMultipart() {
		String contentType = getAttribute("Content-Type");
		if (!getMethod().equalsIgnoreCase(HttpConstants.METHOD_POST)) {
			return false;
		}
		
		if (contentType == null || !contentType.contains("multipart/form-data")) {
			return false;
		}
		
		return true;
		
	}
	
	public byte[] getBoundary() {
		String contentType = getAttribute("Content-Type");
	    int boundaryIndex = contentType.indexOf("boundary=");
	    byte[] boundary = (contentType.substring(boundaryIndex + 9)).getBytes();
	    return boundary;
    }

	public String getAbsolutPath() {
	    return getRootPath() + getUriPath();
    }

	public InputStream getInputStream() {
	    return inputStream;
    }

}
