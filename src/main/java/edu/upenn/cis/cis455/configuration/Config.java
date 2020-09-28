package edu.upenn.cis.cis455.configuration;

public class Config {
	private int _port;
	private String _root_dir;
	
	public int getPort() {
		return _port;
	}
	
	public String getRootDir() {
		return _root_dir;
	}
	
	public void setPort(int port) {
		this._port = port;
	}
	
	public void setRootDir(String root_dir) {
		this._root_dir = root_dir;
	}
}
