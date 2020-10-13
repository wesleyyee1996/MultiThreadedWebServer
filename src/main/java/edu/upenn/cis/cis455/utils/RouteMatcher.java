package edu.upenn.cis.cis455.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Route;
import edu.upenn.cis.cis455.m2.server.WebService;

public class RouteMatcher {

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
			
			// only compare the 2 paths if the lengths of the 2 paths match
			if (registeredPath.getNameCount() == requestPath.getNameCount()) {
				
				// loop through the requestPath and compare each of the registered path 
				// components to the requestPath components
				for (int pathIdx = 0; pathIdx < registeredPath.getNameCount(); i++) {
					
					// if the register path component and request path component match, then continue comparing
					if (requestPath.getName(pathIdx).equals(registeredPath.getName(pathIdx))) {
						if (pathIdx == requestPath.getNameCount()-1) {
							return pathRoute.y;
						}
						pathIdx++;
						continue;
					}
					// if the registered path component is a path parameter
					else if (registeredPath.getName(pathIdx).toString().startsWith(":")) {
						if (pathIdx == requestPath.getNameCount()-1) {
							return pathRoute.y;
						}
						pathIdx++;
						continue;
					}
					// handles case if registered path is a wildcard
					else if (registeredPath.getName(pathIdx).toString().equals("*")) {
						if (pathIdx == requestPath.getNameCount()-1) {
							return pathRoute.y;
						}
						pathIdx++;
						continue;
					}
					// if registered path doesn't meet any of the criteria, then go to next one
					else {
						break;
					}	
				}
			}		
		}		
		
		// if we made it here, then we didn't find any paths that matched
		return null;
		
	}
	
}
