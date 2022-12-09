package it.whoteach.scraper.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2335062130195757707L;

	public EntityNotFoundException(String message) {
		super(message);
	}
}
