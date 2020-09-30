package edu.upenn.cis.cis455.m1.handling;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Hashtable;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.Response;
import edu.upenn.cis.cis455.m1.interfaces.Route;

public class RequestHandler{
	final static Logger logger = LogManager.getLogger(RequestHandler.class);
	
	private Request request;
	private Response response;
	private String responseBody;
	private File file;
		
    /**
     * Decides which method to call (either shutdown, show control panel, or get file)
     * @param request
     * @return
     * @throws HaltException
     * @throws IOException
     */
    public Response handleRequest(Request request, Response response) throws HaltException, IOException {
    	this.request = request;
    	this.response = response;
        //response.status(200);
        //response.type("text/html");
    	boolean handleFunctionSuccess = handleFunction();
    	if (!handleFunctionSuccess) {
    		return this.response;
    	}
        // if file exists, then get content type and set Response
        // if file exists, then generate the body
        
        return this.response;
    }
    
    // General check method. If there's some exception, send a 500 response
    
    // Check if file exists in directory
    	// If it doesn't exist, then create a file not found response (404)
    
    // Get file type    
    
    private boolean handleFunction() {
    	if (request.uri() == "/shutdown") {
    		// call shutdown method
    	}
    	if (request.uri() == "/control") {
    		// call control panel method
    	}
    	else {
    		// get file from directory
    		return getFileFromPath();
    	}
    	return true;
    }
    
	/** Gets file from directory
	 * @throws HaltException
	 */
	private boolean getFileFromPath () throws HaltException {
		try {
			Path fileDirectory = createRelativePath();
			file = new File(fileDirectory.toString());
			logger.debug("Uri for the file: "+file.toString());
			if (file.exists() == false) {
				
				response.status(404);
				response.body(Constants.error_FileNotFound);
				return false;
			}
			
			// get content type
			String mimeType = Files.probeContentType(fileDirectory);
			if (mimeType != null) {
				response.type(mimeType);
			}
						
			response.setProtocol(request.protocol());
			
			
			
			// read file into byte array
			response.bodyRaw(Files.readAllBytes(file.toPath()));
			response.setHeaders(setResponseHeaders(fileDirectory));
			return true;
		}
		catch (HaltException | IOException e){
			response.status(500);
			response.body(Constants.error_ServerError);
			return false;
		}
				
	}
	
	private Hashtable<String,String> setResponseHeaders(Path fileDirectory) throws IOException{
		Hashtable<String,String> responseHeaders = new Hashtable<String,String>();
		FileTime lastModified = Files.getLastModifiedTime(fileDirectory);
		responseHeaders.put("Last-Modified",lastModified.toString());
		responseHeaders.put("Server:","CIS555/1.00");
		return responseHeaders;		
	}
	
	private void getHeaders() throws IOException {
		
	}
	
	private Path createRelativePath () throws HaltException {
		try {
			System.out.println(request.root_dir());
			System.out.println(request.uri());
			Path pathDirectory = Paths.get(request.root_dir(), request.uri());
			return pathDirectory;
		}
		catch (Exception e){
			e.printStackTrace();
			HaltException haltException = new HaltException(404, Constants.error_FileNotFound);
		}
		return null;
	}
	
	
//	public Response createResponse() throws IOException {
//    	ResponseFactory responseFactory = new ResponseFactory();
//    	return responseFactory.createResponse();
//		
//    	
//    }
}
