package edu.upenn.cis.cis455.m1.interfaces;

import java.io.File;
import java.io.IOException;

import edu.upenn.cis.cis455.Constants;
import edu.upenn.cis.cis455.exceptions.HaltException;

public class SuccessResponse extends Response {

	
//	/** Constructor for GetResponse. Sets body from a File type.
//	 * @param file
//	 * @throws IOException
//	 */
//	public SuccessResponse() throws IOException {
//		//this.setBodyFromFile(file);
//	}
	
	/** Constructor for GetResponse. Sets body and status from a HaltException.
	 * @param haltException
	 */
//	public SuccessResponse(HaltException haltException) {
//		this.body(haltException.body());
//		this.status(haltException.statusCode());
//	}

	@Override
	public String getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer constructStatusLine() {
    	StringBuffer statusLine = new StringBuffer();
    	return statusLine.append(Constants.httpVersion + "200 OK" + Constants.CRFL);
	}
	
	


}
