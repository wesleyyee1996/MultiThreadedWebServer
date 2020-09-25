package edu.upenn.cis.cis455.m1.handling;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.interfaces.Request;
import edu.upenn.cis.cis455.m1.interfaces.Response;
import edu.upenn.cis.cis455.m1.interfaces.ResponseFactory;
import edu.upenn.cis.cis455.m1.interfaces.Route;

public class RequestHandler implements Route {

	private Request request;
	private Response response;
	private File file;
	
	public RequestHandler(Request request) {
		this.request = request;
	}
	
    @Override
    public Object handle(Request request, Response response) throws HaltException {
        response.status(200);
        response.type("text/html");

        return "<html><head><title>Response</title></head><body><h1>Response</h1><p>Test</p></body></html>";
    }
    
	private void getFile () throws IOException {
		Path fileDirectory = createRelativePath();
		file = new File(fileDirectory.toString());
		if (file.exists() == false) {
			
		}
				
	}
	
	private void getHead () throws IOException {
		
	}
	
	private Path createRelativePath () {
		Path pathDirectory = Paths.get(request.root_dir(), request.url());
		return pathDirectory;
	}
	
	
	public Response createResponse(String responseType) throws IOException {
    	ResponseFactory responseFactory = new ResponseFactory();
    	return responseFactory.getResponse();
    	
    }
}
