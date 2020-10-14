package edu.upenn.cis.cis455.utils;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.handling.HttpParsing;
import edu.upenn.cis.cis455.m2.interfaces.Response;

public class SocketOutputBodyBuilder {
    final static Logger logger = LogManager.getLogger(SocketOutputBodyBuilder.class);

	String CRFL = "\r\n";
	StringBuffer socketOutput;
	byte[] socketOutputBytes;
	byte[] socketOutputBytesOutput;
	
	public byte[] buildSocketOutput(Response response) {
		socketOutput = new StringBuffer();
		StringBuffer statusLine = buildStatusLine(response.status(), response.protocol());
		socketOutput.append(statusLine);
		if (!response.getHeadersRaw().isEmpty()) {
			StringBuffer headers = buildHeaders(response.getHeadersRaw());
			socketOutput.append(headers);
		}
		else {
			socketOutput.append(CRFL);
		}
		
		if (!response.getCookieHeaders().isEmpty()) {
			StringBuffer cookieHeaders = buildCookieHeaders(response.getCookieHeaders());
			socketOutput.append(cookieHeaders);
		}
		else {
			socketOutput.append(CRFL);
		}
		
		socketOutputBytes = stringToByteArray(socketOutput.toString());
		
		// if it's a head request, then don't add the body
		if (response.method().equals(Constants.head)) {
			return socketOutputBytes;
		}
		
		// if it's not a head request, then add the body
		if (response.bodyRaw() != null) {
			socketOutputBytesOutput = combineByteArrays(socketOutputBytes, response.bodyRaw());
		}
		if (response.bodyRaw() == null) {
			socketOutputBytesOutput = combineByteArrays(socketOutputBytes, CRFL.getBytes());
		}
		return socketOutputBytesOutput;
	}
	
	private StringBuffer buildStatusLine(int statusCode, String httpVersion) {
		logger.debug("Status code: "+statusCode);
		logger.debug("Http Version: "+httpVersion);
		StringBuffer statusLine = new StringBuffer();
    	return statusLine.append(httpVersion + " "+ Integer.toString(statusCode) + " "+HttpParsing.explainStatus(statusCode)+ Constants.CRFL);
	}
	
	private StringBuffer buildHeaders(Hashtable<String,String> headers) {
		StringBuffer bodyHeaders = new StringBuffer();
    	for (String key : headers.keySet()) {
    		bodyHeaders.append(key + ": " + headers.get(key) + Constants.CRFL);
    	}
    	return bodyHeaders;
	}
	
	private StringBuffer buildCookieHeaders(ArrayList<Tuple<String,String>> cookieHeaders) {
		StringBuffer cookieHeaderString = new StringBuffer();
		for (Tuple<String,String> cookie : cookieHeaders) {
			cookieHeaderString.append(cookie.x +": "+cookie.y+Constants.CRFL);
		}
		cookieHeaderString.append(CRFL);
		return cookieHeaderString;
	}
	
	private byte[] stringToByteArray(String str) {
		return str.getBytes();
	}
	
	private byte[] combineByteArrays(byte[] array1, byte[] array2) {
		byte[] _newMessageBody = new byte[array1.length + array2.length];
    	System.arraycopy(array1, 0, _newMessageBody, 0, array1.length);
    	System.arraycopy(array2, 0, _newMessageBody, array1.length, array2.length);
    	return _newMessageBody;
	}
}
