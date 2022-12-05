package it.whoteach.scraper.exception;

public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 6039784713149154511L;

	public BadRequestException(String message) {
		super(message);
	}
}
