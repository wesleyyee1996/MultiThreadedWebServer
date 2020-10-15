package edu.upenn.cis.cis455.m1.server;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Session;
import edu.upenn.cis.cis455.m2.server.SessionObj;
import edu.upenn.cis.cis455.m2.server.WebService;

public class RequestObj extends Request{
	static final Logger logger = LogManager.getLogger(RequestObj.class);
	
	Hashtable<String, String> _cookies = new Hashtable<String,String>();
	Hashtable<String, Object> _attributes = new Hashtable<String, Object>();
	String _queryString;
	Hashtable<String,List<String>> _queryParams = new Hashtable<String,List<String>>();
	Hashtable<String,String> _uriPathParams = new Hashtable<String,String>();
	String _sessionId;
	

	@Override
	public String requestMethod() {
		// TODO Auto-generated method stub
		return this.requestMethod;
	}

	@Override
	public String host() {
		// TODO Auto-generated method stub
		return this.host;
	}

	@Override
	public String userAgent() {
		// TODO Auto-generated method stub
		return this.userAgent;
	}

	@Override
	public int port() {
		// TODO Auto-generated method stub
		return this.port;
	}

	@Override
	public String pathInfo() {
		// TODO Auto-generated method stub
		return this.pathInfo;
	}

	@Override
	public String url() {
		// TODO Auto-generated method stub
		return this.url;
	}

	@Override
	public String uri() {
		// TODO Auto-generated method stub
		return this.uri;
	}

	@Override
	public String protocol() {
		// TODO Auto-generated method stub
		return this.protocol;
	}

	@Override
	public String contentType() {
		// TODO Auto-generated method stub
		return this.contentType;
	}

	@Override
	public String ip() {
		// TODO Auto-generated method stub
		return this.ip;
	}

	@Override
	public String body() {
		// TODO Auto-generated method stub
		return this.body;
	}

	@Override
	public int contentLength() {
		// TODO Auto-generated method stub
		return this.contentLength;
	}

	@Override
	public String headers(String name) throws IOException {
		// TODO Auto-generated method stub
		try {
			String header = this.headers.get(name);
			return header;
		}
		catch (Exception e){
			System.out.println("No headers in request named " + name + ": e");
			return null;
		}
	}

	@Override
	public Hashtable<String,String> headers() {
		// TODO Auto-generated method stub
		return headers;
	}

	@Override
	public void setRequestMethod(String requestMethod) {
		// TODO Auto-generated method stub
		this.requestMethod = requestMethod;
	}

	@Override
	public void setHost(String host) {
		// TODO Auto-generated method stub
		this.host = host;
	}

	@Override
	public void setUserAgent(String agent) {
		// TODO Auto-generated method stub
		this.userAgent = agent;
	}

	@Override
	public void setPort(int port) {
		// TODO Auto-generated method stub
		this.port = port;
	}

	@Override
	public void setPathInfo(String pathInfo) {
		// TODO Auto-generated method stub
		this.pathInfo = pathInfo;
	}

	@Override
	public void setUrl(String url) {
		// TODO Auto-generated method stub
		this.url = url;
	}

	@Override
	public void setUri(String uri) {
		// TODO Auto-generated method stub
		this.uri = uri;
	}

	@Override
	public void setProtocol(String protocol) {
		// TODO Auto-generated method stub
		this.protocol = protocol;
	}

	@Override
	public void setContentType(String contentType) {
		// TODO Auto-generated method stub
		this.contentType = contentType;
	}

	@Override
	public void setIp(String ip) {
		// TODO Auto-generated method stub
		this.ip = ip;
	}

	@Override
	public void setBody(String body) {
		// TODO Auto-generated method stub
		this.body = body;
	}

	@Override
	public void setContentLength(int contentLength) {
		// TODO Auto-generated method stub
		this.contentLength = contentLength;
	}

	@Override
	public void setHeaders(Hashtable<String, String> headers) {
		// TODO Auto-generated method stub
		this.headers = headers;
	}
	
	@Override
	public String root_dir() {
		// TODO Auto-generated method stub
		return this.root_dir;
	}

	@Override
	public void setRootDir(String root_dir) {
		this.root_dir = root_dir;
		
	}

	/**
	 * Gets the session for the request provided, if it has one. If there's no
	 * request for given session, then returns a new one
	 */
	@Override
	public Session session() {
		
		if (this._sessionId != null) {
			return WebService.getInstance().getSession(_sessionId);
		}
		// Get the Cookie header from the request and then
		// find the cookie w/ JSESSIONID
		else if (this.headers().get("cookie") != null) {
			String cookieString = this.headers().get("cookie");
			_cookies = parseCookieString(cookieString);
			_sessionId = _cookies.get("JSESSIONID");
			return WebService.getInstance().getSession(_sessionId);
		}
		
		
		return session(true);
	}
	
	

	/**
     * @return Gets or creates a session for this request
     */
	@Override
	public Session session(boolean create) {
		if (create) {
			this._sessionId = WebService.getInstance().createSession();
			return WebService.getInstance().getSession(_sessionId);
		}
		return null;
	}

	
	
	/**
	 * Returns the map containing all route params
	 */
	@Override
	public Map<String, String> params() {
		return this._uriPathParams;
	}
	
	/**
	 * Sets the query parameters (NOT the URI Path parameters)
	 * @param queryParams
	 */
	public void setQueryParams(Hashtable<String, List<String>> queryParams) {
		this._queryParams = queryParams;
	}

	/**
	 * Returns the value of the provided queryParam 
	 * Example: query parameter 'id' from the following request URI: /hello?id=foo
	 */
	@Override
	public String queryParams(String param) {
		return _queryParams.get(param).get(0);
	}

	/**
	 * Gets all the values of the query param 
	 * Example: query parameter 'id' from the following request URI: /hello?id=foo&id=bar
	 */
	@Override
	public List<String> queryParamsValues(String param) {
		return _queryParams.get(param);
	}

	/**
	 * Returns all the query parameters
	 */
	@Override
	public Set<String> queryParams() {
		return _queryParams.keySet();
	}

	/**
	 * Returns the query string
	 */
	@Override
	public String queryString() {
		return this._queryString;
	}

	/**
	 * Sets an attribute on the request (can be fetched in filter/routes later in the chain)
	 */
	@Override
	public void attribute(String attrib, Object val) {
		this._attributes.put(attrib, val);
	}

	@Override
	public Object attribute(String attrib) {
		return this._attributes.get(attrib);
	}

	@Override
	public Set<String> attributes() {
		return this._attributes.keySet();
	}

	/**
	 * Returns the request cookies as Map<cookieId,cookieName>
	 */
	@Override
	public Map<String, String> cookies() {
		Hashtable<String,String> stringCookies = new Hashtable<String,String>();
		for (String cookieId : this._cookies.keySet()) {
			stringCookies.put(cookieId,cookie(cookieId));
		}
		return stringCookies;
	}
	

}
