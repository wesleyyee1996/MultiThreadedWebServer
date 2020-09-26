package edu.upenn.cis.cis455.m1.interfaces;

import java.io.IOException;

import edu.upenn.cis.cis455.Constants;

public class ResponseFactory {

	// Create response
	public Response createResponse(String responseType) throws IOException {
		if (responseType == Constants.get) {
			return new GetResponse();
		}
		if (responseType == Constants.head) {
			return new HeadResponse();
		}
		throw new IOException("Invalid response type: " + responseType);
	}
	
	// Based on the status of the request handler, create different kind of response
	// Exception Response
	// Success Response
	
	// set Status Code
	// set Body
	// set Content Type
	
}
