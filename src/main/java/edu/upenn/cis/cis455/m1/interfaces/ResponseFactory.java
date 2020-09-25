package edu.upenn.cis.cis455.m1.interfaces;

import java.io.IOException;

import edu.upenn.cis.cis455.Constants;

public class ResponseFactory {

	public Response getResponse(String responseType) throws IOException {
		if (responseType == Constants.get) {
			return new GetResponse();
		}
		if (responseType == Constants.head) {
			return new HeadResponse();
		}
		throw new IOException("Invalid response type: " + responseType);
	}
}
