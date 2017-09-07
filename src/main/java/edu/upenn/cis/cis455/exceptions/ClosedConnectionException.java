package edu.upenn.cis.cis455.exceptions;

public class ClosedConnectionException extends HaltException {
    public ClosedConnectionException() {
        super(0);
    }
}
