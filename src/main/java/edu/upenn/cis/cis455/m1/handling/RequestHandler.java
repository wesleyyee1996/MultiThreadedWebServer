package edu.upenn.cis.cis455.m1.handling;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m2.interfaces.Filter;
import edu.upenn.cis.cis455.m2.interfaces.Request;
import edu.upenn.cis.cis455.m2.interfaces.Response;
import edu.upenn.cis.cis455.m2.interfaces.Route;
import edu.upenn.cis.cis455.m1.server.ControlPanel;
import edu.upenn.cis.cis455.m2.server.WebService;
import edu.upenn.cis.cis455.utils.Matcher;

public class RequestHandler{
	final static Logger logger = LogManager.getLogger(RequestHandler.class);
	
	private Request request;
	private Response response;
	private Socket socket;
	private String responseBody;
	private File file;
	private int route;
		
    public RequestHandler(Request request, Response response, Socket socket) {
    	this.request = request;
    	this.response = response;
    	this.socket = socket;
    }
	
    /**
     * Reads from the socket and adds data to the response
     * @param request
     * @param response
     * @param socket
     * @return
     * @throws HaltException
     * @throws IOException
     */
    public void handleRequest() throws HaltException, IOException, Exception {    	
    	this.response.setProtocol(request.protocol());
    	this.response.setMethod(request.requestMethod());
    	this.response.addToHeaders("Server","CIS555/1.00");

    	handleFunction();
    }
    
    /** 
     * Decides which method to call (either shutdown, show control panel, or applies any matching routes/filters)
     * If it can't match to a route, then it tries to get the file from static directory
     * @param request
     * @return
     * @throws Exception 
     */
    private void handleFunction() throws Exception, HaltException {
    	
    	// first check to see if uri is calling shutdown method
    	if (request.uri().equals("/shutdown") && request.requestMethod().equals(Constants.get)) {
    		WebService.getInstance().setSocket(this.socket);
    		WebService.getInstance().stop();
    	}
    	
    	// then check to see if uri is calling control
    	else if (request.uri().equals("/control")) {
    		ControlPanel controlPanel = new ControlPanel();
        	byte[] controlPanelBytes = controlPanel.getControlPanel(WebService.getInstance().getThreadPool());
        	response.bodyRaw(controlPanelBytes);
        	response.addToHeaders("Content-Length", Integer.toString(calcContentLength(controlPanelBytes)));
        	response.type("text/html");
    	}
    	
    	// otherwise try to match with filters and route
    	else {    		
    		Matcher matcher = new Matcher();
    		
    		try {
    			// apply before filters, if any
        		ArrayList<Filter> matchedBeforeFilters = matcher.matchFilter(request, response, true);
        		for(Filter filter : matchedBeforeFilters) {
        			filter.handle(request, response);
        		}
    		
    		
    			// apply routes, if any
        		Route matchedRoute = matcher.matchRoute(request, response);
        		if (matchedRoute != null) {
        			Object obj = matchedRoute.handle(request, response);
        			if (obj != null) {
        				response.body(obj.toString());
        			}        			
        		}   	    		
        		// if can't match with a route, then attempt to retrieve from static path
        		else {
            		getFileFromPath();
        		}	
    		
    			// apply after filters, if any
        		ArrayList<Filter> matchedAfterFilters = matcher.matchFilter(request, response, false);
        		for(Filter filter : matchedAfterFilters) {
        			filter.handle(request, response);
        		}
			}
			catch(HaltException he) {
				throw he;
			}
    		response.convertSetCookiesToHeaders();
    	}
    }
    
	/** Gets file from directory
	 * @throws HaltException
	 */
	private void getFileFromPath () throws HaltException {
		try {
			Path fileDirectory = createRelativePath(request.root_dir(), request.uri());
			file = new File(fileDirectory.toString());
			logger.debug("Uri for the file: "+file.toString());
			if (file.exists() == false) {
				throw new HaltException(404, HttpParsing.explainStatus(404));
			}
			
			// check if path is a file or directory
			if (file.isDirectory()) {
				// if it is a directory, then see if /index.html is there
				file = new File(file.toString() + "/index.html");
				fileDirectory = Paths.get(fileDirectory.toString(), "/index.html");
				if (file.exists() == false) {
					throw new HaltException(404, HttpParsing.explainStatus(404));
				}
			}
			
			// get content type
			setContentType(fileDirectory);
						
			response.setProtocol(request.protocol());
			// read file into byte array
			byte[] bodyRaw = Files.readAllBytes(file.toPath());
			response.bodyRaw(bodyRaw);
			response.addToHeaders("Content-Length", Integer.toString(calcContentLength(bodyRaw)));
			setLastModifiedHeader(fileDirectory);
		}
		catch (HaltException he){
			throw he;
		}
		catch (IOException e) {
			logger.error("Error reading file content for file: "+file.toString()+" due to exception: "+e);
			throw new HaltException(500, HttpParsing.explainStatus(500));
		}
				
	}
	
	private int calcContentLength(byte[] bytes) {
		return bytes.length;
	}
	
	private void setContentType(Path fileDirectory) throws HaltException {
		try {
			String mimeType = Files.probeContentType(fileDirectory);
			if (mimeType != null) {
				response.type(mimeType);
				response.addToHeaders("Content-Type", mimeType);
			}
		} catch (IOException e) {
			logger.error("Error retrieving file type for file: "+fileDirectory.toString()+" due to error: "+e);
			throw new HaltException(500, HttpParsing.explainStatus(500));
		}
	}
	
	private void setLastModifiedHeader(Path fileDirectory) throws HaltException{
		try {
			FileTime lastModified = Files.getLastModifiedTime(fileDirectory);
			response.addToHeaders("Last-Modified",lastModified.toString());
		} catch (IOException e) {
			logger.error("Error retrieving last modified time from file: "+fileDirectory.toString()+" due to error: "+e);
			throw new HaltException(500, HttpParsing.explainStatus(500));
		}
	}
	
	private Path createRelativePath (String root_dir, String uri) throws HaltException {
		try {
			Path pathDirectory = Paths.get(root_dir, uri);
			return pathDirectory;
		}
		catch (Exception e){
			e.printStackTrace();
			HaltException haltException = new HaltException(404, HttpParsing.explainStatus(404));
			throw haltException;
		}
	}
	
}
