package edu.upenn.cis.cis455.m1.interfaces;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;

public class GetRequest extends Request{

	@Override
	public String requestMethod() {
		// TODO Auto-generated method stub
		return this.requestMethod;
	}

	@Override
	public String host() {
		// TODO Auto-generated method stub
		return this.host;
	}

	@Override
	public String userAgent() {
		// TODO Auto-generated method stub
		return this.userAgent;
	}

	@Override
	public int port() {
		// TODO Auto-generated method stub
		return this.port;
	}

	@Override
	public String pathInfo() {
		// TODO Auto-generated method stub
		return this.pathInfo;
	}

	@Override
	public String url() {
		// TODO Auto-generated method stub
		return this.url;
	}

	@Override
	public String uri() {
		// TODO Auto-generated method stub
		return this.uri;
	}

	@Override
	public String protocol() {
		// TODO Auto-generated method stub
		return this.protocol;
	}

	@Override
	public String contentType() {
		// TODO Auto-generated method stub
		return this.contentType;
	}

	@Override
	public String ip() {
		// TODO Auto-generated method stub
		return this.ip;
	}

	@Override
	public String body() {
		// TODO Auto-generated method stub
		return this.body;
	}

	@Override
	public int contentLength() {
		// TODO Auto-generated method stub
		return this.contentLength;
	}

	@Override
	public String headers(String name) throws IOException {
		// TODO Auto-generated method stub
		try {
			String header = this.headers.get(name);
			return header;
		}
		catch (Exception e){
			System.out.println("No headers in request named " + name + ": e");
			return null;
		}
	}

	@Override
	public Set<String> headers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRequestMethod(String requestMethod) {
		// TODO Auto-generated method stub
		this.requestMethod = requestMethod;
	}

	@Override
	public void setHost(String host) {
		// TODO Auto-generated method stub
		this.host = host;
	}

	@Override
	public void setUserAgent(String agent) {
		// TODO Auto-generated method stub
		this.userAgent = agent;
	}

	@Override
	public void setPort(int port) {
		// TODO Auto-generated method stub
		this.port = port;
	}

	@Override
	public void setPathInfo(String pathInfo) {
		// TODO Auto-generated method stub
		this.pathInfo = pathInfo;
	}

	@Override
	public void setUrl(String url) {
		// TODO Auto-generated method stub
		this.url = url;
	}

	@Override
	public void setUri(String uri) {
		// TODO Auto-generated method stub
		this.uri = uri;
	}

	@Override
	public void setProtocol(String protocol) {
		// TODO Auto-generated method stub
		this.protocol = protocol;
	}

	@Override
	public void setContentType(String contentType) {
		// TODO Auto-generated method stub
		this.contentType = contentType;
	}

	@Override
	public void setIp(String ip) {
		// TODO Auto-generated method stub
		this.ip = ip;
	}

	@Override
	public void setBody(String body) {
		// TODO Auto-generated method stub
		this.body = body;
	}

	@Override
	public void setContentLength(int contentLength) {
		// TODO Auto-generated method stub
		this.contentLength = contentLength;
	}

	@Override
	public void setHeaders(Hashtable<String, String> headers) {
		// TODO Auto-generated method stub
		this.headers = headers;
	}
	

}
