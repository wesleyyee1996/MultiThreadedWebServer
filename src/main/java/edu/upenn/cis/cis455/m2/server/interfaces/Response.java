package edu.upenn.cis.cis455.m2.server.interfaces;

public abstract class Response extends edu.upenn.cis.cis455.m1.server.interfaces.Response {
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
    
    public abstract void removeCookie(String name);
    
    public abstract void removeCookie(String path, String name);
}
