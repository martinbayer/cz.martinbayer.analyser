package cz.martinbayer.analyser.processors.events;

import java.util.EventObject;

import cz.martinbayer.analyser.processors.types.LogProcessor;

public class StatusEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6534307333780537214L;

	private ProcessorStatus status;

	public StatusEvent(LogProcessor<?> source, ProcessorStatus status) {
		super(source);
		this.status = status;
	}

	@Override
	public LogProcessor<?> getSource() {
		return (LogProcessor<?>) super.getSource();
	}

	public final ProcessorStatus getStatus() {
		return status;
	}

	public final String getAsString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(getSource().getName()).append("] ")
				.append(getStatus().getMessage());
		return sb.toString();
	}
}
