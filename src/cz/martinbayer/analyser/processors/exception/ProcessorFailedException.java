package cz.martinbayer.analyser.processors.exception;

public class ProcessorFailedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8901923189314552972L;

	public ProcessorFailedException(String message) {
		super(message);
	}
}
