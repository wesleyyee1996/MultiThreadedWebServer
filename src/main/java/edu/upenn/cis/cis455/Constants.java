package edu.upenn.cis.cis455;

import java.util.Hashtable;

public class Constants {

	public static final String get = "GET";
	public static final String put = "PUT";
	public static final String head = "HEAD";
	
	public static final String Method = "Method";
	public static final String Uri = "uri";
	public static final String HttpVersion = "protocolVersion";
	
	public static final String userAgent = "User-Agent";
	public static final String contentType = "Content-Type";
	public static final String contentLength = "Content-Length";
	public static final String ip = "http-client-ip";
	public static final String host = "Host";
	//public static final String Url = ""
	
	public static final String error_FileNotFound = "<html><head><title>404 Server Error</title></head><body><h1>File not found</h1><p></p></body></html>\"";
	public static final String error_ServerError = "<html><head><title>500 Server Error</title></head><body><h1>Server error</h1><p></p></body></html>\"";
	
	public static final String httpVersion = "HTTP/1.1";
	
	public static final String CRFL = "\r\n";
	
	public static final int threadPoolSize = 10;
	public static final int taskQueueSize = 100;
	
	public static final String sessionId = "JSESSIONID";
	
	
	public static final Hashtable<Integer, String> statusCodeReasons;
	
	static {
		statusCodeReasons = new Hashtable<Integer, String>();
		statusCodeReasons.put(200,"OK");
		statusCodeReasons.put(400,"Bad request");
		statusCodeReasons.put(403,"Forbidden");
		statusCodeReasons.put(404,"File note found");
		statusCodeReasons.put(500,"Server error");
	}
	
	public static final int controlPanelRoute = 1;
	public static final int normalRoute = 0;
	public static final int normalRouteFailed = 2;
	
}
	

