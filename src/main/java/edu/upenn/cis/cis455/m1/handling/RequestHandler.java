package edu.upenn.cis.cis455.m1.handling;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.Response;
import edu.upenn.cis.cis455.m1.interfaces.Route;

public class RequestHandler implements Route {

	private Request request;
	private Response response;
	private String responseBody;
	private File file;
	
	public RequestHandler(Request request) {
		this.request = request;
		
//		try {
//			responseBody = (String)handle(request, response);
//		} catch (HaltException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	// Since the only thing we are returning to the HttpIOHandler is the string of the Response body, 
    @Override
    public Object handle(Request request, Response response) throws HaltException, IOException {
        //response.status(200);
        //response.type("text/html");
        getFile(response);
        // if file exists, then get content type and set Response
        // if file exists, then generate the body
        
        return this.response.body();
    }
    
    // General check method. If there's some exception, send a 500 response
    
    // Check if file exists in directory
    	// If it doesn't exist, then create a file not found response (404)
    
    // Get file type    
    
	/** Gets file from directory
	 * @throws HaltException
	 */
	private void getFile (Response response) throws HaltException {
		try {
			Path fileDirectory = createRelativePath();
			file = new File(fileDirectory.toString());
			if (file.exists() == false) {
				throw new HaltException(404, Constants.error_FileNotFound);
			}
		}
		catch (HaltException e){
			e.printStackTrace();
			throw new HaltException(404, Constants.error_FileNotFound);
		}
				
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
