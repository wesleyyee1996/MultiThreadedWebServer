package edu.upenn.cis.cis455.m2.server;

import java.time.Instant;
import java.time.Duration;
import java.util.Hashtable;
import java.util.Set;

import edu.upenn.cis.cis455.m2.interfaces.Session;

public class SessionObj extends Session {

	String _id;
	Instant _creationTime;
	Instant _lastAccessedTime;
	
	Duration _maxInactiveInterval;
	Hashtable<String,Object> _attributes;
	
	public SessionObj() {
		_id = createSessionId();
		_creationTime = Instant.now();
	}	
	
	/**
	 * Returns a string containing the unique identifier assigned to 
	 * this session
	 */
	@Override
	public String id() {
		return this._id;
	}

	/**
	 * Returns the time when this session was created, measured in milliseconds since midnight
	 * January 1, 1970 GMT
	 */
	@Override
	public long creationTime() {
		access();
		return this._creationTime.toEpochMilli();
	}

	/**
	 * Returns the last time the client sent a request associated with this session,
	 * as the number of milliseconds since midnight January 1, 1970, GMT, and
	 * marked by the time the container received the request
	 */
	@Override
	public long lastAccessedTime() {
		return _lastAccessedTime.toEpochMilli();
	}

	/**
	 * Invalidates this session then unbinds any objects bound to it
	 */
	@Override
	public void invalidate() {
		this._invalidated = true;
	}

	/**
	 * Returns the maximum time interval, in seconds, that the container will keep this
	 * session open between client accesses
	 */
	@Override
	public int maxInactiveInterval() {
		return (int)this._maxInactiveInterval.getSeconds();
	}

	/**
	 * Sets the time, in seconds, between client requests the web container will invalidate
	 * the session
	 */
	@Override
	public void maxInactiveInterval(int interval) {
		access();
		this._maxInactiveInterval = Duration.ofSeconds(interval);
	}

	/**
	 * Updates the last accessed time if the session hasn't expired
	 */
	@Override
	public void access() {
		
		// check if the session is invalidated already
		if (!_invalidated) {
			// check if session has expired or not
			if (Instant.now().isAfter(this._expiresTime)) {
				_invalidated = true;
			}
			// if session is not expired then reset expiration time to 
			// current time + maxInactiveInterval
			else {
				this._lastAccessedTime = Instant.now();
				this._expiresTime = Instant.now().plus(_maxInactiveInterval);
			}
		}
		else {
			return;
		}
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

	/**
	 * Removes the object bound with the specified name from this session
	 */
	@Override
	public void removeAttribute(String name) {
		access();
		this._attributes.remove(name);
		
	}
}
