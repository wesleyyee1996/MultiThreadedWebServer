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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.Hashtable;

import edu.upenn.cis.cis455.Constants;

public abstract class Response {
    protected int _statusCode = 200;
    protected byte[] _body;
    protected byte[] _messageBody;
    protected String method = Constants.get;
    protected String _contentType = null; // e.g., "text/plain";
    protected Hashtable<String,String> _headers = new Hashtable<String,String>();
    protected String _protocol = "HTTP/1.1";

    public int status() {
        return _statusCode;
    }

    public void status(int statusCode) {
        this._statusCode = statusCode;
    }
    
    public void setHeaders(Hashtable<String,String> headers) {
    	this._headers = headers;
    }
    
    public void addToHeaders(String key, String value) {
    	this._headers.put(key, value);
    }
    
    public void setMethod(String method) {
    	this.method = method;
    }
    
    public String method() {
    	return this.method;
    }
        
    public String protocol() {
    	return this._protocol;
    }
    
    public void setProtocol (String protocol) {
    	this._protocol = protocol;
    }

    /** 
     * Returns the overall body of the Response. Need to call constructBody() before this returns anything.
     * @return
     */
    public String body() {
        try {
            return _body == null ? "" : new String(_body, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    public byte[] bodyRaw() {
        return _body;
    }

    public void bodyRaw(byte[] b) {
        _body = b;
    }

    public void body(String body) {
        this._body = body == null ? null : body.getBytes();
    }
    
    public void setMessageBodyFromFile(File file) throws IOException {
    	this._messageBody = Files.readAllBytes(file.toPath());
    }
    
    public void constructBody() {
    	StringBuffer body = new StringBuffer();
    	
    	body = constructStatusLine();
    	
    	body.append(constructHeaders());    	
    	
    	body.append(Constants.CRFL);
    	this._body = body.toString().getBytes();
    	
    	// Combine message bodies
    	byte[] _newMessageBody = new byte[_body.length + _messageBody.length];
    	System.arraycopy(_body, 0, _newMessageBody, 0, _body.length);
    	System.arraycopy(_messageBody, 0, _newMessageBody, _body.length, _messageBody.length);
    	this._body = _newMessageBody;
    }
    
    public abstract StringBuffer constructStatusLine();
    
    private StringBuffer constructHeaders() {
    	StringBuffer bodyHeaders = new StringBuffer();
    	for (String key : _headers.keySet()) {
    		bodyHeaders.append(key + " : " + _headers.get(key) + Constants.CRFL);
    	}
    	return bodyHeaders;
    }

    public String type() {
        return _contentType;
    }

    public void type(String contentType) {
        this._contentType = contentType;
    }
    
    public abstract String getHeaders();
    
    public Hashtable<String,String> getHeadersRaw() {
    	return this._headers;
    }

    
}
