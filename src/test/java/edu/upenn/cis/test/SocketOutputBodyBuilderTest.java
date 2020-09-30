package edu.upenn.cis.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis.cis455.m1.interfaces.SocketOutputBodyBuilder;
import edu.upenn.cis.cis455.m1.interfaces.SuccessResponse;

public class SocketOutputBodyBuilderTest {

	SuccessResponse response;
	@Before
	public void setUp() {
		response = new SuccessResponse();
		response.status(404);
	}
	
	@Test
	public void test() {
		
		response.
		SocketOutputBodyBuilder bodyBuilder = new SocketOutputBodyBuilder();
		
		fail("Not yet implemented");
	}

}
