package edu.upenn.cis.cis455.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.m2.interfaces.Route;

/**
 * Need to create our own class RouteMap which maps paths to routes. Hashtable cannot do this due to wildcards.
 * @author vagrant
 *
 */
public class RouteMap {
	final static Logger logger = LogManager.getLogger(RouteMap.class);
	
	private ArrayList<Tuple<Path, Route>> routeMap;
	
	public RouteMap() {
		routeMap = new ArrayList<Tuple<Path, Route>>();
	}
	
	public ArrayList<Tuple<Path, Route>> getRouteMap(){
		return this.routeMap;
	}
		
	/**
	 * Adds a path, route pair to the RouteMap provided that the path doesn't already exist
	 * @param path
	 * @param route
	 */
	public void add(String path, Route route) {
		Path pathPath = Paths.get(path);
		if (!containsPath(pathPath)) {
			if (route != null) {
				routeMap.add(new Tuple<Path,Route>(pathPath, route));
			}
			else {
				logger.debug("The provided route is null");
			}
		}
		else {
			logger.debug("The RouteMap already contains the given path "+path);
		}
	}
	
	/**
	 * Checks if the routeMap contains a given path as a key
	 * @param path
	 * @return
	 */
	private boolean containsPath(Path path) {
		for (int i = 0; i < routeMap.size() ; i++) {
			Tuple<Path,Route> pathRoute = routeMap.get(i);
			if (pathRoute.x != null) {
				if(pathRoute.x.equals(path.toString())) {
					return true;
				}
			}
		}
		return false;
	}
	
	
}
