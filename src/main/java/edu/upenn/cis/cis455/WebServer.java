package edu.upenn.cis.cis455;

import static edu.upenn.cis.cis455.WebServiceFactory.*;

import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.*;

import edu.upenn.cis.cis455.m2.server.WebService;

public class WebServer {
	
	static final Logger logger = LogManager.getLogger(WebServer.class);
	
    public static void main(String[] args) throws IOException {
        org.apache.logging.log4j.core.config.Configurator.setLevel("edu.upenn.cis.cis455", Level.DEBUG);
        logger.info("Starting server");

        Integer port = 45555;
        String root_dir = "./www";
        
        if (args.length > 2) {
        	logger.error("Too many arguments");
        	System.exit(1);
        }
        else if (args.length == 1) {
        	try{
        		port = Integer.parseInt(args[0]);
        	}
        	catch(UnsupportedOperationException ex){
        		logger.error("Please enter a port number for the first argument");
        		System.exit(1);
        	}
        }
        else {
        	port = Integer.parseInt(args[0]);
        	root_dir = args[1];
        }
    	// TODO: make sure you parse *BOTH* command line arguments properly
    	//port(port);
    	//staticFileLocation(root_dir);
                        
            // All user routes should go below here...

            // ... and above here. Leave this comment for the Spark comparator tool

        
        //System.out.println(port);
        //System.out.println(root_dir);       
        
        WebService webService = new WebService();
        webService.port(port);
        webService.staticFileLocation(root_dir);
        webService.start();
        
        //awaitInitialization();
        logger.info("Waiting to handle requests!");
        
    }

}
