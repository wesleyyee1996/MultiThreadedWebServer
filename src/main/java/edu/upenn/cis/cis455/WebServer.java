package edu.upenn.cis.cis455;

import static edu.upenn.cis.cis455.WebServiceFactory.*;
import java.util.Optional;

import org.apache.logging.log4j.Level;

public class WebServer {
    public static void main(String[] args) {
        org.apache.logging.log4j.core.config.Configurator.setLevel("edu.upenn.cis.cis455", Level.DEBUG);

        Integer port = 45555;
        String root_dir = "./www";
        
        if (args.length > 2) {
        	System.out.println("Too many arguments");
        	System.exit(1);
        }
        else if (args.length == 1) {
        	try{
        		port = Integer.parseInt(args[0]);
        	}
        	catch(UnsupportedOperationException ex){
        		System.out.println("Please enter a port number for the first argument");
        		System.exit(1);
        	}
        }
        else {
        	port = Integer.parseInt(args[0]);
        	root_dir = args[1];
        }
    	// TODO: make sure you parse *BOTH* command line arguments properly
    	port(port);
    	staticFileLocation(root_dir);
                        
            // All user routes should go below here...

            // ... and above here. Leave this comment for the Spark comparator tool

        
        System.out.println(port);
        System.out.println(root_dir);
        
        System.out.println("Waiting to handle requests!");
        //awaitInitialization();
    }

}
