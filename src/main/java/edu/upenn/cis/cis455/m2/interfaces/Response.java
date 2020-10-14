package edu.upenn.cis.cis455.m2.interfaces;

import java.util.ArrayList;

import edu.upenn.cis.cis455.utils.Tuple;

public abstract class Response extends edu.upenn.cis.cis455.m1.interfaces.Response {
    /**
     * Add a header key/value
     */
    public abstract void header(String header, String value);

    /**
     * Trigger an HTTP redirect to a new location
     */
    public abstract void redirect(String location);

    /**
     * Trigger a redirect with a specific HTTP 3xx status code
     */
    public abstract void redirect(String location, int httpStatusCode);

    public abstract void cookie(String name, String value);

    public abstract void cookie(String name, String value, int maxAge);

    public abstract void cookie(String name, String value, int maxAge, boolean secured);

    public abstract void cookie(String name, String value, int maxAge, boolean secured, boolean httpOnly);

    public abstract void cookie(String path, String name, String value);

    public abstract void cookie(String path, String name, String value, int maxAge);

    public abstract void cookie(String path, String name, String value, int maxAge, boolean secured);

    public abstract void cookie(String path, String name, String value, int maxAge, boolean secured, boolean httpOnly);

    /**
     * Set the cookie Expires date to a date in the past
     * @param name
     */
    public abstract void removeCookie(String name);

    public abstract void removeCookie(String path, String name);
    
    public abstract void convertSetCookiesToHeaders();
    
    public abstract ArrayList<Tuple<String,String>> getCookieHeaders();
}
