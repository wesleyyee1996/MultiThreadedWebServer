package edu.upenn.cis.cis455.m1.interfaces;

import java.io.IOException;
import java.util.Hashtable;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.server.HttpTask;

public class RequestFactory {

	public  Request getRequest(Hashtable<String,String> parsedHeaders, HttpTask task, String uri) throws IOException {
		String requestType = parsedHeaders.get(Constants.Method);
		if (requestType.equals(Constants.get)) {
			GetRequest getRequest = new GetRequest();
			setRequestParams(getRequest, task, requestType, parsedHeaders, uri);
			return getRequest;
		}
		if (requestType.equals(Constants.head)) {
			HeadRequest headRequest = new HeadRequest();
			setRequestParams(headRequest, task, requestType, parsedHeaders, uri);
			return headRequest;
		}
		throw new IOException("Error creating request for request type " + requestType);
	}
	
	public void setRequestParams(Request request, HttpTask task, String requestType, Hashtable<String,String> headers, String uri) {
		
		request.setRequestMethod(requestType);
		request.setPort(task.getPort());
		request.setRootDir(task.getRootDir());
		request.setHeaders(headers);
		if (headers.get(Constants.ip) != null) {
			request.setIp(headers.get(Constants.ip));
		}
		if (uri != null) {
			request.setUri(uri);
		}
		if (headers.get(Constants.userAgent) != null) {
			request.setUserAgent(headers.get(Constants.userAgent));
		}
		if (headers.get(Constants.contentLength)!= null) {
			request.setContentLength(Integer.parseInt(headers.get(Constants.contentLength)));
		}
		if (headers.get(Constants.contentType)!= null) {
			request.setContentType(headers.get(Constants.contentType));
		}
		if (headers.get(Constants.HttpVersion) != null) {
			request.setProtocol(headers.get(Constants.HttpVersion));
		}
		if (headers.get(Constants.host) != null) {
			request.setProtocol(headers.get(Constants.host));
		}
	}
}
