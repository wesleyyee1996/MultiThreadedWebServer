package edu.upenn.cis.cis455;

import static edu.upenn.cis.cis455.m2.server.WebServiceController.*;

import org.apache.logging.log4j.Level;

public class WebServer {
    public static void main(String[] args) {
        org.apache.logging.log4j.core.config.Configurator.setLevel("edu.upenn.cis.cis455", Level.DEBUG);
        
        // TODO: make sure you parse *BOTH* command line arguments properly
        
        if (args.length == 0)
            staticFileLocation("./www");
        else
            staticFileLocation(args[0]);

        // Register a test handler (should only work in Milestone 2)            
        get("/test", (request, result) -> "Hello 555");
        
        awaitInitialization();
        
        System.out.println("Waiting to handle requests!");
    }

}
