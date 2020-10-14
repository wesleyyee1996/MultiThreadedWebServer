package edu.upenn.cis.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis.cis455.m1.server.ResponseObj;
import edu.upenn.cis.cis455.utils.SocketOutputBodyBuilder;

public class SocketOutputBodyBuilderTest {

	ResponseObj response;
	@Before
	public void setUp() {
		response = new ResponseObj();
		response.status(404);
	}
//	
//	@Test
//	public void test() {
//		
//		response.
//		SocketOutputBodyBuilder bodyBuilder = new SocketOutputBodyBuilder();
//		
//		fail("Not yet implemented");
//	}

}
