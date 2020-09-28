package edu.upenn.cis.cis455.configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import edu.upenn.cis.cis455.exceptions.ConfigurationException;

public class ConfigBuilder {

	final static Logger logger = LogManager.getLogger(ConfigBuilder.class);
	
	private static Config _currentConfig;
	private static ConfigBuilder _configBuilder;
	
	public static ConfigBuilder getInstance() {
		if (_configBuilder == null) {
			_configBuilder = new ConfigBuilder();
		}
		return _configBuilder;
	}
	
	public Config getCurrentConfig() {
		if (_currentConfig == null) {
			throw new RuntimeException("Error getting current configuration. No configuration set.");
		}
		return _currentConfig;
	}
	
	public void setCurrentConfig(String path) throws ConfigurationException {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new ConfigurationException(e);
		}
		StringBuffer stringBuffer = new StringBuffer();
		int i;
		try {
			while((i = fileReader.read()) != -1) {
				stringBuffer.append((char)i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ConfigurationException(e);
		}
		JsonNode conf = null;
		try {
			conf = JsonUtil.parseString(stringBuffer.toString());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new ConfigurationException (e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ConfigurationException(e);
		}
		try {
			_currentConfig = JsonUtil.fromJson(conf, Config.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new ConfigurationException(e);
		}
	}
	
}
