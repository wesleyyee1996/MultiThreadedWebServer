package edu.upenn.cis.cis455.exceptions;

public class ClosedConnectionException extends HaltException {
	private static final long serialVersionUID = -8561030665797185655L;

	public ClosedConnectionException() {
        super(0);
    }
}
