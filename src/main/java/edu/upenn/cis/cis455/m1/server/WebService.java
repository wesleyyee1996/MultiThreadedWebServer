/*
 * CIS 455/555 route-based HTTP framework
 * 
 * V. Liu, Z. Ives
 * 
 * Portions excerpted from or inspired by Spark Framework, 
 * 
 *                 http://sparkjava.com,
 * 
 * with license notice included below.
 */

/*
 * Copyright 2011- Per Wendel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.upenn.cis.cis455.m1.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.IOException;

import java.net.ServerSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.interfaces.Route;
import edu.upenn.cis.cis455.m1.server.*;

public class WebService {
    final static Logger logger = LogManager.getLogger(WebService.class);

    protected HttpListener listener;
    protected ThreadPool threadPool;
    protected int threadPoolSize;
    protected int port;
    protected String root_dir;
    protected String ip_address;
    
    /**
     * Launches the Web server thread pool and the listener
     * @throws IOException 
     */
    public void start() throws IOException {
    	
    	threadPool = new ThreadPool(threadPoolSize); 	
    	listener = new HttpListener(this.port, this.root_dir);
    }

    /**
     * Gracefully shut down the server
     */
    public void stop() {
    	threadPool.shutdownPool();
    }

    /**
     * Hold until the server is fully initialized
     * @throws IOException 
     */
    public void awaitInitialization() throws IOException {
        logger.info("Initializing server");
        start();
    }

    /**
     * Triggers a HaltException that terminates the request
     */
    public HaltException halt() {
        throw new HaltException();
    }

    /**
     * Triggers a HaltException that terminates the request
     */
    public HaltException halt(int statusCode) {
        throw new HaltException(statusCode);
    }

    /**
     * Triggers a HaltException that terminates the request
     */
    public HaltException halt(String body) {
        throw new HaltException(body);
    }

    /**
     * Triggers a HaltException that terminates the request
     */
    public HaltException halt(int statusCode, String body) {
        throw new HaltException(statusCode, body);
    }

    /**
     * Set the root directory of the "static web" files
     */
    public void staticFileLocation(String directory) {
    	this.root_dir = directory;
    }

    ///////////////////////////////////////////////////
    // For more advanced capabilities
    ///////////////////////////////////////////////////

    /**
     * Handle an HTTP GET request to the path
     */
    public void get(String path, Route route) {}

    ////////////////////////////////////////////
    // Server configuration
    ////////////////////////////////////////////

    /**
     * Set the IP address to listen on (default 0.0.0.0)
     */
    public void ipAddress(String ipAddress) {
    	this.ip_address = ipAddress;
    }

    /**
     * Set the TCP port to listen on (default 45555)
     */
    public void port(int port) {
    	this.port = port;
    }

    /**
     * Set the size of the thread pool
     */
    public void SetThreadPool(int threads) {
    	this.threadPoolSize = threads;
    }

}
