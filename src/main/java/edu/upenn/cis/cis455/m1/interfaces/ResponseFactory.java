package edu.upenn.cis.cis455.m1.interfaces;

import java.io.File;
import java.io.IOException;

import edu.upenn.cis.cis455.Constants;

/** Creates Responses based on the status code
 * @author vagrant
 *
 */
public class ResponseFactory {
	
	/** Return a different Response based on status code
	 * @param statusCode
	 * @return
	 * @throws IOException
	 */
	public Response createResponse(int statusCode) throws IOException {
		if (responseType == Constants.get) {
			return new GetResponse();
		}
		if (responseType == Constants.head) {
			return new HeadResponse();
		}
		throw new IOException("Invalid response type: " + responseType);
	}
	
	// ResponseFactory should set everything regarding the Response, so create an overload createResponse method
	// that also passes in the File if success
	public Response createResponse(int statusCode, File file) throws IOException {
		if (statusCode == 200) {
			return new SuccessResponse(statusCode, file);
		}
	}
	
	// Based on the status of the request handler, create different kind of response
	// Exception Response
	// Success Response
	
	// Set Response attributes.
	private void setResponseAttributes(Response response) {
		response.body
	}
	
	// set Status Code
	private void setStatusCode(Response response) {
		
	}
	
	
	// set Content Type
	private void setContentType(Response response) {
		
	}
	
}
