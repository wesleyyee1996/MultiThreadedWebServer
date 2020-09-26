package edu.upenn.cis.cis455.m1.handling;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.interfaces.GetResponse;
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.Response;
import edu.upenn.cis.cis455.m1.interfaces.ResponseFactory;
import edu.upenn.cis.cis455.m1.interfaces.Route;

public class RequestHandler implements Route {

	private Request request;
	private Response response;
	public String responseBody;
	private File file;
	
	public RequestHandler(Request request) {
		this.request = request;
		
		try {
			responseBody = (String)handle(request, response);
		} catch (HaltException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    @Override
    public Object handle(Request request, Response response) throws HaltException, IOException {
        //response.status(200);
        //response.type("text/html");
        getFile();
        return this.response.body();
    }
    
    // General check method. If there's some exception, send a 500 response
    
    // Check if file exists in directory
    	// If it doesn't exist, then create a file not found response (404)
    
    // Create response method should get response using ResponseFactory, but RequestHandler should add the file to the body
    
    
	/** Gets file from directory
	 * @throws HaltException
	 */
	private void getFile () throws HaltException {
		try {
			Path fileDirectory = createRelativePath();
			file = new File(fileDirectory.toString());
			if (file.exists() == false) {
				HaltException haltException = new HaltException(404, "<html><head><title>404 Server Error</title></head><body><h1>File not found</h1><p>Test</p></body></html>\"");
				response = new GetResponse(haltException);
			}
		}
		catch (HaltException e){
			e.printStackTrace();
			HaltException haltException = new HaltException(404, "<html><head><title>404 Server Error</title></head><body><h1>File not found</h1><p>Test</p></body></html>\"");
			response = new GetResponse(haltException);
		}
				
	}
	
	private void getHead () throws IOException {
		
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
			HaltException haltException = new HaltException(404, "<html><head><title>404 Server Error</title></head><body><h1>File not found</h1><p>Test</p></body></html>\"");
			response = new GetResponse(haltException);
		}
		return null;
	}
	
	
	public Response createResponse() throws IOException {
		GetResponse getResponse = new GetResponse(file);
		return getResponse;
    	//ResponseFactory responseFactory = new ResponseFactory();
    	//return responseFactory.createResponse();
		
    	
    }
}
