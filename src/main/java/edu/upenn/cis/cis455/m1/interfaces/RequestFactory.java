package edu.upenn.cis.cis455.m1.interfaces;

import java.io.IOException;

import edu.upenn.cis.cis455.*;
import edu.upenn.cis.cis455.m1.server.HttpTask;

public class RequestFactory {

	public static Request getRequest(String requestType) {
		if (requestType == Constants.get) {
			Request getRequest = new GetRequest();
			setRequestParams(getRequest, task, requestType)
		}
		if (requestType == Constants.head) {
			return new HeadRequest();
		}
		return null;
		//throw new IOException("Invalid request type: " + requestType);
	}
	
	public void setRequestParams(Request request, HttpTask task, String requestType) {
		
		request.requestMethod(requestType);
		request.port(task.getPort());
		
	}
}
