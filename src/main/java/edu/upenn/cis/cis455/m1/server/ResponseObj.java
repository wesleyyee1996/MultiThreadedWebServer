package edu.upenn.cis.cis455.m1.server;

import java.util.Hashtable;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Session;
import edu.upenn.cis.cis455.m2.server.Cookie;
import edu.upenn.cis.cis455.m2.server.SessionObj;

public class ResponseObj extends Response {

	private Hashtable<String, Cookie> _cookies = new Hashtable<String, Cookie>();
	
	@Override
	public String getHeaders() {
		return null;
	}

	@Override
	public StringBuffer constructStatusLine() {
    	StringBuffer statusLine = new StringBuffer();
    	return statusLine.append(Constants.httpVersion + "200 OK" + Constants.CRFL);
	}

	@Override
	public void header(String header, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redirect(String location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redirect(String location, int httpStatusCode) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * adds a non-persistent cookie to the response
	 */
	@Override
	public void cookie(String name, String value) {
		Cookie cookie = new Cookie();
		cookie.addNameValuePair(name, value);	
	}

	/**
	 * Adds a cookie to the response
	 */
	@Override
	public void cookie(String name, String value, int maxAge) {
		Cookie cookie = new Cookie();
		cookie.addNameValuePair(name, value);
		cookie.setMaxAge(maxAge);
		this._cookies.put(name, cookie);		
	}

	@Override
	public void cookie(String name, String value, int maxAge, boolean secured) {
		
		Cookie cookie = new Cookie();
		cookie.addNameValuePair(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setSecured(secured);
		this._cookies.put(name, cookie);
		
	}

	@Override
	public void cookie(String name, String value, int maxAge, boolean secured, boolean httpOnly) {
		
		Cookie cookie = new Cookie();
		cookie.addNameValuePair(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setSecured(secured);
		cookie.setHttpOnly(httpOnly);
		this._cookies.put(name, cookie);
		
	}

	@Override
	public void cookie(String path, String name, String value) {
		Cookie cookie = new Cookie();
		cookie.addNameValuePair(name, value);
		cookie.setPath(path);
		this._cookies.put(name, cookie);
		
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge) {
		Cookie cookie = new Cookie();
		cookie.addNameValuePair(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		this._cookies.put(name, cookie);
		
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge, boolean secured) {
		
		Cookie cookie = new Cookie();
		cookie.addNameValuePair(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setSecured(secured);
		cookie.setPath(path);
		this._cookies.put(name, cookie);
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge, boolean secured, boolean httpOnly) {
		Cookie cookie = new Cookie();
		cookie.addNameValuePair(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setSecured(secured);
		cookie.setHttpOnly(httpOnly);
		cookie.setPath(path);
		this._cookies.put(name, cookie);
		
	}

	@Override
	public void removeCookie(String name) {
		this._cookies.get(name).setMaxAge(0);		
	}

	@Override
	public void removeCookie(String path, String name) {
		Cookie cookie = this._cookies.get(name);
		if (cookie.path().equals(path)) {
			cookie.setMaxAge(0);
		}
		
	}
}
