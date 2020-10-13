package edu.upenn.cis.test;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Route;
import edu.upenn.cis.cis455.utils.RouteMap;
import edu.upenn.cis.cis455.utils.Tuple;

public class RouteMapTest {

	Route testRoute;
	
	@Before
	public void before() {
		testRoute = (Request request, Response response)-> {
			return "abc";};		
	}
	
	@Test
	public void test() {
		RouteMap routeMap = new RouteMap();
		routeMap.add("/index/test", testRoute);
		ArrayList<Tuple<Path, Route>> test = routeMap.getRouteMap();
		assertTrue(test.get(0).x.toString().equals("/index/test"));
		assertTrue(test.get(0).y == testRoute);
	}

}
