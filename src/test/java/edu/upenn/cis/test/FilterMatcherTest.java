package edu.upenn.cis.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.m1.server.RequestObj;
import edu.upenn.cis.cis455.m1.server.ResponseObj;
import edu.upenn.cis.cis455.m2.interfaces.Filter;
import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Route;
import edu.upenn.cis.cis455.m2.server.WebService;
import edu.upenn.cis.cis455.utils.FilterMap;
import edu.upenn.cis.cis455.utils.Matcher;
import edu.upenn.cis.cis455.utils.RouteMap;

public class FilterMatcherTest {

	FilterMap getRouteMap = new FilterMap();
	Matcher filterMatcher = new Matcher();
	Request request = new RequestObj();
	Response response = new ResponseObj();
	Filter testFilter1;
	Filter testFilter2;
	Filter testFilter3;
	String testPath1;
	String testPath2;
	String testPath3;
	String reqPath;
	
	
	@Before
	public void before() {
		testFilter1 = (Request request, Response response)-> {
			System.out.println("testfilter1");};	
			testFilter2 = (Request request, Response response)-> {
			System.out.println("testfilter2");};	
			testFilter3 = (Request request, Response response)-> {
			System.out.println("testfilter3");};	
		request.setRequestMethod(Constants.get);
		
	}
	
	@Test
	public void test1() {
		testPath1 = "/index/:color/abc";
		testPath2 = "/index/blue/abc";
		testPath3 = "/index/red/abc";
		reqPath = "/index/red/abc";
		request.setUri(reqPath);
		WebService webService = new WebService();
		webService.before(testPath1, "*", testFilter1);
		webService.before(testPath2, "*", testFilter2);
		webService.before(testPath3, "*", testFilter3);
		ArrayList<Filter> matchedFilters = filterMatcher.matchFilter(request, response, true);
		assertTrue(matchedFilters.contains(testFilter1));
		assertTrue(matchedFilters.contains(testFilter3));
	}
	
	@Test
	public void test2() {
		testPath1 = "/index/*/abc";
		testPath2 = "/index/blue/abc";
		testPath3 = "/index/red/abc";
		reqPath = "/index/red/abc";
		request.setUri(reqPath);
		WebService webService = new WebService();
		webService.before(testPath1, "*", testFilter1);
		webService.before(testPath2, "*", testFilter2);
		webService.before(testPath3, "*", testFilter3);
		ArrayList<Filter> matchedFilters = filterMatcher.matchFilter(request, response, true);
		assertTrue(!matchedFilters.contains(testFilter1));
		assertTrue(!matchedFilters.contains(testFilter2));
		assertTrue(matchedFilters.contains(testFilter3));
	}

}
