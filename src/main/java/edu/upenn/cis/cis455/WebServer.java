package edu.upenn.cis.cis455;

import org.apache.logging.log4j.*;

import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Route;
import edu.upenn.cis.cis455.utils.CommandLineParser;
import edu.upenn.cis.cis455.utils.CommandLineValues;

public class WebServer {
	
	static final Logger logger = LogManager.getLogger(WebServer.class);
	
    public static void main(String[] args) throws Exception {
        org.apache.logging.log4j.core.config.Configurator.setLevel("edu.upenn.cis.cis455", Level.DEBUG);
        logger.info("Starting server");
        
        
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
		
		Route test = (Request request, Response response)-> {
			System.out.println("test");
			return "test";};
		WebServiceFactory.get("/test", test);
		
		// Run web service
		WebServiceFactory.awaitInitialization();
		logger.info("Waiting to handle requests!");
        
    }

}
