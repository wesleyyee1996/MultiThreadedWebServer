package edu.upenn.cis.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.server.RequestObj;
import edu.upenn.cis.cis455.m1.server.ResponseObj;
import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Route;
import edu.upenn.cis.cis455.m2.server.WebService;
import edu.upenn.cis.cis455.utils.RouteMap;
import edu.upenn.cis.cis455.utils.RouteMatcher;

public class RouteMatcherTest {

	RouteMap getRouteMap = new RouteMap();
	RouteMatcher routeMatcher = new RouteMatcher();
	Request request = new RequestObj();
	Response response = new ResponseObj();
	Route testRoute1;
	Route testRoute2;
	Route testRoute3;
	String testPath1 = "/index/:color/abc";
	String testPath2 = "/index/blue/abc";
	String testPath3 = "/index/red/xyz";
	String reqPath = "/index/red/abc";
	WebService webService = new WebService();
	
	@Before
	public void before() {
		testRoute1 = (Request request, Response response)-> {
			return "abc";};	
		testRoute2 = (Request request, Response response)-> {
			return "def";};	
		testRoute3 = (Request request, Response response)-> {
			return "ghi";};	
		request.setRequestMethod(Constants.get);
		request.setUri(reqPath);
		webService.get(testPath1, testRoute1);
		webService.get(testPath2, testRoute2);
		webService.get(testPath3, testRoute3);
	}
	
	@Test
	public void test() {
		Route testRoute = routeMatcher.matchRoute(request, response);
		assertTrue(testRoute == testRoute1);
	}

}
