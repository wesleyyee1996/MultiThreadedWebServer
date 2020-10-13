package edu.upenn.cis.cis455.m1.server;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m2.interfaces.Request;

public class RequestFactory {

	public  RequestObj getRequest(Hashtable<String,String> parsedHeaders, HttpTask task, String uri, Hashtable<String, List<String>> parsedQueryParams) throws IOException {
		String requestType = parsedHeaders.get(Constants.Method);
		RequestObj request = new RequestObj();
		setRequestParams(request, task, requestType, parsedHeaders, uri, parsedQueryParams);
		return request;
	}
	
	public void setRequestParams(Request request, HttpTask task, String requestType, Hashtable<String,String> headers, String uri, Hashtable<String,List<String>> parsedQueryParams) {
		
		request.setRequestMethod(requestType);
		request.setPort(task.getPort());
		request.setRootDir(task.getRootDir());
		request.setHeaders(headers);
		request.setQueryParams(parsedQueryParams);
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
