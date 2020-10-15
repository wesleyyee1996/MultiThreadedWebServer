package edu.upenn.cis.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis.cis455.WebServiceFactory;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;
import edu.upenn.cis.cis455.m1.server.HttpTask;
import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Session;
import edu.upenn.cis.cis455.utils.CommandLineValues;

public class PathParametersTest {

	@Before
    public void setUp() {
        org.apache.logging.log4j.core.config.Configurator.setLevel("edu.upenn.cis.cis455", Level.DEBUG);
        
        // Create and configure a WebService
     		WebServiceFactory.createWebService();
     		WebServiceFactory.port(CommandLineValues.getInstance().getPort());
     		WebServiceFactory.staticFileLocation(CommandLineValues.getInstance().getRootDir());
     		WebServiceFactory.ipAddress(null);
     		
     		WebServiceFactory.get("/test/:color/abc", (Request request, Response response) -> {
     				System.out.println("matched route");
     				Map<String,String> params = request.params();
     				return params.get(":color");});
     		// Run web service
     		WebServiceFactory.awaitInitialization();
    }
	
    
    String sampleGetRequest = 
        "GET /test/red/abc HTTP/1.1\r\n" +
        "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n" +
        "Host: www.cis.upenn.edu\r\n" +
        "Accept-Language: en-us\r\n" +
        "Accept-Encoding: gzip, deflate\r\n" +
        "Cookie: JSESSIONID=123456789" +"\r\n"+
        "Connection: Keep-Alive\r\n\r\n";
    
    
	
	@Test
	public void test() throws HaltException, Exception {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Socket s = getMockSocket(
            sampleGetRequest, 
            byteArrayOutputStream);
        
        HttpTask task = new HttpTask(s, 45555, "./www");
		
		HttpIoHandler httpIoHandler = new HttpIoHandler(s, task);
		httpIoHandler.handleRequest();
		
		String result = byteArrayOutputStream.toString("UTF-8").replace("\r", "");
        assertTrue(result.endsWith("red"));
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
