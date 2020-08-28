package edu.upenn.cis.cis455;

import static edu.upenn.cis.cis455.WebServiceFactory.*;

import org.apache.logging.log4j.Level;

public class WebServer {
	public static void main(String[] args) {
		org.apache.logging.log4j.core.config.Configurator.setLevel("edu.upenn.cis.cis455", Level.DEBUG);

		// TODO: make sure you parse *BOTH* command line arguments properly

		// TODO: add routes, etc.

		System.out.println("Waiting to handle requests!");
		awaitInitialization();
	}

}
