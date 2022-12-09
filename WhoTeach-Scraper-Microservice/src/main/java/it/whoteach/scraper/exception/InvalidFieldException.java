package it.whoteach.scraper.exception;

public class InvalidFieldException extends RuntimeException {

	private static final long serialVersionUID = 2240245875148803441L;
	
	public InvalidFieldException(String message) {
		super(message);
	}
}
