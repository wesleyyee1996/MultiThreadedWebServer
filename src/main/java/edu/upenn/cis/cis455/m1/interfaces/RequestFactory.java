package edu.upenn.cis.cis455.m1.interfaces;

import java.io.IOException;

import edu.upenn.cis.cis455.*;

public class RequestFactory {

	public Request RequestFactory(String requestType) throws IOException {
		if (requestType == Constants.get) {
			return new GetRequest();
		}
		throw new IOException("Invalid request type: " + requestType);
	}
}
