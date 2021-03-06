package edu.upenn.cis.cis455.m2.interfaces;

import java.time.Instant;
import java.util.Set;

public abstract class Session {
	
    public abstract String id();

    /**
     * Time the session was created
     */
    public abstract long creationTime();

    /**
     * Time the session was last accessed
     */
    public abstract long lastAccessedTime();

    /**
     * Invalidate the session
     */
    public abstract void invalidate();

    /**
     * Get the inactivity timeout
     */
    public abstract int maxInactiveInterval();

    /**
     * Set the inactivity timeout
     */
    public abstract void maxInactiveInterval(int interval);

    /**
     * Notify the session that it was just accessed -- should update the last
     * accessed time
     */
    public abstract void access();

    /**
     * Store an object under the name
     */
    public abstract void attribute(String name, Object value);
    
    /**
     * Returns whether session has been invalidated or not
     * @return
     */
    public abstract boolean invalidated();
    
    public abstract Instant expiresTime();

    /**
     * Get an object associated with the name
     */
    public abstract Object attribute(String name);

    /**
     * Get all objects bound to the session
     */
    public abstract Set<String> attributes();

    /**
     * Delete an object from the session
     */
    public abstract void removeAttribute(String name);
    
    /**
     * Creates a random 15 character long number for sessionId
     * @return
     */
    public String createSessionId() {
		String nums = "0123456789";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 15; i++) {
			int idx = (int)(nums.length()*Math.random());
			sb.append(nums.charAt(idx));
		}
		return sb.toString();
	}
    
}
