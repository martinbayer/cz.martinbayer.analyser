package cz.martinbayer.analyser.processors.events;

public class ProcessorStatus {

	private String message;

	public ProcessorStatus(String statusMessage) {
		this.message = statusMessage;
	}

	public final String getMessage() {
		return message;
	}
}
