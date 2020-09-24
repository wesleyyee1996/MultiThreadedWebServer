package edu.upenn.cis.cis455.m1.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;

import org.junit.Test;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.interfaces.RequestFactory;
import edu.upenn.cis.test.TestHelper;
import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;
import edu.upenn.cis.cis455.m1.interfaces.Request;

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
	
	@Test
	public void test() throws IOException {
		Hashtable<String,String> parsedHeaders = new Hashtable<String,String>();
		parsedHeaders.put(Constants.Method, "GET");
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Socket s = TestHelper.getMockSocket(
            sampleGetRequest, 
            byteArrayOutputStream);

		HttpTask task = new HttpTask(s, port, root_dir);
		
		RequestFactory requestFactory = new RequestFactory();
		Request getRequest = requestFactory.getRequest(parsedHeaders, task);
		assertNotNull(getRequest);
	}

}
