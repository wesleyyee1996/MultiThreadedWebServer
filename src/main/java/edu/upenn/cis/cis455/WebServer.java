package edu.upenn.cis.cis455;

import org.apache.logging.log4j.Level;

public class WebServer {
	public static void main(String[] args) {
		org.apache.logging.log4j.core.config.Configurator.setLevel("edu.upenn.cis.cis455", Level.DEBUG);

		// TODO: make sure you parse *BOTH* command line arguments properly

		// TODO: launch your server daemon
		

		// All user routes should go below here...

		// ... and above here. Leave this comment for the Spark comparator tool

		System.out.println("Waiting to handle requests!");
	}

}
