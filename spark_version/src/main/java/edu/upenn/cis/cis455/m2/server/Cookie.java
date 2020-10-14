package edu.upenn.cis.cis455.m2.server;

import java.util.Hashtable;

public class Cookie {

	private String _name;
	private String _value;
	private Hashtable<String, String> nameValuePair = new Hashtable<String,String>();
	private Hashtable<String, String> _params = new Hashtable<String, String>();
	private String _path;
	private int _maxAge;
	private boolean _secured;
	private boolean _httpOnly;
	
	public void addNameValuePair(String name, String value) {
		this.nameValuePair.put(name, value);
	}
	
	public String getValueForName(String name) {
		return this.nameValuePair.get(name);
	}
	
	public void addParam(String key, String val) {
		this._params.put(key, val);
	}
	
	public String getParamForKey(String key) {
		return this._params.get(key);
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
	
	public void setHttpOnly(boolean httpOnly) {
		this._httpOnly = httpOnly;
	}
	
	public boolean httpOnly() {
		return this._httpOnly;
	}
	
}
