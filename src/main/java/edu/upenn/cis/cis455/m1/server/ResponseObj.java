package edu.upenn.cis.cis455.m1.server;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Session;
import edu.upenn.cis.cis455.m2.server.SetCookie;
import edu.upenn.cis.cis455.utils.Tuple;
import edu.upenn.cis.cis455.m2.server.SessionObj;

public class ResponseObj extends Response {
	protected int _statusCode = 200;
    protected byte[] _body;
    protected byte[] _messageBody;
    protected String method = Constants.get;
    protected String _contentType = null; // e.g., "text/plain";
    protected String _protocol = "HTTP/1.1";
    protected ArrayList<Tuple<String,String>> cookieHeaders = new ArrayList<Tuple<String,String>>();
	
	private Hashtable<String, SetCookie> _cookies = new Hashtable<String, SetCookie>();
	
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
		this._headers.put(header, value);
	}

	@Override
	public void redirect(String location) {
		Hashtable<String, String> header = new Hashtable<String, String>();
		header.put("Location", location);
		this._headers = header;
		this._body = null;
		this._statusCode = 301;		
	}

	@Override
	public void redirect(String location, int httpStatusCode) {
		Hashtable<String, String> header = new Hashtable<String, String>();
		header.put("Location", location);
		this._headers = header;
		this._body = null;
		this._statusCode = httpStatusCode;			
	}

	/**
	 * adds a non-persistent cookie to the response
	 */
	@Override
	public void cookie(String name, String value) {
		SetCookie cookie = new SetCookie();
		cookie.setName(name);
		cookie.setValue(value);	
		this._cookies.put(name, cookie);
	}

	/**
	 * Adds a cookie to the response
	 */
	@Override
	public void cookie(String name, String value, int maxAge) {
		SetCookie cookie = new SetCookie();
		cookie.setName(name);
		cookie.setValue(value);
		cookie.setMaxAge(maxAge);
		this._cookies.put(name, cookie);		
	}

	@Override
	public void cookie(String name, String value, int maxAge, boolean secured) {
		
		SetCookie cookie = new SetCookie();
		cookie.setName(name);
		cookie.setValue(value);
		cookie.setMaxAge(maxAge);
		cookie.setSecured(secured);
		this._cookies.put(name, cookie);
		
	}

	@Override
	public void cookie(String name, String value, int maxAge, boolean secured, boolean httpOnly) {
		
		SetCookie cookie = new SetCookie();
		cookie.setName(name);
		cookie.setValue(value);
		cookie.setMaxAge(maxAge);
		cookie.setSecured(secured);
		cookie.setHttpOnly(httpOnly);
		this._cookies.put(name, cookie);
		
	}

	@Override
	public void cookie(String path, String name, String value) {
		SetCookie cookie = new SetCookie();
		cookie.setName(name);
		cookie.setValue(value);
		cookie.setPath(path);
		this._cookies.put(name, cookie);
		
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge) {
		SetCookie cookie = new SetCookie();
		cookie.setName(name);
		cookie.setValue(value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		this._cookies.put(name, cookie);
		
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge, boolean secured) {
		
		SetCookie cookie = new SetCookie();
		cookie.setName(name);
		cookie.setValue(value);
		cookie.setMaxAge(maxAge);
		cookie.setSecured(secured);
		cookie.setPath(path);
		this._cookies.put(name, cookie);
	}

	@Override
	public void cookie(String path, String name, String value, int maxAge, boolean secured, boolean httpOnly) {
		SetCookie cookie = new SetCookie();
		cookie.setName(name);
		cookie.setValue(value);
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
		SetCookie cookie = this._cookies.get(name);
		if (cookie.path().equals(path)) {
			cookie.setMaxAge(0);
		}
	}
	
	/**
	 * Creates the Set-Cookie headers from added cookies and 
	 * adds them to response headers
	 */
	public void convertSetCookiesToHeaders() {
		Iterator cookiesIterator = _cookies.entrySet().iterator();
		while(cookiesIterator.hasNext()) {
			Map.Entry<String,SetCookie> cookieHash = (Map.Entry<String,SetCookie>)cookiesIterator.next();
			SetCookie cookie = cookieHash.getValue();
			StringBuilder cookieString = new StringBuilder();
			cookieString.append(cookie.name()+"="+cookie.value()+"; ");
			if (!cookie.secured()) {
				if (cookie.expires() != null) {
					cookieString.append(Constants.expires+"="+cookie.expires()+"; ");
				}
				if (cookie.maxAge() != 0) {
					cookieString.append(Constants.maxAge+"="+cookie.maxAge()+"; ");
				}
				cookieString.append(Constants.domain+"="+"localhost:"+WebService.getInstance().getPort()+"; ");
				if (cookie.path() != null) {
					cookieString.append(Constants.path+"="+cookie.path()+"; ");
				}
				if (cookie.httpOnly()) {
					cookieString.append(Constants.httpOnly+"="+cookie.httpOnly()+"; ");
				}
			}	
			Tuple<String,String> cookieHeader = new Tuple<String,String>("Set-Cookie", cookieString.toString());
			this.cookieHeaders.add(cookieHeader);
		}
	}

	@Override
	public ArrayList<Tuple<String, String>> getCookieHeaders() {
		return this.cookieHeaders;
	}
}
