package edu.upenn.cis.cis455.m2.server;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Hashtable;

public class SetCookie {

	private String _name;
	private String _value;
//	private Hashtable<String, String> nameValuePair = new Hashtable<String,String>();
	private Hashtable<String, String> _params = new Hashtable<String, String>();
	private String _path;
	private Duration _maxAge;
	private boolean _secured;
	private boolean _httpOnly;
	private Date _expires;
	
//	public void addNameValuePair(String name, String value) {
//		this.nameValuePair.put(name, value);
//	}
//	
//	public String getValueForName(String name) {
//		return this.nameValuePair.get(name);
//	}
	
	public void setName(String name) {
		this._name = name;
	}
	
	public String name() {
		return this._name;
	}
	
	public void setValue(String value) {
		this._value = value;
	}
	
	public String value() {
		return this._value;
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
		if (this._maxAge == null) {
			return 0;
		}
		return (int) this._maxAge.toMinutes()*60;
	}
	
	public void setMaxAge(int age) {
		this._maxAge = Duration.ofSeconds(age);
		Instant expireInstant = Instant.now().plusSeconds((int)this._maxAge.toMinutes()*60);
		this._expires = Date.from(expireInstant);
	}
	
	public String expires() {
		if (this._expires != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("EE, dd MMM yyyy kk:mm:ss z");
			return formatter.format(this._expires);
		}
		return null;
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
