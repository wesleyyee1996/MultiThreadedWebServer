package edu.upenn.cis.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
import edu.upenn.cis.cis455.utils.Matcher;

public class RouteMatcherTest {

	RouteMap getRouteMap = new RouteMap();
	Matcher routeMatcher = new Matcher();
	Request request = new RequestObj();
	Response response = new ResponseObj();
	Route testRoute1;
	Route testRoute2;
	Route testRoute3;
	String testPath1;
	String testPath2;
	String testPath3;
	String reqPath;
	
	
	@Before
	public void before() {
		testRoute1 = (Request request, Response response)-> {
			return "abc";};	
		testRoute2 = (Request request, Response response)-> {
			return "def";};	
		testRoute3 = (Request request, Response response)-> {
			return "ghi";};	
		request.setRequestMethod(Constants.get);
		
	}
	
	@Test
	public void test1() {
		testPath1 = "/index/:color/abc";
		testPath2 = "/index/blue/abc";
		testPath3 = "/index/red/xyz";
		reqPath = "/index/red/abc";
		request.setUri(reqPath);
		WebService webService = new WebService();
		webService.get(testPath1, testRoute1);
		webService.get(testPath2, testRoute2);
		webService.get(testPath3, testRoute3);
		Route matchedTestRoute = routeMatcher.matchRoute(request, response);
		assertTrue(matchedTestRoute == testRoute1);
	}
	
	@Test
	public void test2() {
		testPath1 = "/index/red/*";
		testPath2 = "/index/blue/abc";
		testPath3 = "/index/:color/abc";
		reqPath = "/index/red/abc";
		request.setUri(reqPath);
		WebService webService = new WebService();
		webService.get(testPath1, testRoute1);
		webService.get(testPath2, testRoute2);
		webService.get(testPath3, testRoute3);
		Route matchedTestRoute = routeMatcher.matchRoute(request, response);
		assertTrue(matchedTestRoute == testRoute1);
	}
	
	@Test
	public void test3() {
		testPath1 = "/index/:color/abc";
		testPath2 = "/index/blue/abc";
		testPath3 = "*";
		reqPath = "/index/red/abc";
		request.setUri(reqPath);
		WebService webService = new WebService();
		webService.get(testPath1, testRoute1);
		webService.get(testPath2, testRoute2);
		webService.get(testPath3, testRoute3);
		Route matchedTestRoute = routeMatcher.matchRoute(request, response);
		assertTrue(matchedTestRoute == testRoute1);
	}
	
	/**
	 * Fails when it's run w/ others but succeeds when run by itself, not sure why...
	 * @throws Exception 
	 */
	@Test
	public void test4() throws Exception {
		testPath1 = "*/*";
		testPath2 = "/index/blue/abc";
		testPath3 = "/index/:color/abc";
		reqPath = "/index/red/abc";
		request.setUri(reqPath);
		WebService webService = new WebService();
		webService.get(testPath1, testRoute1);
		webService.get(testPath2, testRoute2);
		webService.get(testPath3, testRoute3);
		Route matchedTestRoute = routeMatcher.matchRoute(request, response);
		System.out.println(matchedTestRoute.handle(request, response));
		assertTrue(matchedTestRoute == testRoute3);
	}
	
	/**
	 * Fails when it's run w/ others but succeeds when run by itself, not sure why...
	 * @throws Exception 
	 */
	@Test
	public void test5() throws Exception {
		testPath1 = "*/+/*";
		testPath2 = "/index/blue/abc";
		testPath3 = "/index/:color/abc";
		reqPath = "/index/red/abc";
		request.setUri(reqPath);
		WebService webService = new WebService();
		webService.get(testPath1, testRoute1);
		webService.get(testPath2, testRoute2);
		webService.get(testPath3, testRoute3);
		Route matchedTestRoute = routeMatcher.matchRoute(request, response);
		System.out.println(matchedTestRoute.handle(request, response));
		assertTrue(matchedTestRoute == testRoute3);
	}
	
	/**
	 * Fails when it's run w/ others but succeeds when run by itself, not sure why...
	 * @throws Exception 
	 */
	@Test
	public void test6() throws Exception {
		testPath1 = "/";
		testPath2 = "/index/blue/abc";
		testPath3 = "/index/:color/abc";
		reqPath = "/index/red/abc";
		request.setUri(reqPath);
		WebService webService = new WebService();
		webService.get(testPath1, testRoute1);
		webService.get(testPath2, testRoute2);
		webService.get(testPath3, testRoute3);
		Route matchedTestRoute = routeMatcher.matchRoute(request, response);
		System.out.println(matchedTestRoute.handle(request, response));
		assertTrue(matchedTestRoute == testRoute3);
	}

}
