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
		//this._headers.put("Cookie", name+"="+value+"; Max-Age="+maxAge);
		
	}

	@Override
	public void cookie(String name, String value, int maxAge, boolean secured) {
		
		Cookie cookie = new Cookie();
		cookie.addNameValuePair(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setSecured(secured);
		this._cookies.put(name, cookie);
		
//		if (secured) {
//			this._headers.put("Cookie", name+"="+value+"; Max-Age="+maxAge+"; Secure");
//		}
//		else {
//			cookie(name, value, maxAge);
//		}
		
	}

	@Override
	public void cookie(String name, String value, int maxAge, boolean secured, boolean httpOnly) {
		if (secured && httpOnly) {
			this._headers.put("Cookie", name+"="+value+"; Max-Age="+maxAge+"; Secure; HttpOnly");
		}
		else if(secured && !httpOnly) {
			cookie(name, value, maxAge, secured);
		}
		else if (!secured && httpOnly){
			this._headers.put("Cookie", name+"="+value+"; Max-Age="+maxAge+"; HttpOnly");
		}
		else if (!secured && !httpOnly) {
			this._headers.put("Cookie", name+"="+value+"; Max-Age="+maxAge+";");
		}
		
	}

	@Override
	public void cookie(String path, String name, String value) {
		this._headers.put("Cookie", name+"="+value+"; Path: "+path);	
		
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge) {
		this._headers.put("Cookie", name+"="+value+"; Path: "+path+"; Max-Age="+maxAge);
		
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge, boolean secured) {
		
		if (secured) {
			this._headers.put("Cookie", name+"="+value+"; Path: "+path+"; Max-Age="+maxAge+"; Secure");
		}
		else {
			cookie(path, name, value, maxAge);
		}
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge, boolean secured, boolean httpOnly) {
		if (secured && httpOnly) {
			this._headers.put("Cookie", name+"="+value+"; Path: "+path+"; Max-Age="+maxAge+"; Secure; HttpOnly");
		}
		else if(secured && !httpOnly) {
			cookie(path, name, value, maxAge, secured);
		}
		else if (!secured && httpOnly){
			this._headers.put("Cookie", name+"="+value+"; Path: "+path+"; Max-Age="+maxAge+"; HttpOnly");
		}
		else if (!secured && !httpOnly) {
			this._headers.put("Cookie", name+"="+value+"; Path: "+path+"; Max-Age="+maxAge+";");
		}
		
	}

	@Override
	public void removeCookie(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCookie(String path, String name) {
		// TODO Auto-generated method stub
		
	}
}
