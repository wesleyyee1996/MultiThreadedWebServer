package edu.upenn.cis.cis455.webserver;

import edu.upenn.cis.cis455.webserver.implementations.Cis455Service;
import edu.upenn.cis.cis455.webserver.stubs.Service;

public class HttpFactory {
    /**
     * Initializes a singleton service
     */
    private static class ServiceSingleton {
        private static final Service INSTANCE = new Cis455Service();
    }
    
    public static Service getServerInstance() {
        return ServiceSingleton.INSTANCE;
    }
    
}
