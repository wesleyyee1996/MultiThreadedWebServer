package edu.upenn.cis.cis455.exceptions;

public class ConfigurationException extends Exception{
	public ConfigurationException(String message) {
		super(message);
	}
	
	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ConfigurationException(Throwable cause) {
		super(cause);
	}
}
