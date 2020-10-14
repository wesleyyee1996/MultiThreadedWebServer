package edu.upenn.cis.test;

import static edu.upenn.cis.cis455.WebServiceFactory.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.logging.log4j.*;

import edu.upenn.cis.cis455.WebServer;
import edu.upenn.cis.cis455.WebServiceFactory;
import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Route;
import edu.upenn.cis.cis455.utils.CommandLineParser;
import edu.upenn.cis.cis455.utils.CommandLineValues;

public class SampleWebApp {
	static final Logger logger = LogManager.getLogger(SampleWebApp.class);
	
    public static void main() {
                
        
        // TODO: Uncomment this for milestone 2: it will be a GET handler to a simple lambda function
        // get("index.html", (request, response) -> "Hello World");
    }
    
    public static Socket getMockSocket(String socketContent, ByteArrayOutputStream output) throws IOException {
        Socket s = mock(Socket.class);
        byte[] arr = socketContent.getBytes();
        final ByteArrayInputStream bis = new ByteArrayInputStream(arr);

        when(s.getInputStream()).thenReturn(bis);
        when(s.getOutputStream()).thenReturn(output);
        when(s.getLocalAddress()).thenReturn(InetAddress.getLocalHost());
        when(s.getRemoteSocketAddress()).thenReturn(InetSocketAddress.createUnresolved("host", 8080));
        
        return s;
    }
}
