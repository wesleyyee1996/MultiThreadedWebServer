package edu.upenn.cis.cis455.m2.server;

public class Cookie {

	private String _name;
	private String _sessionId;
	private String _path;
	private int _maxAge;
	private boolean _secured;
	private boolean _httpOnly;
	
	public String name() {
		return this._name;
	}
	
	public void setName(String name) {
		this._name = name;
	}
	
	public String sessionId() {
		return this._sessionId;
	}
	
	public void setSessionId(String id) {
		this._sessionId = id;
	}
	
	public String path() {
		return this._path;
	}
	
	public void setPath(String path) {
		this._path = path;
	}
	
	public int maxAge() {
		return this._maxAge;
	}
	
	public void setMaxAge(int age) {
		this._maxAge = age;
	}
	
	public boolean secured() {
		return this._secured;
	}
	
	public void setSecured(boolean secured) {
		this._secured = secured;
	}
	
	public void httpOnly(boolean httpOnly) {
		this._httpOnly = httpOnly;
	}
	
	public boolean httpOnly() {
		return this._httpOnly;
	}
	
}
