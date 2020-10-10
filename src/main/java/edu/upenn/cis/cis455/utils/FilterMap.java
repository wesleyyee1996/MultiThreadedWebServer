package edu.upenn.cis.cis455.utils;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.m2.interfaces.Filter;

public class FilterMap {
	
		final static Logger logger = LogManager.getLogger(FilterMap.class);
		
		private ArrayList<Triplet<String, String, Filter>> filterMap;
		
		public FilterMap() {
			filterMap = new ArrayList<Triplet<String, String, Filter>>();
		}
		
		/**
		 * Gets the registered Filter from the inputted path
		 * @param path
		 * @return
		 */		
		public Filter get(String path, String acceptType) {
			if (!containsPathAndType(path, acceptType)) {
				for (int i = 0; i < filterMap.size() ; i++) {
					Triplet<String,String,Filter> trip = filterMap.get(i);
					if (trip.x.equals(path) && trip.y.equals(acceptType) && trip.z != null) {
						return trip.z;
					}
				}
			}
			logger.debug("The path "+path+" does not have a registered filter");
			return null;
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
		public void add(String path, String acceptType, Filter filter) {
			if (!containsPathAndType(path, acceptType)) {
				if (filter != null) {
					filterMap.add(new Triplet<String,String, Filter>(path,  acceptType,filter));
				}
				else {
					logger.debug("The provided filter is null");
				}
			}
			else {
				logger.debug("The FilterMap already contains the given path "+path +" and acceptType "+acceptType);
			}
		}
		
		/**
		 * Checks if the filterMap contains a given path as a key
		 * @param path
		 * @return
		 */
		public boolean containsPathAndType(String path,String acceptType) {
			for (int i = 0; i < filterMap.size() ; i++) {
				Triplet<String,String,Filter> trip = filterMap.get(i);
				if (trip.x != null && trip.y != null) {
					if(trip.x.equals(path) && trip.y.equals(acceptType)) {
						return true;
					}
				}
			}
			return false;
		}
		
}
