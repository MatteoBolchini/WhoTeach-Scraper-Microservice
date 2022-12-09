package it.whoteach.scraper.exception;

public class RequiredFieldNullException extends RuntimeException {
	
	private static final long serialVersionUID = -4656936788208299157L;

	public RequiredFieldNullException(String message) {
		super(message);
	}
}
