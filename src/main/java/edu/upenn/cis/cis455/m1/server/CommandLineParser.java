package edu.upenn.cis.cis455.m1.server;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineParser {

    final static Logger logger = LogManager.getLogger(CommandLineParser.class);
    private static CommandLineValues _commandLineValues;

    /**
     * @param args
     * @return
     */
    public void validateInput(String[] args, CommandLineValues commandLineValues) {
    	_commandLineValues = commandLineValues;
    	int argsLength = args.length;
    	switch (argsLength) {
    		case 0: 
    			logger.info("No input parameters. Defaulting port to 45555 and root_dir to ./www");
    			commandLineValues.setValuesOk(true);
    			break;
    		case 1:
				boolean isArgPort;
			
				isArgPort = parseArgIsPort(args[0]);
			
				// check if argument is a port
				if (isArgPort && _commandLineValues.getValuesOk()) {
					boolean isPortValid = validatePort(_commandLineValues.getPort());
					if (isPortValid) {
						_commandLineValues.setValuesOk(true);
						break;
					}
					if (!isPortValid) {
						_commandLineValues.setValuesOk(false);
						_commandLineValues.setErrorMessage("Inputted port "+_commandLineValues.getPort()+" is not valid. Shutting down server.");
						break;
					}
				}
				
				// if the argument is not a port, then it must be a root directory
				if (isArgPort == false && _commandLineValues.getValuesOk() == true) {
					boolean isRootDirValid = validateRootDir(_commandLineValues.getRootDir());
					if (isRootDirValid) {
						_commandLineValues.setValuesOk(true);
						break;
					}
					if (!isRootDirValid) {
						_commandLineValues.setErrorMessage("Inputted root directory "+args[0]+" is not valid or does not exist. Shutting down server.");
						_commandLineValues.setValuesOk(false);
						break;
					}
				}
				break;
				
    		case 2:
    			// Validate 1st arg. This should always be the port
    			if (!parseArgIsPort(args[0])) {
    				_commandLineValues.setErrorMessage("1st argument should always be a port. Shutting down server.");
    				_commandLineValues.setValuesOk(false);
    				break;
    			}
    			
    			// Validate 2nd arg. This should always be the root_dir
    			if (parseArgIsPort(args[1]) == false && _commandLineValues.getValuesOk()==true) {
    				boolean isRootDirValid = validateRootDir(_commandLineValues.getRootDir());
    				if (isRootDirValid) {
    					_commandLineValues.setValuesOk(true);
    					break;
    				}
    				if (!isRootDirValid) {
    					_commandLineValues.setErrorMessage("Inputted root directory "+args[1]+" is not valid or does not exist. Shutting down server.");
        				_commandLineValues.setValuesOk(false);
        				break;
    				}
    			}
    			if (parseArgIsPort(args[1])) {
    				_commandLineValues.setErrorMessage("2nd argument should always be a root directory. Shutting down server.");
    				_commandLineValues.setValuesOk(false);
    				break;
    			}
    			break;
			default:
				// There are too many arguments if it has made it here
				_commandLineValues.setErrorMessage("Too many inputted arguments. Shutting down server.");
				_commandLineValues.setValuesOk(false);
				break;
    	}
    }
	
	/**
	 * Checks to see if an argument is a port or a path. If neither, then close server
	 * @param arg
	 * @return boolean
	 */
	private boolean parseArgIsPort(String arg){
		try {
			int port = Integer.parseInt(arg);
			_commandLineValues.setPort(port);
			return true;
		}
		catch (NumberFormatException nfe){
			//logger.debug("Argument "+arg+" is not a port or is too large.");
		}
		Pattern pattern = Pattern.compile("/");
		Matcher matcher = pattern.matcher(arg);
		if (matcher.find()) {
			_commandLineValues.setRootDir(arg);;
			return false;
		}
		_commandLineValues.setErrorMessage("Argument "+arg+" is invalid. Shutting down server.");
		_commandLineValues.setValuesOk(false);
		return false;
	}
	
	/**
	 * Validates to see if port is between (1,60999) and not 80
	 * @param port
	 * @return
	 */
	private boolean validatePort(int port) {
		if (port >= 1 && port <= 60999 && port != 80) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks that inputted root directory is actually a valid filepath
	 * @param root_dir
	 * @return
	 */
	private boolean validateRootDir(String root_dir) {
		
		// First check if the file or directory exists
		File rootDir = new File(root_dir);
		if (!rootDir.exists()) {
			return false;
		}
		
		// If the inserted root directory contains a file, handle it
		Path path = Paths.get(root_dir);
		Path filename = path.getFileName();
		
		// This means that there is a filename in the root dir, then throw an error
		if (filename != null) {
			return false;
		}
		
		return true;
	}
}
