package edu.upenn.cis.cis455.m1.handling;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Hashtable;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.Response;
import edu.upenn.cis.cis455.m1.interfaces.Route;
import edu.upenn.cis.cis455.m1.server.ControlPanel;
import edu.upenn.cis.cis455.m1.server.WebService;

public class RequestHandler{
	final static Logger logger = LogManager.getLogger(RequestHandler.class);
	
	private Request request;
	private Response response;
	private Socket socket;
	private String responseBody;
	private File file;
	private int route;
		
    /**
     * Decides which method to call (either shutdown, show control panel, or get file)
     * @param request
     * @return
     * @throws HaltException
     * @throws IOException
     */
    public boolean handleRequest(Request request, Response response, Socket socket) throws HaltException, IOException {
    	this.request = request;
    	this.response = response;
    	this.socket = socket;
    	this.response.setProtocol(request.protocol());
    	this.response.setMethod(request.requestMethod());
    	this.response.addToHeaders("Server","CIS555/1.00");

    	boolean handleFunctionSuccess = handleFunction();
    	if (!handleFunctionSuccess) {
    		return false;
    	}
        
        return true;
    }
    
    private boolean handleFunction() {
    	if (request.uri().equals("/shutdown") && request.requestMethod().equals(Constants.get)) {
    		// call shutdown method
    		WebService.getInstance().setSocket(this.socket);
    		WebService.getInstance().stop();
    	}
    	else if (request.uri().equals("/control")) {
    		ControlPanel controlPanel = new ControlPanel();
        	byte[] controlPanelBytes = controlPanel.getControlPanel(WebService.getInstance().getThreadPool());
        	response.bodyRaw(controlPanelBytes);
        	response.addToHeaders("Content-Length", Integer.toString(calcContentLength(controlPanelBytes)));
        	response.type("text/html");
    		return true;
    		// call control panel method
    	}
    	else {
    		// if request type is head, then don't need to get the body
	    		route = Constants.normalRoute;
	    		return getFileFromPath();
    	}
    	return true;
    }
    
	/** Gets file from directory
	 * @throws HaltException
	 */
	private boolean getFileFromPath () throws HaltException {
		try {
			Path fileDirectory = createRelativePath(request.root_dir(), request.uri());
			file = new File(fileDirectory.toString());
			logger.debug("Uri for the file: "+file.toString());
			if (file.exists() == false) {
				response.status(404);
				response.body(HttpParsing.explainStatus(404));
				WebService.getInstance().halt(404, HttpParsing.explainStatus(404));
				return false;
			}
			
			// check if path is a file or directory
			if (file.isDirectory()) {
				// if it is a directory, then see if /index.html is there
				file = new File(file.toString() + "/index.html");
				fileDirectory = Paths.get(fileDirectory.toString(), "/index.html");
				if (file.exists() == false) {
					response.status(404);
					response.body(HttpParsing.explainStatus(404));
					return false;
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
			return true;
		}
		catch (HaltException | IOException e){
			response.status(500);
			response.body(HttpParsing.explainStatus(500));
			return false;
		}
				
	}
	
	private int calcContentLength(byte[] bytes) {
		return bytes.length;
	}
	
	private void setContentType(Path fileDirectory) throws IOException {
		String mimeType = Files.probeContentType(fileDirectory);
		if (mimeType != null) {
			response.type(mimeType);
			response.addToHeaders("Content-Type", mimeType);
		}
	}
	
	private void setLastModifiedHeader(Path fileDirectory) throws IOException{
		FileTime lastModified = Files.getLastModifiedTime(fileDirectory);
		response.addToHeaders("Last-Modified",lastModified.toString());
	}
	
	private Path createRelativePath (String root_dir, String uri) throws HaltException {
		try {
			Path pathDirectory = Paths.get(root_dir, uri);
			return pathDirectory;
		}
		catch (Exception e){
			e.printStackTrace();
			HaltException haltException = new HaltException(404, HttpParsing.explainStatus(404));
		}
		return null;
	}
	
}
