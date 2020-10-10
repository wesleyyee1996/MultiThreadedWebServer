package edu.upenn.cis.cis455.m1.server;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.interfaces.Response;

public class ResponseObj extends Response {

	@Override
	public String getHeaders() {
		return null;
	}

	@Override
	public StringBuffer constructStatusLine() {
    	StringBuffer statusLine = new StringBuffer();
    	return statusLine.append(Constants.httpVersion + "200 OK" + Constants.CRFL);
	}
}
