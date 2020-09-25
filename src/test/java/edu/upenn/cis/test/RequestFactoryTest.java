package edu.upenn.cis.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Hashtable;

import org.junit.Test;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.interfaces.RequestFactory;
import edu.upenn.cis.cis455.m1.server.HttpTask;
import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;
import edu.upenn.cis.cis455.m1.interfaces.Request;

//import edu.upenn.cis.test.TestHelper;

public class RequestFactoryTest {
	
	String sampleGetRequest = 
	        "GET /a/b/hello.htm?q=x&v=12%200 HTTP/1.1\r\n" +
	        "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n" +
	        "Host: www.cis.upenn.edu\r\n" +
	        "Accept-Language: en-us\r\n" +
	        "Accept-Encoding: gzip, deflate\r\n" +
	        "Cookie: name1=value1;name2=value2;name3=value3\r\n" +
	        "Connection: Keep-Alive\r\n\r\n";	
	
	int port = 45555;
	String root_dir = "/.www";
	Hashtable<String,String> parsedHeaders;
	
	@Test
	public void testRequestFactory_CheckRequestNotNull() throws IOException {
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Socket s = getMockSocket(
            sampleGetRequest, 
            byteArrayOutputStream);

		HttpTask task = new HttpTask(s, port, root_dir);
		HttpIoHandler handler = new HttpIoHandler(s, task);
		
		handler.parseInputStream();
		parsedHeaders = handler.parsedHeaders;
		
		// Gets a new request from the Request Factory
		RequestFactory requestFactory = new RequestFactory();
		Request getRequest = requestFactory.getRequest(parsedHeaders, task);
		assertNotNull(getRequest);
		
		
	}
	
//	public void checkIfHeadersValid() {
//		parsedHeaders.
//		for(int i=0; i < parsedHeaders.size(); i++) {
//			
//		}
//	}
	
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
