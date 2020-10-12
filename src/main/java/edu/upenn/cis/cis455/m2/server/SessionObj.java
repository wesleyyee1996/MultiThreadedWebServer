package edu.upenn.cis.cis455.m2.server;

import java.time.Instant;
import java.util.Hashtable;
import java.util.Set;

import edu.upenn.cis.cis455.m2.interfaces.Session;

public class SessionObj extends Session {

	String _id;
	long _creationTime;
	long _lastAccessedTime;
	int _maxInactiveInterval;
	Hashtable<String,Object> _attributes;
	
	public SessionObj() {
		_id = createSessionId();
		_creationTime = Instant.now().toEpochMilli();
	}
	
	@Override
	public String id() {
		return this._id;
	}

	@Override
	public long creationTime() {
		access();
		return this._creationTime;
	}

	@Override
	public long lastAccessedTime() {
		return this._lastAccessedTime;
	}

	@Override
	public void invalidate() {
		access();
		// TODO Auto-generated method stub
		
	}

	@Override
	public int maxInactiveInterval() {
		access();
		return this._maxInactiveInterval;
	}

	@Override
	public void maxInactiveInterval(int interval) {
		access();
		this._maxInactiveInterval = interval;
	}

	@Override
	public void access() {
		_lastAccessedTime += Instant.now().toEpochMilli();
	}

	@Override
	public void attribute(String name, Object value) {
		access();
		this._attributes.put(name,value);		
	}

	@Override
	public Object attribute(String name) {
		access();
		return this._attributes.get(name);
	}

	@Override
	public Set<String> attributes() {
		access();
		return this._attributes.keySet();
	}

	@Override
	public void removeAttribute(String name) {
		access();
		this._attributes.remove(name);
		
	}
	
	
	
}
