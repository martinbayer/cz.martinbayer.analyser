package cz.martinbayer.analyser.processors.model;

import java.util.EventObject;

public class LogRemovedEvent<T extends IE4LogsisLog> extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4785929543183440383L;

	private boolean removed;

	public LogRemovedEvent(T source, boolean removed) {
		super(source);
		this.removed = removed;
	}

	/**
	 * Mark the record to be removed if True is returned, otherwise mark it as
	 * Not removed
	 * 
	 * @return
	 */
	public final boolean isRemoved() {
		return removed;
	}

	@Override
	public T getSource() {
		return (T) source;
	}
}
