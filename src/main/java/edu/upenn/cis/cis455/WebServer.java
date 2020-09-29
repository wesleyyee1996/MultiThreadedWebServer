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
import edu.upenn.cis.cis455.m2.server.WebService;

public class WebServer {
	
	static final Logger logger = LogManager.getLogger(WebServer.class);
	
    public static void main(String[] args) throws IOException {
        org.apache.logging.log4j.core.config.Configurator.setLevel("edu.upenn.cis.cis455", Level.DEBUG);
        logger.info("Starting server");
        
        Config conf = new Config();
        try {
        	ConfigBuilder.getInstance().setCurrentConfig("src/main/java/edu/upenn/cis/cis455/configuration/config.json");
			conf = ConfigBuilder.getInstance().getCurrentConfig();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			logger.error("Error configuring server", e);
		}
        
        if (args.length > 2) {
        	logger.error("Too many arguments");
        	System.exit(1);
        }
        else if (args.length == 1) {
        	try{
        		conf.setPort(Integer.parseInt(args[0]));
        	}
        	catch(UnsupportedOperationException ex){
        		logger.error("Please enter a port number for the first argument");
        		System.exit(1);
        	}
        }
        conf.setPort(Integer.parseInt(args[0]));
        conf.setRootDir(args[1]);
    	// TODO: make sure you parse *BOTH* command line arguments properly
    	//port(port);
    	//staticFileLocation(root_dir);
                        
            // All user routes should go below here...

            // ... and above here. Leave this comment for the Spark comparator tool

        
        //System.out.println(port);
        //System.out.println(root_dir);     
        CommandLineParser commandLineParse = new CommandLineParser();
        commandLineParse.validateInput(args, CommandLineValues.getInstance());
        
        WebServiceFactory webServiceFactory = new WebServiceFactory();
        try {
			webServiceFactory.CreateWebService(conf.getPort(), conf.getRootDir());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        WebService webService = new WebService();
        webService.port(conf.getPort());
        webService.staticFileLocation(conf.getRootDir());
        webService.start();
        
        //awaitInitialization();
        logger.info("Waiting to handle requests!");
        
    }

}
