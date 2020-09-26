package edu.upenn.cis.cis455.m1.interfaces;

import java.io.File;
import java.io.IOException;

import edu.upenn.cis.cis455.exceptions.HaltException;

public class GetResponse extends Response {

	
	/** Constructor for GetResponse. Sets body from a File type.
	 * @param file
	 * @throws IOException
	 */
	public GetResponse(File file) throws IOException {
		this.setBodyFromFile(file);
	}
	
	/** Constructor for GetResponse. Sets body and status from a HaltException.
	 * @param haltException
	 */
	public GetResponse(HaltException haltException) {
		this.body(haltException.body());
		this.status(haltException.statusCode());
	}
	
	


}
