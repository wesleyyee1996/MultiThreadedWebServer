package edu.upenn.cis.cis455.m1.interfaces;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;

public class HeadRequest extends Request{

	@Override
	public String requestMethod() {
		// TODO Auto-generated method stub
		return this.requestMethod;
	}

	@Override
	public void setRequestMethod(String requestMethod) {
		// TODO Auto-generated method stub
		this.requestMethod = requestMethod;
	}

	@Override
	public String host() {
		// TODO Auto-generated method stub
		return this.host;
	}

	@Override
	public void setHost(String host) {
		this.host = host;
		
	}

	@Override
	public String userAgent() {
		return this.userAgent;
	}

	@Override
	public void setUserAgent(String agent) {
		this.userAgent = agent;
		
	}

	@Override
	public int port() {
		return this.port;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
		
	}

	@Override
	public String pathInfo() {
		return this.pathInfo;
	}

	@Override
	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;		
	}

	@Override
	public String url() {
		return this.url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String uri() {
		return this.uri;
	}

	@Override
	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String protocol() {
		return this.protocol;
	}

	@Override
	public void setProtocol(String protocol) {
		this.protocol = protocol;
		
	}

	@Override
	public String contentType() {
		// TODO Auto-generated method stub
		return this.contentType;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String ip() {
		return this.ip;
	}

	@Override
	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String body() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBody(String body) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int contentLength() {
		return this.contentLength;
	}

	@Override
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
		
	}

	@Override
	public String headers(String name) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHeaders(Hashtable<String, String> headers) {
		this.headers = headers;
		
	}

	@Override
	public Hashtable<String,String> headers() {
		// TODO Auto-generated method stub
		return this.headers;
	}

	@Override
	public String root_dir() {
		// TODO Auto-generated method stub
		return this.root_dir;
	}

	@Override
	public void setRootDir(String root_dir) {
		this.root_dir = root_dir;
	}

}
