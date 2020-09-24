package edu.upenn.cis.cis455.m1.interfaces;

import java.io.IOException;
import java.util.Hashtable;

import edu.upenn.cis.cis455.*;
import edu.upenn.cis.cis455.m1.server.HttpTask;

public class RequestFactory {

	public  Request getRequest(Hashtable<String,String> parsedHeaders, HttpTask task) throws IOException {
		String requestType = parsedHeaders.get(Constants.Method);
		if (requestType.contentEquals(Constants.get)) {
			GetRequest getRequest = new GetRequest();
			setRequestParams(getRequest, task, requestType, parsedHeaders);
			return getRequest;
		}
		if (requestType == Constants.head) {
			return new HeadRequest();
		}
		//return null;
		throw new IOException("Error creating request for request type " + requestType);
	}
	
	public void setRequestParams(Request request, HttpTask task, String requestType, Hashtable<String,String> headers) {
		
		request.setRequestMethod(requestType);
		request.setPort(task.getPort());
		if (headers.get(Constants.ip) != null) {
			request.setIp(headers.get(Constants.ip));
		}
		if (headers.get(Constants.Uri) != null) {
			request.setUri(headers.get(Constants.Uri));
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
//		if (headers.get(Constants.Body) != null) {
//			request.setBody(headers.get(Constants.Body));
//		}
	}
}
