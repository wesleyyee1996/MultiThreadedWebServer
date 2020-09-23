package edu.upenn.cis.cis455.m1.interfaces;

import java.io.IOException;

import edu.upenn.cis.cis455.Constants;

public class ResponseFactory {

	public Response ResponseFactory(String responseType) throws IOException {
		if (responseType == Constants.get) {
			return new GetResponse();
		}
		throw new IOException("Invalid response type: " + responseType);
	}
}
