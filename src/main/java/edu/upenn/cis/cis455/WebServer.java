package edu.upenn.cis.cis455;

import static edu.upenn.cis.cis455.WebServiceFactory.*;

import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.*;

import edu.upenn.cis.cis455.configuration.Config;
import edu.upenn.cis.cis455.configuration.ConfigBuilder;
import edu.upenn.cis.cis455.exceptions.ConfigurationException;
import edu.upenn.cis.cis455.m1.server.CommandLineParser;
import edu.upenn.cis.cis455.m1.server.CommandLineValues;
import edu.upenn.cis.cis455.m1.server.WebService;

public class WebServer {
	
	static final Logger logger = LogManager.getLogger(WebServer.class);
	
    public static void main(String[] args) throws Exception {
        org.apache.logging.log4j.core.config.Configurator.setLevel("edu.upenn.cis.cis455", Level.DEBUG);
        logger.info("Starting server");
        
//        Config conf = new Config();
//        try {
//        	ConfigBuilder.getInstance().setCurrentConfig("src/main/java/edu/upenn/cis/cis455/configuration/config.json");
//			conf = ConfigBuilder.getInstance().getCurrentConfig();
//		} catch (ConfigurationException e) {
//			// TODO Auto-generated catch block
//			logger.error("Error configuring server", e);
//		}
        
        // Parse command line inputs. If inputs are bad, then shut down the server.
        CommandLineParser commandLineParse = new CommandLineParser();
        commandLineParse.validateInput(args, CommandLineValues.getInstance());
        if (!CommandLineValues.getInstance().getValuesOk()) {
        	logger.error(CommandLineValues.getInstance().getErrorMessage());
        	System.exit(0);
        }
        
        // Create and configure a WebService
		WebServiceFactory.createWebService();
		WebServiceFactory.port(CommandLineValues.getInstance().getPort());
		WebServiceFactory.staticFileLocation(CommandLineValues.getInstance().getRootDir());
		WebServiceFactory.ipAddress(null);
		
		// Run web service
		WebServiceFactory.awaitInitialization();
		logger.info("Waiting to handle requests!");
        
    }

}
