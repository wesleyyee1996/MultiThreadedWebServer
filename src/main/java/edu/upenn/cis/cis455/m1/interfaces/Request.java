/**
 * CIS 455/555 route-based HTTP framework
 * 
 * V. Liu, Z. Ives
 * 
 * Portions excerpted from or inspired by Spark Framework, 
 * 
 *                 http://sparkjava.com,
 * 
 * with license notice included below.
 */

/*
 * Copyright 2011- Per Wendel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.upenn.cis.cis455.m1.interfaces;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;

/**
 * Initial (simplified) request API, for Milestone 1
 */
public abstract class Request {
    /**
     * Indicates we have a persistent HTTP 1.1 connection
     */
    boolean persistent = false;
    protected String requestMethod;
    protected String host;
    protected String userAgent;
    protected int port;
    protected String pathInfo;
    protected String url;
    protected String uri;
    protected String protocol;
    protected String contentType;
    protected String ip;
    protected String body;
    protected int contentLength;
    protected String header;
    protected Hashtable<String,String> headers;

    /**
     * The request method (GET, POST, ...)
     */
    public abstract String requestMethod();
    
    
    /**Set request method
     * @param requestMethod
     */
    public abstract void setRequestMethod(String requestMethod);

    /**
     * @return The host
     */
    public abstract String host();
    
    /** Set host
     * @param host
     */
    public abstract void setHost(String host);

    /**
     * @return The user-agent
     */
    public abstract String userAgent();
    
    public abstract void setUserAgent(String agent);

    /**
     * @return The server port
     */
    public abstract int port();
    
    public abstract void setPort(int port);

    /**
     * @return The path
     */
    public abstract String pathInfo();
    
    public abstract void setPathInfo(String pathInfo);

    /**
     * @return The URL
     */
    public abstract String url();
    
    public abstract void setUrl(String url);

    /**
     * @return The URI up to the query string
     */
    public abstract String uri();
    
    public abstract void setUri(String uri);

    /**
     * @return The protocol name and version from the request
     */
    public abstract String protocol();
    
    public abstract void setProtocol(String protocol);

    /**
     * @return The MIME type of the body
     */
    public abstract String contentType();
    
    public abstract void setContentType(String contentType);

    /**
     * @return The client's IP address
     */
    public abstract String ip();
    
    public abstract void setIp(String ip);

    /**
     * @return The request body sent by the client
     */
    public abstract String body();
    
    public abstract void setBody(String body);

    /**
     * @return The length of the body
     */
    public abstract int contentLength();
    
    public abstract void setContentLength(int contentLength);

    /**
     * @return Get the item from the header
     */
    public abstract String headers(String name) throws IOException;
    
    public abstract void setHeaders(Hashtable<String,String> headers);

    public abstract Hashtable<String,String> headers();

    /**
     * Indicates we have a persistent HTTP 1.1 connection
     */
    public boolean persistentConnection() {
        return persistent;
    }

    /**
     * Sets whether we have a persistent HTTP 1.1 connection
     */
    public void persistentConnection(boolean persistent) {
        this.persistent = persistent;
    }
}
