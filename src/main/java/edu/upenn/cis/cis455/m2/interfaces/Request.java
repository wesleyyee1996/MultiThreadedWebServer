package edu.upenn.cis.cis455.m2.interfaces;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.server.RequestObj;
import edu.upenn.cis.cis455.m2.server.WebService;

/**
 * Expanded version of the request API for Milestone 2
 */
public abstract class Request extends edu.upenn.cis.cis455.m1.interfaces.Request {
	static final Logger logger = LogManager.getLogger(Request.class);
    /**
     * @return Gets the session associated with this request
     */
    public abstract Session session();

    /**
     * @return Gets or creates a session for this request
     */
    public abstract Session session(boolean create);

    /**
     * @return a map containing the route parameters
     */
    public abstract Map<String, String> params();

    /**
     * @return the named parameter Example: parameter 'name' from the following
     *         pattern: (get '/hello/:name')
     */
    public String params(String param) {
        if (param == null)
            return null;

        if (param.startsWith(":"))
            return params().get(param.toLowerCase());
        else
            return params().get(':' + param.toLowerCase());
    }
    
    public abstract void setQueryParams(Hashtable<String, List<String>> queryParams);

    /**
     * @return Query parameter from the URL
     */
    public abstract String queryParams(String param);

    public String queryParamOrDefault(String param, String def) {
        String ret = queryParams(param);

        return (ret == null) ? def : ret;
    }

    /**
     * @return Get the list of values for the query parameter
     */
    public abstract List<String> queryParamsValues(String param);

    public abstract Set<String> queryParams();

    /**
     * @return The raw query string
     */
    public abstract String queryString();

    /**
     * Add an attribute to the request (eg in a filter)
     */
    public abstract void attribute(String attrib, Object val);

    /**
     * @return Gets an attribute attached to the request
     */
    public abstract Object attribute(String attrib);

    /**
     * @return All attributes attached to the request
     */
    public abstract Set<String> attributes();

    public abstract Map<String, String> cookies();

    public String cookie(String name) {
        if (name == null || cookies() == null)
            return null;
        else
            return cookies().get(name);
    }
    
    /**
	 * Parses the cookie string from the Cookie: header
	 * and returns the parse cookies
	 * @param cookieString
	 * @return
	 */
	public Hashtable<String, String> parseCookieString(String cookieString){
		Hashtable<String,String> cookies = new Hashtable<String,String>();
		
		// split cookie components by ;
		String[] cookieComponents = cookieString.split(";");
				
		// then handle the attributes
		for (int i = 0; i < cookieComponents.length; i++) {
			String[] cookieComponent = cookieComponents[i].split("=");	
			
			// check to make sure the component is valid
			if (cookieComponent.length != 2) {
				logger.error("Bad cookie header");
				HaltException he = WebService.getInstance().halt(400);
				throw he;
			}
						
			cookies.put(cookieComponent[0].trim(), cookieComponent[1].trim());
		}
		return cookies;
	}

}
