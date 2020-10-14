package edu.upenn.cis.cis455.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Filter;
import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Route;
import edu.upenn.cis.cis455.m2.server.WebService;

public class Matcher {

	/**
	 * Finds a route that matches with given request's path and method, if it exists.
	 * Otherwise, return null.
	 * @param request
	 * @param response
	 * @return
	 * @throws HaltException
	 */
	public Route matchRoute(Request request, Response response) throws HaltException {
		
		// get the path and requestType from the request
		Path requestPath = Paths.get(request.uri());
		String requestType = request.requestMethod();
		
		RouteMap routeMap = new RouteMap();
		
		// based on requestType, look in the corresponding RouteMap in WebService
		switch(requestType) {
			case Constants.get:
				routeMap = WebService.getRouteMap;
				break;
			case Constants.head:
				routeMap = WebService.headRouteMap;
				break;
			case Constants.post:
				routeMap = WebService.postRouteMap;
				break;
			case Constants.put:
				routeMap = WebService.putRouteMap;
				break;
			case Constants.delete:
				routeMap = WebService.deleteRouteMap;
				break;
			case Constants.options:
				routeMap = WebService.optionsRouteMap;
				break;
			default:
				WebService.getInstance().halt(500);	
				break;
		}		
		
		// loop through RouteMap's entries
		for (int i=0;i<routeMap.getRouteMap().size();routeMap.getRouteMap()) {
			Tuple<Path, Route> pathRoute = routeMap.getRouteMap().get(i);
			Path registeredPath = pathRoute.x;
			
			// if the registered path is just *
			if (registeredPath.toString().equals("*")){
				return pathRoute.y;
			}
			
			// only compare the 2 paths if the lengths of the 2 paths match
			// or if the registered path ends with *
			if (registeredPath.getNameCount() == requestPath.getNameCount()
					|| registeredPath.endsWith("*")) {
				
				// loop through the requestPath and compare each of the registered path 
				// components to the requestPath components
				for (int pathIdx = 0; pathIdx < registeredPath.getNameCount(); i++) {
					
					// if the register path component and request path component match 
					// or the registered path component is a path parameter
					// then continue comparing
					if (requestPath.getName(pathIdx).equals(registeredPath.getName(pathIdx))
							|| registeredPath.getName(pathIdx).toString().startsWith(":")
							|| registeredPath.getName(pathIdx).toString().equals("*")) {
						
						// check to see if at the end of the request path
						// or if at end of registered path (which is a *)
						if (pathIdx == requestPath.getNameCount()-1
								|| registeredPath.getName(pathIdx).toString().equals("*")) {
							
							// if so, then add the route to list of matched routes
							return pathRoute.y;
						}
						pathIdx++;
						continue;
					}
					// if registered path doesn't meet any of the criteria, then go to next one
					else {
						i++;
						break;
					}	
				}
			}
			else {
				i++;
			}
		}		
		return null;		
	}
	
	/**
	 * Finds a filter that matches with the given request's path and content type, if it exists. Otherwise,
	 * returns null
	 * @param request
	 * @param response
	 * @param isBeforeFilter
	 * @return
	 * @throws HaltException
	 */
	public ArrayList<Filter> matchFilter(Request request, Response response, boolean isBeforeFilter) throws HaltException {

		// get the path and contentType from the request
		Path requestPath = Paths.get(request.uri());
		String contentType = request.contentType();
		
		FilterMap filterMap = new FilterMap();
		
		ArrayList<Filter> matchedFilters = new ArrayList<Filter>();;
		
		// based on whether looking for before or after, use the corresponding FilterMap in WebService
		if (isBeforeFilter) {
			filterMap = WebService.beforeFilterMap;
		}
		else {
			filterMap = WebService.afterFilterMap;
		}
		
		// loop through FilterMap's entries
		for (int i=0;i<filterMap.getFilterMap().size();filterMap.getFilterMap()) {
			Triplet<Path, String, Filter> pathFilter = filterMap.getFilterMap().get(i);
			Path registeredPath = pathFilter.x;
			
			// if the registered path is just *
			if (registeredPath.toString().equals("*")){
				matchedFilters.add(pathFilter.z);
				i++;
				break;
			}
			
			// only compare the 2 paths if the lengths of the 2 paths match
			// or if the registered path ends with a *
			if (registeredPath.getNameCount() == requestPath.getNameCount() 
					|| registeredPath.endsWith("*")) {				
				
				// loop through the requestPath and compare each of the registered path 
				// components to the requestPath components
				for (int pathIdx = 0; pathIdx < registeredPath.getNameCount(); i++) {
					
					// check the registered path's content type
					if (registeredPath.getName(pathIdx).toString().equals("*")
							|| registeredPath.getName(pathIdx).toString().equals(contentType)){
						
						// if the register path component and request path component match 
						// or the registered path component is a path parameter						
						// then continue comparing
						if (requestPath.getName(pathIdx).equals(registeredPath.getName(pathIdx)) 
								|| registeredPath.getName(pathIdx).toString().startsWith(":")
								|| registeredPath.getName(pathIdx).toString().equals("*")) {
							
							// check to see if at end of request path
							// or end of registered path (if ends w/ *)
							if (pathIdx == (requestPath.getNameCount()-1)
									|| registeredPath.getName(pathIdx).toString().equals("*")) {
								
								// if we are, then add to list of matched filters
								matchedFilters.add(pathFilter.z);
								i++;
								break;
							}
							pathIdx++;
							continue;
						}
						// if registered path doesn't meet any of the criteria, then go to next one
						else {
							i++;
							break;
						}	
					}
					else {
						i++;
						break;
					}
					
				}
			}
			else {
				i++;
			}
		}		
		
		return matchedFilters;
	}
	
	
}
