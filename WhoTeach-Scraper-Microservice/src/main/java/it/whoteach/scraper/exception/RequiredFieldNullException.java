package it.whoteach.scraper.exception;

public class RequiredFieldNullException extends RuntimeException {
	
	public RequiredFieldNullException(String message) {
		super(message);
	}
}
