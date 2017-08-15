/**
 * CIS 455/555 route-based HTTP framework.
 * 
 * Basic service handler and route manager.
 * 
 * Z. Ives, 8/2017
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

package edu.upenn.cis.cis455.webserver;

import edu.upenn.cis.cis455.webserver.exceptions.HaltException;
import edu.upenn.cis.cis455.webserver.stubs.Service;

public class HttpFramework {
    
    // We don't want people to use the constructor
    protected HttpFramework() {}
    
    protected static Service getInstance() {
        return HttpFactory.getServerInstance();
    }
    
    ///////////////////////////////////////////////////
    // HTTP requests
    ///////////////////////////////////////////////////
    
    /**
     * Handle an HTTP GET request to the path
     */
    public static void get(String path, Route route) {
        getInstance().get(path, route);
    }

    /**
     * Handle an HTTP POST request to the path
     */
    public static void post(String path, Route route) {
        getInstance().post(path, route);
    }

    /**
     * Handle an HTTP PUT request to the path
     */
    public static void put(String path, Route route) {
        getInstance().put(path, route);
    }

    /**
     * Handle an HTTP DELETE request to the path
     */
    public static void delete(String path, Route route) {
        getInstance().delete(path, route);
    }

    /**
     * Handle an HTTP HEAD request to the path
     */
    public static void head(String path, Route route) {
        getInstance().head(path, route);
    }

    /**
     * Handle an HTTP OPTIONS request to the path
     */
    public static void options(String path, Route route) {
        getInstance().options(path, route);
    }
    
    ///////////////////////////////////////////////////
    // HTTP request filtering
    ///////////////////////////////////////////////////
    
    /**
     * Add filters that get called before a request
     */
    public static void before(Filter... filters) {
        for (Filter filter: filters)
            getInstance().before(filter);
    }

    /**
     * Add filters that get called after a request
     */
    public static void after(Filter... filters) {
        for (Filter filter: filters)
            getInstance().after(filter);
    }

    /**
     * Add filters that get called before a request
     */
    public static void before(String path, String acceptType, Filter... filters) {
        for (Filter filter: filters)
            getInstance().before(path, acceptType, filter);
    }

    /**
     * Add filters that get called after a request
     */
    public static void after(String path, String acceptType, Filter... filters) {
        for (Filter filter: filters)
            getInstance().after(path, acceptType, filter);
    }
    
    /**
     * Triggers a HaltException that terminates the request
     */
    public static HaltException halt() {
        throw getInstance().halt();
    }

    /**
     * Triggers a HaltException that terminates the request
     */
    public static HaltException halt(int statusCode, String body) {
        throw getInstance().halt(statusCode, body);
    }

    ////////////////////////////////////////////
    // Server configuration
    ////////////////////////////////////////////
    
    /**
     * Set the IP address to listen on (default 0.0.0.0)
     */
    public static void ipAddress(String ipAddress) {
        getInstance().ipAddress(ipAddress);
    }
    
    /**
     * Set the TCP port to listen on (default 80)
     */
    public static void port(int port) {
        getInstance().port(port);
    }
    
    /**
     * Set the size of the thread pool
     */
    public static void threadPool(int threads) {
        getInstance().threadPool(threads);
    }
    
    /**
     * Set the root directory of the "static web" files
     */
    public static void staticFileLocation(String directory) {
        getInstance().staticFileLocation(directory);
    }
    
    /**
     * Hold until the server is fully initialized
     */
    public static void awaitInitialization() {
        getInstance().awaitInitialization();
    }
    
    /**
     * Gracefully shut down the server
     */
    public static void stop() {
        getInstance().stop();
    }
}
