package edu.upenn.cis.cis455.utils;

public class CommandLineValues {

	private int _port = 45555;
	private String _root_dir = "./www";
	private boolean _valuesOk = true;
	private String _errorMessage;
	private static CommandLineValues _commandLineValues;
	
	public static CommandLineValues getInstance() {
		if (_commandLineValues == null) {
			_commandLineValues = new CommandLineValues();
		}
		return _commandLineValues;
	}
	
	public String getRootDir() {
		return _root_dir;
	}
	
	public void setRootDir(String root_dir) {
		_root_dir = root_dir;
	}
	
	public int getPort() {
		return _port;
	}
	
	public void setPort(int port) {
		_port = port;
	}
	
	public boolean getValuesOk() {
		return _valuesOk;
	}
	
	public void setValuesOk(boolean areValuesOk) {
		_valuesOk = areValuesOk;
	}
	
	public String getErrorMessage() {
		return _errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}
	
	public void resetValues() {
		_port = 45555;
		_root_dir = "/.www";
		_valuesOk = true;
	}
	
}
