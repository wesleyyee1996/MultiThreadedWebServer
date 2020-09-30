package edu.upenn.cis.cis455.m1.interfaces;

import java.io.File;
import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;

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
		StringBuffer headers = buildHeaders(response.getHeadersRaw());
		socketOutput.append(headers);
		socketOutputBytes = stringToByteArray(socketOutput.toString());
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
    	return statusLine.append(httpVersion + " "+ Integer.toString(statusCode) + " "+Constants.statusCodeReasons.get(statusCode)+ Constants.CRFL);
	}
	
	private StringBuffer buildHeaders(Hashtable<String,String> headers) {
		StringBuffer bodyHeaders = new StringBuffer();
    	for (String key : headers.keySet()) {
    		bodyHeaders.append(key + " : " + headers.get(key) + Constants.CRFL);
    	}
    	bodyHeaders.append(Constants.CRFL);
    	return bodyHeaders;
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
