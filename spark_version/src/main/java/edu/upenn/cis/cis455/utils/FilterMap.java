package edu.upenn.cis.cis455.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.m2.interfaces.Filter;

public class FilterMap {
	
		final static Logger logger = LogManager.getLogger(FilterMap.class);
		
		private ArrayList<Triplet<Path, String, Filter>> filterMap;
		
		public FilterMap() {
			filterMap = new ArrayList<Triplet<Path, String, Filter>>();
		}
			
		/**
		 * Adds a filter to the FilterMap provided and sets the path and acceptType to "*"
		 * Adds a path
		 * @param filter
		 */
		public void add(Filter filter) {
			String path = "*";
			String acceptType = "*";
			add(path, acceptType, filter);
		}
		
		/**
		 * Adds a path, acceptType, filter triplet to the FilterMap provided that the path and acceptType doesn't already exist
		 * @param path
		 * @param route
		 */
		public void add(String pathString, String acceptType, Filter filter) {
			Path path = Paths.get(pathString);
			if (!containsPathAndType(path, acceptType)) {
				if (filter != null) {
					filterMap.add(new Triplet<Path,String, Filter>(path,  acceptType,filter));
				}
				else {
					logger.info("The provided filter is null");
				}
			}
			else {
				logger.info("The FilterMap already contains the given path "+path +" and acceptType "+acceptType);
			}
		}
		
		/**
		 * Checks if the filterMap contains a given path as a key
		 * @param path
		 * @return
		 */
		private boolean containsPathAndType(Path path,String acceptType) {
			for (int i = 0; i < filterMap.size() ; i++) {
				Triplet<Path,String,Filter> trip = filterMap.get(i);
				if (trip.x != null && trip.y != null) {
					if(trip.x.toString().equals(path) && trip.y.equals(acceptType)) {
						return true;
					}
				}
			}
			return false;
		}
		
		public ArrayList<Triplet<Path, String, Filter>> getFilterMap(){
			return this.filterMap;
		}
		
}
