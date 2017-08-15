/**
 * CIS 455/555 route-based HTTP framework
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
package edu.upenn.cis.cis455.webserver.stubs;

import edu.upenn.cis.cis455.webserver.Filter;
import edu.upenn.cis.cis455.webserver.Route;
import edu.upenn.cis.cis455.webserver.exceptions.HaltException;

public abstract class Service {
    
    /**
     * Launches the Web server thread pool and the listener
     */
    public abstract void start();
    
    /**
     * Handle an HTTP GET request to the path
     */
    public abstract void get(String path, Route route);

    /**
     * Handle an HTTP POST request to the path
     */
    public abstract void post(String path, Route route);

    /**
     * Handle an HTTP PUT request to the path
     */
    public abstract void put(String path, Route route);

    /**
     * Handle an HTTP DELETE request to the path
     */
    public abstract void delete(String path, Route route);

    /**
     * Handle an HTTP HEAD request to the path
     */
    public abstract void head(String path, Route route);

    /**
     * Handle an HTTP OPTIONS request to the path
     */
    public abstract void options(String path, Route route);
    
    ///////////////////////////////////////////////////
    // HTTP request filtering
    ///////////////////////////////////////////////////
    
    /**
     * Add filters that get called before a request
     */
    public abstract void before(Filter filter);

    /**
     * Add filters that get called after a request
     */
    public abstract void after(Filter filter);
    /**
     * Add filters that get called before a request
     */
    public abstract void before(String path, String acceptType, Filter filter);
    /**
     * Add filters that get called after a request
     */
    public abstract void after(String path, String acceptType, Filter filter);
    
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

    ////////////////////////////////////////////
    // Server configuration
    ////////////////////////////////////////////
    
    /**
     * Set the IP address to listen on (default 0.0.0.0)
     */
    public abstract void ipAddress(String ipAddress);
    
    /**
     * Set the TCP port to listen on (default 80)
     */
    public abstract void port(int port);
    
    /**
     * Set the size of the thread pool
     */
    public abstract void threadPool(int threads);
    
    /**
     * Set the root directory of the "static web" files
     */
    public abstract void staticFileLocation(String directory);
    
    /**
     * Hold until the server is fully initialized
     */
    public abstract void awaitInitialization();
    
    /**
     * Gracefully shut down the server
     */
    public abstract void stop();
}
